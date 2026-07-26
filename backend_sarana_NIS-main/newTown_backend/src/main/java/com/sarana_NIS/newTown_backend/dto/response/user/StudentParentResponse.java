package com.sarana_NIS.newTown_backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.Relationship;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentParentResponse {

    private Long studentId;
    private String studentName;
    private String studentIdNumber;
    private Long parentId;
    private String parentName;
    private String parentPhone;
    private String parentEmail;
    private Relationship relationship;
    private String relationshipDisplay;

    // Constructors
    public StudentParentResponse() {}

    public StudentParentResponse(Long studentId, String studentName, Long parentId, String parentName) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.parentId = parentId;
        this.parentName = parentName;
    }

    // Getters and Setters
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentIdNumber() { return studentIdNumber; }
    public void setStudentIdNumber(String studentIdNumber) { this.studentIdNumber = studentIdNumber; }

    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }

    public String getParentPhone() { return parentPhone; }
    public void setParentPhone(String parentPhone) { this.parentPhone = parentPhone; }

    public String getParentEmail() { return parentEmail; }
    public void setParentEmail(String parentEmail) { this.parentEmail = parentEmail; }

    public Relationship getRelationship() { return relationship; }
    public void setRelationship(Relationship relationship) { this.relationship = relationship; }

    public String getRelationshipDisplay() { return relationshipDisplay; }
    public void setRelationshipDisplay(String relationshipDisplay) { this.relationshipDisplay = relationshipDisplay; }
}