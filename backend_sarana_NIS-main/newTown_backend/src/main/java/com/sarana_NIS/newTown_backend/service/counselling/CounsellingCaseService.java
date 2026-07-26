package com.sarana_NIS.newTown_backend.service.counselling;

import com.sarana_NIS.newTown_backend.dto.request.counselling.CounsellingCaseRequest;
import com.sarana_NIS.newTown_backend.dto.request.counselling.CaseActionRequest;
import com.sarana_NIS.newTown_backend.dto.response.counselling.CounsellingCaseResponse;
import com.sarana_NIS.newTown_backend.dto.response.counselling.AtRiskStudentDetailResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.counselling.CounsellingCase;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.model.enums.CaseStatus;
import com.sarana_NIS.newTown_backend.model.enums.IssueSeverity;
import com.sarana_NIS.newTown_backend.repository.CounsellingCaseRepository;
import com.sarana_NIS.newTown_backend.repository.StudentRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CounsellingCaseService {

    private static final Logger log = LoggerFactory.getLogger(CounsellingCaseService.class);

    private final CounsellingCaseRepository counsellingCaseRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public CounsellingCaseService(CounsellingCaseRepository counsellingCaseRepository,
                                  StudentRepository studentRepository,
                                  UserRepository userRepository) {
        this.counsellingCaseRepository = counsellingCaseRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CounsellingCaseResponse createCase(CounsellingCaseRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        CounsellingCase counsellingCase = new CounsellingCase();
        counsellingCase.setStudent(student);
        counsellingCase.setIssueType(request.getIssueType());
        counsellingCase.setTitle(request.getTitle());
        counsellingCase.setDescription(request.getDescription());
        counsellingCase.setSeverity(request.getSeverity());
        counsellingCase.setStatus(CaseStatus.ACTIVE);
        counsellingCase.setOpenedAt(LocalDateTime.now());

        if (request.getOpenedBy() != null) {
            User user = userRepository.findById(request.getOpenedBy())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            counsellingCase.setOpenedBy(user);
        }

        CounsellingCase saved = counsellingCaseRepository.save(counsellingCase);
        log.info("Counselling case created for student: {}", student.getNameEnglish());
        return mapToResponse(saved);
    }

    @Transactional
    public CounsellingCaseResponse updateCaseStatus(CaseActionRequest request) {
        CounsellingCase counsellingCase = counsellingCaseRepository.findById(request.getCaseId())
                .orElseThrow(() -> new RuntimeException("Case not found"));

        switch (request.getAction()) {
            case "RESOLVE":
                counsellingCase.setStatus(CaseStatus.RESOLVED);
                counsellingCase.setClosedAt(LocalDateTime.now());
                break;
            case "CLOSE":
                counsellingCase.setStatus(CaseStatus.CLOSED);
                counsellingCase.setClosedAt(LocalDateTime.now());
                break;
            case "REOPEN":
                counsellingCase.setStatus(CaseStatus.ACTIVE);
                counsellingCase.setClosedAt(null);
                break;
            case "ESCALATE":
                counsellingCase.setSeverity(IssueSeverity.CRITICAL);
                break;
            default:
                throw new RuntimeException("Invalid action: " + request.getAction());
        }

        CounsellingCase saved = counsellingCaseRepository.save(counsellingCase);
        log.info("Case status updated: {} - {}", request.getAction(), request.getCaseId());
        return mapToResponse(saved);
    }

    public CounsellingCaseResponse getCaseById(Long caseId) {
        CounsellingCase counsellingCase = counsellingCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found with id: " + caseId));
        return mapToResponse(counsellingCase);
    }

    public List<CounsellingCaseResponse> getActiveCases() {
        List<CounsellingCase> cases = counsellingCaseRepository.findByStatus(CaseStatus.ACTIVE);
        return cases.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<CounsellingCaseResponse> getCasesByStudent(Long studentId) {
        List<CounsellingCase> cases = counsellingCaseRepository.findByStudentId(studentId);
        return cases.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<AtRiskStudentDetailResponse> getAtRiskStudents() {
        List<CounsellingCase> cases = counsellingCaseRepository.findHighPriorityCases();
        return cases.stream()
                .collect(Collectors.groupingBy(CounsellingCase::getStudent))
                .entrySet().stream()
                .map(entry -> {
                    Student student = entry.getKey();
                    List<CounsellingCase> studentCases = entry.getValue();

                    AtRiskStudentDetailResponse response = new AtRiskStudentDetailResponse();
                    response.setStudentId(student.getId());
                    response.setStudentName(student.getNameEnglish());
                    response.setClassName(student.getCurrentClass() != null ?
                            student.getCurrentClass().getName() : null);

                    IssueSeverity maxSeverity = studentCases.stream()
                            .map(CounsellingCase::getSeverity)
                            .max(Enum::compareTo)
                            .orElse(IssueSeverity.MEDIUM);
                    response.setOverallSeverity(maxSeverity);

                    response.setActiveCases(studentCases.stream()
                            .map(c -> {
                                AtRiskStudentDetailResponse.CaseSummary summary =
                                        new AtRiskStudentDetailResponse.CaseSummary();
                                summary.setId(c.getId());
                                // ✅ Use getTitle() - make sure it exists in CounsellingCase
                                summary.setTitle(c.getTitle());
                                summary.setStatus(c.getStatus().name());
                                summary.setOpenedAt(c.getOpenedAt());
                                return summary;
                            })
                            .collect(Collectors.toList()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCase(Long caseId) {
        CounsellingCase counsellingCase = counsellingCaseRepository.findById(caseId)
                .orElseThrow(() -> new RuntimeException("Case not found"));
        counsellingCaseRepository.delete(counsellingCase);
        log.info("Counselling case deleted: {}", caseId);
    }

    // ========== Helper Methods ==========

    private CounsellingCaseResponse mapToResponse(CounsellingCase counsellingCase) {
        CounsellingCaseResponse response = new CounsellingCaseResponse();
        response.setId(counsellingCase.getId());
        response.setStudentId(counsellingCase.getStudent().getId());
        response.setStudentName(counsellingCase.getStudent().getNameEnglish());
        response.setIssueType(counsellingCase.getIssueType());
        response.setTitle(counsellingCase.getTitle());
        response.setDescription(counsellingCase.getDescription());
        response.setSeverity(counsellingCase.getSeverity());
        response.setStatus(counsellingCase.getStatus());

        if (counsellingCase.getOpenedBy() != null) {
            response.setOpenedBy(counsellingCase.getOpenedBy().getFullName());
        }

        response.setOpenedAt(counsellingCase.getOpenedAt());
        response.setClosedAt(counsellingCase.getClosedAt());

        // Set session count - you would need to count sessions
        response.setSessionCount(0);
        response.setLastSessionAt(null);
        response.setIsAtRisk(counsellingCase.getSeverity() == IssueSeverity.HIGH ||
                counsellingCase.getSeverity() == IssueSeverity.CRITICAL);
        response.setActionPlan(counsellingCase.getActionPlan());

        return response;
    }
}