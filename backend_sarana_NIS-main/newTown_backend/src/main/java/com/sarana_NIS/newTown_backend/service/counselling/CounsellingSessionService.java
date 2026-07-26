package com.sarana_NIS.newTown_backend.service.counselling;

import com.sarana_NIS.newTown_backend.dto.request.counselling.CounsellingSessionRequest;
import com.sarana_NIS.newTown_backend.dto.response.counselling.CounsellingSessionResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.counselling.CounsellingCase;
import com.sarana_NIS.newTown_backend.model.entity.counselling.CounsellingSession;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.repository.CounsellingCaseRepository;
import com.sarana_NIS.newTown_backend.repository.CounsellingSessionRepository;
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
public class CounsellingSessionService {

    private static final Logger log = LoggerFactory.getLogger(CounsellingSessionService.class);

    private final CounsellingSessionRepository counsellingSessionRepository;
    private final CounsellingCaseRepository counsellingCaseRepository;
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    public CounsellingSessionService(CounsellingSessionRepository counsellingSessionRepository,
                                     CounsellingCaseRepository counsellingCaseRepository,
                                     StudentRepository studentRepository, UserRepository userRepository) {
        this.counsellingSessionRepository = counsellingSessionRepository;
        this.counsellingCaseRepository = counsellingCaseRepository;
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CounsellingSessionResponse createSession(CounsellingSessionRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        CounsellingSession session = new CounsellingSession();

        // ✅ Fix: CounsellingSession doesn't have direct student field
        // Student is accessed through counsellingCase
        // We'll set this after we create the case reference

        // ✅ Fix: Convert LocalDate to LocalDateTime (start of day)
        LocalDateTime sessionDateTime = request.getSessionDate().atStartOfDay();
        session.setSessionDate(sessionDateTime);

        // ✅ Fix: Use durationMinutes instead of duration
        session.setDurationMinutes(request.getDuration());
        session.setSessionType(request.getSessionType());
        session.setNotes(request.getNotes());
        session.setFollowUpRequired(request.getFollowUpRequired() != null && request.getFollowUpRequired());

        // ✅ Fix: Convert LocalDate to LocalDateTime for followUpDate
        if (request.getFollowUpDate() != null) {
            session.setFollowUpDate(request.getFollowUpDate().atStartOfDay());
        }

        if (request.getCaseId() != null) {
            CounsellingCase counsellingCase = counsellingCaseRepository.findById(request.getCaseId())
                    .orElseThrow(() -> new RuntimeException("Case not found"));
            session.setCounsellingCase(counsellingCase);
        } else {
            // If no case, create one or throw error
            throw new RuntimeException("Case ID is required");
        }

        if (request.getCounsellorId() != null) {
            User counsellor = userRepository.findById(request.getCounsellorId())
                    .orElseThrow(() -> new RuntimeException("Counsellor not found"));
            session.setCounsellor(counsellor);
        }

        CounsellingSession saved = counsellingSessionRepository.save(session);
        log.info("Counselling session created for student: {}", student.getNameEnglish());
        return mapToResponse(saved);
    }

    public List<CounsellingSessionResponse> getSessionsByStudent(Long studentId) {
        List<CounsellingSession> sessions = counsellingSessionRepository.findByStudentId(studentId);
        return sessions.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public List<CounsellingSessionResponse> getSessionsByCase(Long caseId) {
        List<CounsellingSession> sessions = counsellingSessionRepository.findByCaseId(caseId);
        return sessions.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public CounsellingSessionResponse getSessionById(Long sessionId) {
        CounsellingSession session = counsellingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        return mapToResponse(session);
    }

    @Transactional
    public CounsellingSessionResponse updateSessionNotes(Long sessionId, String notes) {
        CounsellingSession session = counsellingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));
        session.setNotes(notes);
        CounsellingSession updated = counsellingSessionRepository.save(session);
        log.info("Session notes updated for session: {}", sessionId);
        return mapToResponse(updated);
    }

    private CounsellingSessionResponse mapToResponse(CounsellingSession session) {
        CounsellingSessionResponse response = new CounsellingSessionResponse();
        response.setId(session.getId());

        // ✅ Fix: Get student info from counsellingCase
        if (session.getCounsellingCase() != null && session.getCounsellingCase().getStudent() != null) {
            Student student = session.getCounsellingCase().getStudent();
            response.setStudentId(student.getId());
            response.setStudentName(student.getNameEnglish());
            response.setCaseId(session.getCounsellingCase().getId());
        }

        if (session.getCounsellor() != null) {
            response.setCounsellorName(session.getCounsellor().getFullName());
            response.setCounsellorId(session.getCounsellor().getId());
        }

        // ✅ Fix: Convert LocalDateTime to LocalDate for response
        if (session.getSessionDate() != null) {
            response.setSessionDate(session.getSessionDate().toLocalDate());
        }

        // ✅ Fix: Use durationMinutes
        response.setDuration(session.getDurationMinutes());
        response.setSessionType(session.getSessionType());
        response.setNotes(session.getNotes());
        response.setFollowUpRequired(session.getFollowUpRequired());

        // ✅ Fix: Convert LocalDateTime to LocalDate for followUpDate
        if (session.getFollowUpDate() != null) {
            response.setFollowUpDate(session.getFollowUpDate().toLocalDate());
        }

        return response;
    }
}