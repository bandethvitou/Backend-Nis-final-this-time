package com.sarana_NIS.newTown_backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserProfileResponse {

    private UserResponse user;
    private StudentProfile student;
    private StaffProfile staff;
    private ParentProfile parent;
    private List<String> permissions = new ArrayList<>();
    private List<String> dashboardAccess = new ArrayList<>();
    private String primaryRole;
    private List<String> allRoles = new ArrayList<>();

    // Constructors
    public UserProfileResponse() {}

    public UserProfileResponse(UserResponse user) {
        this.user = user;
    }

    // Getters and Setters
    public UserResponse getUser() { return user; }
    public void setUser(UserResponse user) { this.user = user; }

    public StudentProfile getStudent() { return student; }
    public void setStudent(StudentProfile student) { this.student = student; }

    public StaffProfile getStaff() { return staff; }
    public void setStaff(StaffProfile staff) { this.staff = staff; }

    public ParentProfile getParent() { return parent; }
    public void setParent(ParentProfile parent) { this.parent = parent; }

    public List<String> getPermissions() { return permissions; }
    public void setPermissions(List<String> permissions) { this.permissions = permissions; }

    public List<String> getDashboardAccess() { return dashboardAccess; }
    public void setDashboardAccess(List<String> dashboardAccess) { this.dashboardAccess = dashboardAccess; }

    public String getPrimaryRole() { return primaryRole; }
    public void setPrimaryRole(String primaryRole) { this.primaryRole = primaryRole; }

    public List<String> getAllRoles() { return allRoles; }
    public void setAllRoles(List<String> allRoles) { this.allRoles = allRoles; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StudentProfile {
        private String studentId;
        private String level;
        private String className;
        private Double gpa;
        private Integer attendancePercentage;
        private Integer rank;
        private String status;

        // Constructors
        public StudentProfile() {}

        public StudentProfile(String studentId) {
            this.studentId = studentId;
        }

        // Getters and Setters
        public String getStudentId() { return studentId; }
        public void setStudentId(String studentId) { this.studentId = studentId; }

        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }

        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }

        public Double getGpa() { return gpa; }
        public void setGpa(Double gpa) { this.gpa = gpa; }

        public Integer getAttendancePercentage() { return attendancePercentage; }
        public void setAttendancePercentage(Integer attendancePercentage) { this.attendancePercentage = attendancePercentage; }

        public Integer getRank() { return rank; }
        public void setRank(Integer rank) { this.rank = rank; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class StaffProfile {
        private String staffId;
        private String department;
        private String position;
        private List<String> positions = new ArrayList<>();
        private String shift;
        private LocalDate joinDate;

        // Constructors
        public StaffProfile() {}

        public StaffProfile(String staffId) {
            this.staffId = staffId;
        }

        // Getters and Setters
        public String getStaffId() { return staffId; }
        public void setStaffId(String staffId) { this.staffId = staffId; }

        public String getDepartment() { return department; }
        public void setDepartment(String department) { this.department = department; }

        public String getPosition() { return position; }
        public void setPosition(String position) { this.position = position; }

        public List<String> getPositions() { return positions; }
        public void setPositions(List<String> positions) { this.positions = positions; }

        public String getShift() { return shift; }
        public void setShift(String shift) { this.shift = shift; }

        public LocalDate getJoinDate() { return joinDate; }
        public void setJoinDate(LocalDate joinDate) { this.joinDate = joinDate; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ParentProfile {
        private List<ChildInfo> children = new ArrayList<>();
        private Integer childrenCount;

        // Constructors
        public ParentProfile() {}

        // Getters and Setters
        public List<ChildInfo> getChildren() { return children; }
        public void setChildren(List<ChildInfo> children) { this.children = children; }

        public Integer getChildrenCount() { return childrenCount; }
        public void setChildrenCount(Integer childrenCount) { this.childrenCount = childrenCount; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ChildInfo {
        private Long id;
        private String name;
        private String level;
        private String className;

        // Constructors
        public ChildInfo() {}

        public ChildInfo(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }

        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }
    }
}