package com.sarana_NIS.newTown_backend.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class ParentCreateRequest {

    @NotBlank(message = "Relationship is required")
    @Size(min = 2, max = 50, message = "Relationship must be between 2 and 50 characters")
    private String relationship;

    @Size(max = 100, message = "Occupation must be less than 100 characters")
    private String occupation;

    @Size(max = 255, message = "Address must be less than 255 characters")
    private String address;

    private Long userId;

    @Size(min = 1, message = "At least one student is required")
    private List<Long> studentIds = new ArrayList<>();

    // Constructors
    public ParentCreateRequest() {}

    public ParentCreateRequest(String relationship, List<Long> studentIds) {
        this.relationship = relationship;
        this.studentIds = studentIds;
    }

    // Getters and Setters
    public String getRelationship() { return relationship; }
    public void setRelationship(String relationship) { this.relationship = relationship; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public List<Long> getStudentIds() { return studentIds; }
    public void setStudentIds(List<Long> studentIds) { this.studentIds = studentIds; }
}