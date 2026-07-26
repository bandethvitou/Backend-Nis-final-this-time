package com.sarana_NIS.newTown_backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sarana_NIS.newTown_backend.model.enums.EnrollmentStatus;
import com.sarana_NIS.newTown_backend.model.enums.Gender;
import com.sarana_NIS.newTown_backend.model.enums.Level;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentResponse {

    private Long id;
    private String studentId;
    private UserResponse user;
    private String nameKhmer;
    private String nameEnglish;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Level level;
    private ClassInfo currentClass;
    private LocalDate enrollmentDate;
    private EnrollmentStatus studentStatus;
    private String medicalNotes;
    private String previousSchool;
    private List<ParentInfo> parents = new ArrayList<>();
    private Double currentGPA;
    private Integer attendanceRate;
    private Boolean isAtRisk;
    private String riskLevel;

    // Constructors
    public StudentResponse() {}

    public StudentResponse(Long id, String studentId, String nameEnglish) {
        this.id = id;
        this.studentId = studentId;
        this.nameEnglish = nameEnglish;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public UserResponse getUser() { return user; }
    public void setUser(UserResponse user) { this.user = user; }

    public String getNameKhmer() { return nameKhmer; }
    public void setNameKhmer(String nameKhmer) { this.nameKhmer = nameKhmer; }

    public String getNameEnglish() { return nameEnglish; }
    public void setNameEnglish(String nameEnglish) { this.nameEnglish = nameEnglish; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public ClassInfo getCurrentClass() { return currentClass; }
    public void setCurrentClass(ClassInfo currentClass) { this.currentClass = currentClass; }

    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

    public EnrollmentStatus getStudentStatus() { return studentStatus; }
    public void setStudentStatus(EnrollmentStatus studentStatus) { this.studentStatus = studentStatus; }

    public String getMedicalNotes() { return medicalNotes; }
    public void setMedicalNotes(String medicalNotes) { this.medicalNotes = medicalNotes; }

    public String getPreviousSchool() { return previousSchool; }
    public void setPreviousSchool(String previousSchool) { this.previousSchool = previousSchool; }

    public List<ParentInfo> getParents() { return parents; }
    public void setParents(List<ParentInfo> parents) { this.parents = parents; }

    public Double getCurrentGPA() { return currentGPA; }
    public void setCurrentGPA(Double currentGPA) { this.currentGPA = currentGPA; }

    public Integer getAttendanceRate() { return attendanceRate; }
    public void setAttendanceRate(Integer attendanceRate) { this.attendanceRate = attendanceRate; }

    public Boolean getIsAtRisk() { return isAtRisk; }
    public void setIsAtRisk(Boolean isAtRisk) { this.isAtRisk = isAtRisk; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ClassInfo {
        private Long id;
        private String name;
        private String grade;
        private String room;
        private String teacherName;

        // Constructors
        public ClassInfo() {}

        public ClassInfo(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }

        public String getRoom() { return room; }
        public void setRoom(String room) { this.room = room; }

        public String getTeacherName() { return teacherName; }
        public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ParentInfo {
        private Long id;
        private String fullName;
        private String phone;
        private String email;
        private String relationship;
        private String occupation;

        // Constructors
        public ParentInfo() {}

        public ParentInfo(Long id, String fullName) {
            this.id = id;
            this.fullName = fullName;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getRelationship() { return relationship; }
        public void setRelationship(String relationship) { this.relationship = relationship; }

        public String getOccupation() { return occupation; }
        public void setOccupation(String occupation) { this.occupation = occupation; }
    }
}