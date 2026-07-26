package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttendanceSummaryResponse {

    // Class/Subject Info
    private String className;
    private String subjectName;
    private Long classSubjectId;

    // Student Info
    private Long studentId;
    private String studentName;
    private String studentClass;

    // Summary Stats
    private Integer totalStudents;
    private Integer presentStudents;
    private Integer absentStudents;
    private Integer lateStudents;
    private Integer excusedStudents;
    private Double overallAttendanceRate;

    // Student Stats (Nested)
    private StudentAttendance studentStats;
    private List<StudentAttendance> studentAttendances = new ArrayList<>();

    // Constructors
    public AttendanceSummaryResponse() {}

    public AttendanceSummaryResponse(Integer totalStudents, Double overallAttendanceRate) {
        this.totalStudents = totalStudents;
        this.overallAttendanceRate = overallAttendanceRate;
    }

    // Getters and Setters
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public Long getClassSubjectId() { return classSubjectId; }
    public void setClassSubjectId(Long classSubjectId) { this.classSubjectId = classSubjectId; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentClass() { return studentClass; }
    public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

    public Integer getTotalStudents() { return totalStudents; }
    public void setTotalStudents(Integer totalStudents) { this.totalStudents = totalStudents; }

    public Integer getPresentStudents() { return presentStudents; }
    public void setPresentStudents(Integer presentStudents) { this.presentStudents = presentStudents; }

    public Integer getAbsentStudents() { return absentStudents; }
    public void setAbsentStudents(Integer absentStudents) { this.absentStudents = absentStudents; }

    public Integer getLateStudents() { return lateStudents; }
    public void setLateStudents(Integer lateStudents) { this.lateStudents = lateStudents; }

    public Integer getExcusedStudents() { return excusedStudents; }
    public void setExcusedStudents(Integer excusedStudents) { this.excusedStudents = excusedStudents; }

    public Double getOverallAttendanceRate() { return overallAttendanceRate; }
    public void setOverallAttendanceRate(Double overallAttendanceRate) { this.overallAttendanceRate = overallAttendanceRate; }

    public StudentAttendance getStudentStats() { return studentStats; }
    public void setStudentStats(StudentAttendance studentStats) { this.studentStats = studentStats; }

    public List<StudentAttendance> getStudentAttendances() { return studentAttendances; }
    public void setStudentAttendances(List<StudentAttendance> studentAttendances) { this.studentAttendances = studentAttendances; }

    // Inner class
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StudentAttendance {
        private Long studentId;
        private String studentName;
        private String studentClass;
        private Integer presentDays;
        private Integer absentDays;
        private Integer lateDays;
        private Integer excusedDays;
        private Double attendanceRate;
        private String status;

        // Constructors
        public StudentAttendance() {}

        public StudentAttendance(Long studentId, String studentName) {
            this.studentId = studentId;
            this.studentName = studentName;
        }

        // Getters and Setters
        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public String getStudentClass() { return studentClass; }
        public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

        public Integer getPresentDays() { return presentDays; }
        public void setPresentDays(Integer presentDays) { this.presentDays = presentDays; }

        public Integer getAbsentDays() { return absentDays; }
        public void setAbsentDays(Integer absentDays) { this.absentDays = absentDays; }

        public Integer getLateDays() { return lateDays; }
        public void setLateDays(Integer lateDays) { this.lateDays = lateDays; }

        public Integer getExcusedDays() { return excusedDays; }
        public void setExcusedDays(Integer excusedDays) { this.excusedDays = excusedDays; }

        public Double getAttendanceRate() { return attendanceRate; }
        public void setAttendanceRate(Double attendanceRate) { this.attendanceRate = attendanceRate; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}