package com.sarana_NIS.newTown_backend.dto.request.user;

import com.sarana_NIS.newTown_backend.model.enums.Relationship;
import jakarta.validation.constraints.NotNull;

public class StudentParentRequest {

    @NotNull(message = "Student ID is required")
    private Long studentId;

    @NotNull(message = "Parent ID is required")
    private Long parentId;

    @NotNull(message = "Relationship is required")
    private Relationship relationship;

    // Constructors
    public StudentParentRequest() {}

    public StudentParentRequest(Long studentId, Long parentId, Relationship relationship) {
        this.studentId = studentId;
        this.parentId = parentId;
        this.relationship = relationship;
    }

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public Relationship getRelationship() { return relationship; }
    public void setRelationship(Relationship relationship) { this.relationship = relationship; }
}