package com.sarana_NIS.newTown_backend.dto.response.user;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParentDashboardResponse {

    private Long parentId;
    private String parentName;
    private List<ChildDashboard> children = new ArrayList<>();
    private Integer childrenCount;
    private List<RecentMessage> recentMessages = new ArrayList<>();
    private List<UpcomingEvent> upcomingEvents = new ArrayList<>();

    // Constructors
    public ParentDashboardResponse() {}

    public ParentDashboardResponse(Long parentId, String parentName) {
        this.parentId = parentId;
        this.parentName = parentName;
    }

    // Getters and Setters
    public Long getParentId() { return parentId; }
    public void setParentId(Long parentId) { this.parentId = parentId; }

    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }

    public List<ChildDashboard> getChildren() { return children; }
    public void setChildren(List<ChildDashboard> children) { this.children = children; }

    public Integer getChildrenCount() { return childrenCount; }
    public void setChildrenCount(Integer childrenCount) { this.childrenCount = childrenCount; }

    public List<RecentMessage> getRecentMessages() { return recentMessages; }
    public void setRecentMessages(List<RecentMessage> recentMessages) { this.recentMessages = recentMessages; }

    public List<UpcomingEvent> getUpcomingEvents() { return upcomingEvents; }
    public void setUpcomingEvents(List<UpcomingEvent> upcomingEvents) { this.upcomingEvents = upcomingEvents; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ChildDashboard {
        private Long studentId;
        private String studentName;
        private String className;
        private String level;
        private Double gpa;
        private Integer attendanceRate;
        private Integer homeworkPending;
        private Boolean hasOverdueFees;
        private List<RecentScore> recentScores = new ArrayList<>();

        // Constructors
        public ChildDashboard() {}

        public ChildDashboard(Long studentId, String studentName) {
            this.studentId = studentId;
            this.studentName = studentName;
        }

        // Getters and Setters
        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }

        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }

        public Double getGpa() { return gpa; }
        public void setGpa(Double gpa) { this.gpa = gpa; }

        public Integer getAttendanceRate() { return attendanceRate; }
        public void setAttendanceRate(Integer attendanceRate) { this.attendanceRate = attendanceRate; }

        public Integer getHomeworkPending() { return homeworkPending; }
        public void setHomeworkPending(Integer homeworkPending) { this.homeworkPending = homeworkPending; }

        public Boolean getHasOverdueFees() { return hasOverdueFees; }
        public void setHasOverdueFees(Boolean hasOverdueFees) { this.hasOverdueFees = hasOverdueFees; }

        public List<RecentScore> getRecentScores() { return recentScores; }
        public void setRecentScores(List<RecentScore> recentScores) { this.recentScores = recentScores; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RecentScore {
        private String subject;
        private Double score;
        private String grade;
        private LocalDate date;

        // Constructors
        public RecentScore() {}

        // Getters and Setters
        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }

        public Double getScore() { return score; }
        public void setScore(Double score) { this.score = score; }

        public String getGrade() { return grade; }
        public void setGrade(String grade) { this.grade = grade; }

        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RecentMessage {
        private Long id;
        private String sender;
        private String subject;
        private String preview;
        private LocalDateTime sentAt;
        private Boolean isRead;

        // Constructors
        public RecentMessage() {}

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getSender() { return sender; }
        public void setSender(String sender) { this.sender = sender; }

        public String getSubject() { return subject; }
        public void setSubject(String subject) { this.subject = subject; }

        public String getPreview() { return preview; }
        public void setPreview(String preview) { this.preview = preview; }

        public LocalDateTime getSentAt() { return sentAt; }
        public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

        public Boolean getIsRead() { return isRead; }
        public void setIsRead(Boolean isRead) { this.isRead = isRead; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UpcomingEvent {
        private Long id;
        private String title;
        private LocalDate date;
        private String type;
        private String location;

        // Constructors
        public UpcomingEvent() {}

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getLocation() { return location; }
        public void setLocation(String location) { this.location = location; }
    }
}