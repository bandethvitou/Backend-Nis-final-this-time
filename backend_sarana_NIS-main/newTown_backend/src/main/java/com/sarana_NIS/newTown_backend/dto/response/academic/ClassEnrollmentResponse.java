package com.sarana_NIS.newTown_backend.dto.response.academic;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClassEnrollmentResponse {

    private Long classId;
    private String className;
    private String level;
    private String grade;
    private String teacherName;
    private String room;
    private Integer capacity;
    private Integer currentEnrollment;
    private Integer availableSpots;
    private Double occupancyRate;
    private String status;
    private List<StudentEnrollment> students = new ArrayList<>();
    private List<EnrollmentTrend> trends = new ArrayList<>();
    private List<GenderStat> genderStats = new ArrayList<>();

    // Constructors
    public ClassEnrollmentResponse() {}

    public ClassEnrollmentResponse(Long classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    // Getters and Setters
    public Long getClassId() { return classId; }
    public void setClassId(Long classId) { this.classId = classId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }

    public String getRoom() { return room; }
    public void setRoom(String room) { this.room = room; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getCurrentEnrollment() { return currentEnrollment; }
    public void setCurrentEnrollment(Integer currentEnrollment) { this.currentEnrollment = currentEnrollment; }

    public Integer getAvailableSpots() { return availableSpots; }
    public void setAvailableSpots(Integer availableSpots) { this.availableSpots = availableSpots; }

    public Double getOccupancyRate() { return occupancyRate; }
    public void setOccupancyRate(Double occupancyRate) { this.occupancyRate = occupancyRate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<StudentEnrollment> getStudents() { return students; }
    public void setStudents(List<StudentEnrollment> students) { this.students = students; }

    public List<EnrollmentTrend> getTrends() { return trends; }
    public void setTrends(List<EnrollmentTrend> trends) { this.trends = trends; }

    public List<GenderStat> getGenderStats() { return genderStats; }
    public void setGenderStats(List<GenderStat> genderStats) { this.genderStats = genderStats; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StudentEnrollment {
        private Long studentId;
        private String studentName;
        private String gender;
        private LocalDate enrollmentDate;
        private String status;
        private Double gpa;
        private Integer attendanceRate;
        private String parentName;
        private String parentPhone;

        // Constructors
        public StudentEnrollment() {}

        public StudentEnrollment(Long studentId, String studentName) {
            this.studentId = studentId;
            this.studentName = studentName;
        }

        // Getters and Setters
        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }

        public LocalDate getEnrollmentDate() { return enrollmentDate; }
        public void setEnrollmentDate(LocalDate enrollmentDate) { this.enrollmentDate = enrollmentDate; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public Double getGpa() { return gpa; }
        public void setGpa(Double gpa) { this.gpa = gpa; }

        public Integer getAttendanceRate() { return attendanceRate; }
        public void setAttendanceRate(Integer attendanceRate) { this.attendanceRate = attendanceRate; }

        public String getParentName() { return parentName; }
        public void setParentName(String parentName) { this.parentName = parentName; }

        public String getParentPhone() { return parentPhone; }
        public void setParentPhone(String parentPhone) { this.parentPhone = parentPhone; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EnrollmentTrend {
        private String period;
        private Integer count;
        private Integer newStudents;
        private Integer dropouts;

        // Constructors
        public EnrollmentTrend() {}

        // Getters and Setters
        public String getPeriod() { return period; }
        public void setPeriod(String period) { this.period = period; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public Integer getNewStudents() { return newStudents; }
        public void setNewStudents(Integer newStudents) { this.newStudents = newStudents; }

        public Integer getDropouts() { return dropouts; }
        public void setDropouts(Integer dropouts) { this.dropouts = dropouts; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class GenderStat {
        private String gender;
        private Integer count;
        private Double percentage;

        // Constructors
        public GenderStat() {}

        // Getters and Setters
        public String getGender() { return gender; }
        public void setGender(String gender) { this.gender = gender; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public Double getPercentage() { return percentage; }
        public void setPercentage(Double percentage) { this.percentage = percentage; }
    }
}