package com.sarana_NIS.newTown_backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParentResponse {

    private Long id;
    private UserResponse user;
    private String relationship;
    private String occupation;
    private String address;
    private List<StudentInfo> students = new ArrayList<>();

    // Constructors
    public ParentResponse() {}

    public ParentResponse(Long id, UserResponse user) {
        this.id = id;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public UserResponse getUser() { return user; }
    public void setUser(UserResponse user) { this.user = user; }

    public String getRelationship() { return relationship; }
    public void setRelationship(String relationship) { this.relationship = relationship; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public List<StudentInfo> getStudents() { return students; }
    public void setStudents(List<StudentInfo> students) { this.students = students; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StudentInfo {
        private Long id;
        private String studentId;
        private String fullName;
        private String level;
        private String className;
        private String status;
        private Double gpa;

        // Constructors
        public StudentInfo() {}

        public StudentInfo(Long id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getStudentId() { return studentId; }
        public void setStudentId(String studentId) { this.studentId = studentId; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }

        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public Double getGpa() { return gpa; }
        public void setGpa(Double gpa) { this.gpa = gpa; }
    }
}