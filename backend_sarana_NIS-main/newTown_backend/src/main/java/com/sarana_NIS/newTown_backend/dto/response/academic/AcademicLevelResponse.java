package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AcademicLevelResponse {

    private Long id;
    private String name;
    private String code;
    private Integer minAge;
    private Integer maxAge;
    private Integer sequenceOrder;
    private Integer classCount;
    private Integer studentCount;
    private List<ClassSummary> classes = new ArrayList<>();

    // Constructors
    public AcademicLevelResponse() {}

    public AcademicLevelResponse(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public Integer getMinAge() { return minAge; }
    public void setMinAge(Integer minAge) { this.minAge = minAge; }

    public Integer getMaxAge() { return maxAge; }
    public void setMaxAge(Integer maxAge) { this.maxAge = maxAge; }

    public Integer getSequenceOrder() { return sequenceOrder; }
    public void setSequenceOrder(Integer sequenceOrder) { this.sequenceOrder = sequenceOrder; }

    public Integer getClassCount() { return classCount; }
    public void setClassCount(Integer classCount) { this.classCount = classCount; }

    public Integer getStudentCount() { return studentCount; }
    public void setStudentCount(Integer studentCount) { this.studentCount = studentCount; }

    public List<ClassSummary> getClasses() { return classes; }
    public void setClasses(List<ClassSummary> classes) { this.classes = classes; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ClassSummary {
        private Long id;
        private String name;
        private String grade;
        private Integer studentCount;
        private String teacherName;

        // Constructors
        public ClassSummary() {}

        public ClassSummary(Long id, String name) {
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

        public Integer getStudentCount() { return studentCount; }
        public void setStudentCount(Integer studentCount) { this.studentCount = studentCount; }

        public String getTeacherName() { return teacherName; }
        public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    }
}