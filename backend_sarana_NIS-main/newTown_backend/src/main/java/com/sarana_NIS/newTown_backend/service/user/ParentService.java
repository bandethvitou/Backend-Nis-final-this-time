package com.sarana_NIS.newTown_backend.service.user;

import com.sarana_NIS.newTown_backend.dto.request.user.ParentCreateRequest;
import com.sarana_NIS.newTown_backend.dto.response.user.ParentResponse;
import com.sarana_NIS.newTown_backend.dto.response.user.StudentParentResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.user.Parent;
import com.sarana_NIS.newTown_backend.model.entity.user.Student;
import com.sarana_NIS.newTown_backend.repository.ParentRepository;
import com.sarana_NIS.newTown_backend.repository.StudentRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentService {

    private static final Logger log = LoggerFactory.getLogger(ParentService.class);

    private final ParentRepository parentRepository;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    public ParentService(ParentRepository parentRepository, UserRepository userRepository,
                         StudentRepository studentRepository) {
        this.parentRepository = parentRepository;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public ParentResponse createParent(ParentCreateRequest request) {
        Parent parent = new Parent();
        parent.setRelationship(request.getRelationship());
        parent.setOccupation(request.getOccupation());
        parent.setAddress(request.getAddress());

        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            parent.setUser(user);
        }

        if (request.getStudentIds() != null) {
            List<Student> students = studentRepository.findAllById(request.getStudentIds());
            for (Student student : students) {
                parent.addStudent(student);
            }
        }

        Parent savedParent = parentRepository.save(parent);
        log.info("Parent created: {}", savedParent.getUser() != null ? savedParent.getUser().getFullName() : savedParent.getId());
        return mapToResponse(savedParent);
    }

    public ParentResponse getParentById(Long parentId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));
        return mapToResponse(parent);
    }

    public List<StudentParentResponse> getStudentParentRelationships(Long studentId) {
        List<Parent> parents = parentRepository.findParentsByStudentId(studentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return parents.stream()
                .map(parent -> {
                    StudentParentResponse response = new StudentParentResponse();
                    response.setStudentId(studentId);
                    response.setStudentName(student.getNameEnglish());
                    response.setParentId(parent.getId());
                    response.setParentName(parent.getUser() != null ? parent.getUser().getFullName() : null);
                    response.setRelationship(com.sarana_NIS.newTown_backend.model.enums.Relationship.valueOf(parent.getRelationship().toUpperCase()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    public PageResponse<ParentResponse> getAllParents(Pageable pageable) {
        Page<Parent> page = parentRepository.findAll(pageable);
        List<ParentResponse> content = page.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(content, page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }

    @Transactional
    public void deleteParent(Long parentId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        new ArrayList<>(parent.getStudents()).forEach(parent::removeStudent);

        parentRepository.delete(parent);
        log.info("Parent deleted: {}", parentId);
    }

    @Transactional
    public ParentResponse updateParent(Long parentId, ParentCreateRequest request) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new RuntimeException("Parent not found"));

        if (request.getRelationship() != null) parent.setRelationship(request.getRelationship());
        if (request.getOccupation() != null) parent.setOccupation(request.getOccupation());
        if (request.getAddress() != null) parent.setAddress(request.getAddress());

        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            parent.setUser(user);
        }

        if (request.getStudentIds() != null) {
            new ArrayList<>(parent.getStudents()).forEach(parent::removeStudent);
            List<Student> students = studentRepository.findAllById(request.getStudentIds());
            for (Student student : students) {
                parent.addStudent(student);
            }
        }

        Parent updatedParent = parentRepository.save(parent);
        log.info("Parent updated: {}", updatedParent.getId());
        return mapToResponse(updatedParent);
    }

    private ParentResponse mapToResponse(Parent parent) {
        ParentResponse response = new ParentResponse();
        response.setId(parent.getId());

        if (parent.getUser() != null) {
            response.setUser(UserService.mapToUserResponse(parent.getUser()));
        }

        response.setRelationship(parent.getRelationship());
        response.setOccupation(parent.getOccupation());
        response.setAddress(parent.getAddress());

        List<ParentResponse.StudentInfo> students = parent.getStudents().stream()
                .map(student -> {
                    ParentResponse.StudentInfo info = new ParentResponse.StudentInfo();
                    info.setId(student.getId());
                    info.setStudentId(student.getStudentId());
                    info.setFullName(student.getNameEnglish());
                    info.setLevel(student.getLevel() != null ? student.getLevel().name() : null);
                    info.setClassName(student.getCurrentClass() != null ? student.getCurrentClass().getName() : null);
                    info.setStatus(student.getStudentStatus() != null ? student.getStudentStatus().name() : null);
                    return info;
                })
                .collect(Collectors.toList());
        response.setStudents(students);

        return response;
    }
}