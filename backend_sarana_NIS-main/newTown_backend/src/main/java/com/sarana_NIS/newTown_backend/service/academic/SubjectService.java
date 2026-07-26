package com.sarana_NIS.newTown_backend.service.academic;

import com.sarana_NIS.newTown_backend.dto.request.academic.SubjectRequest;
import com.sarana_NIS.newTown_backend.dto.response.academic.SubjectResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.academic.Subject;
import com.sarana_NIS.newTown_backend.model.entity.management.Department;  // Add this import
import com.sarana_NIS.newTown_backend.repository.DepartmentRepository;
import com.sarana_NIS.newTown_backend.repository.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    private static final Logger log = LoggerFactory.getLogger(SubjectService.class);

    private final SubjectRepository subjectRepository;
    private final DepartmentRepository departmentRepository;

    public SubjectService(SubjectRepository subjectRepository, DepartmentRepository departmentRepository) {
        this.subjectRepository = subjectRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public SubjectResponse createSubject(SubjectRequest request) {
        if (subjectRepository.findByCode(request.getCode()).isPresent()) {
            throw new RuntimeException("Subject code already exists: " + request.getCode());
        }

        Subject subject = new Subject();
        subject.setName(request.getName());
        subject.setCode(request.getCode());
        subject.setDescription(request.getDescription());

        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            subject.setDepartment(department);
        }

        Subject saved = subjectRepository.save(subject);
        log.info("Subject created: {}", saved.getName());
        return mapToResponse(saved);
    }

    @Transactional
    public SubjectResponse updateSubject(Long subjectId, SubjectRequest request) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (request.getName() != null) subject.setName(request.getName());
        if (request.getCode() != null) subject.setCode(request.getCode());
        if (request.getDescription() != null) subject.setDescription(request.getDescription());
        if (request.getDepartmentId() != null) {
            Department department = departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new RuntimeException("Department not found"));
            subject.setDepartment(department);
        }

        Subject updated = subjectRepository.save(subject);
        log.info("Subject updated: {}", updated.getName());
        return mapToResponse(updated);
    }

    public SubjectResponse getSubjectById(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        return mapToResponse(subject);
    }

    public SubjectResponse getSubjectByCode(String code) {
        Subject subject = subjectRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Subject not found with code: " + code));
        return mapToResponse(subject);
    }

    public PageResponse<SubjectResponse> getAllSubjects(Pageable pageable) {
        Page<Subject> page = subjectRepository.findAll(pageable);
        PageResponse<SubjectResponse> response = new PageResponse<>();
        response.setContent(page.getContent().stream().map(this::mapToResponse).collect(Collectors.toList()));
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        return response;
    }

    public List<SubjectResponse> getSubjectsByDepartment(Long departmentId) {
        List<Subject> subjects = subjectRepository.findByDepartmentId(departmentId);
        return subjects.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional
    public void deleteSubject(Long subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new RuntimeException("Subject not found"));
        subjectRepository.delete(subject);
        log.info("Subject deleted: {}", subject.getName());
    }

    private SubjectResponse mapToResponse(Subject subject) {
        SubjectResponse response = new SubjectResponse();
        response.setId(subject.getId());
        response.setName(subject.getName());
        response.setCode(subject.getCode());
        response.setDescription(subject.getDescription());
        response.setDepartmentName(subject.getDepartment() != null ? subject.getDepartment().getName() : null);
        response.setCreatedAt(subject.getCreatedAt());
        return response;
    }
}