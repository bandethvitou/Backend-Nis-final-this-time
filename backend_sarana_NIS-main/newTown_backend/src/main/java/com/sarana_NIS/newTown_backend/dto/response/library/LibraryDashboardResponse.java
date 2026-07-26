package com.sarana_NIS.newTown_backend.dto.response.library;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LibraryDashboardResponse {

    private QuickStats quickStats;
    private List<RecentActivity> recentActivities = new ArrayList<>();
    private List<PopularCategory> topCategories = new ArrayList<>();
    private List<OverdueAlert> overdueAlerts = new ArrayList<>();
    private Double borrowingRate;
    private Integer visitorsToday;

    // Constructors
    public LibraryDashboardResponse() {}

    // Getters and Setters
    public QuickStats getQuickStats() { return quickStats; }
    public void setQuickStats(QuickStats quickStats) { this.quickStats = quickStats; }

    public List<RecentActivity> getRecentActivities() { return recentActivities; }
    public void setRecentActivities(List<RecentActivity> recentActivities) { this.recentActivities = recentActivities; }

    public List<PopularCategory> getTopCategories() { return topCategories; }
    public void setTopCategories(List<PopularCategory> topCategories) { this.topCategories = topCategories; }

    public List<OverdueAlert> getOverdueAlerts() { return overdueAlerts; }
    public void setOverdueAlerts(List<OverdueAlert> overdueAlerts) { this.overdueAlerts = overdueAlerts; }

    public Double getBorrowingRate() { return borrowingRate; }
    public void setBorrowingRate(Double borrowingRate) { this.borrowingRate = borrowingRate; }

    public Integer getVisitorsToday() { return visitorsToday; }
    public void setVisitorsToday(Integer visitorsToday) { this.visitorsToday = visitorsToday; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class QuickStats {
        private Integer totalBooks;
        private Integer availableBooks;
        private Integer checkedOutBooks;
        private Integer overdueBooks;
        private Integer totalStudents;
        private Integer studentsWithBooks;

        // Constructors
        public QuickStats() {}

        // Getters and Setters
        public Integer getTotalBooks() { return totalBooks; }
        public void setTotalBooks(Integer totalBooks) { this.totalBooks = totalBooks; }

        public Integer getAvailableBooks() { return availableBooks; }
        public void setAvailableBooks(Integer availableBooks) { this.availableBooks = availableBooks; }

        public Integer getCheckedOutBooks() { return checkedOutBooks; }
        public void setCheckedOutBooks(Integer checkedOutBooks) { this.checkedOutBooks = checkedOutBooks; }

        public Integer getOverdueBooks() { return overdueBooks; }
        public void setOverdueBooks(Integer overdueBooks) { this.overdueBooks = overdueBooks; }

        public Integer getTotalStudents() { return totalStudents; }
        public void setTotalStudents(Integer totalStudents) { this.totalStudents = totalStudents; }

        public Integer getStudentsWithBooks() { return studentsWithBooks; }
        public void setStudentsWithBooks(Integer studentsWithBooks) { this.studentsWithBooks = studentsWithBooks; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class RecentActivity {
        private String type;
        private String description;
        private String user;
        private LocalDateTime timestamp;

        // Constructors
        public RecentActivity() {}

        // Getters and Setters
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public String getUser() { return user; }
        public void setUser(String user) { this.user = user; }

        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PopularCategory {
        private String category;
        private Integer count;
        private Double percentage;

        // Constructors
        public PopularCategory() {}

        // Getters and Setters
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public Double getPercentage() { return percentage; }
        public void setPercentage(Double percentage) { this.percentage = percentage; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OverdueAlert {
        private String bookTitle;
        private String studentName;
        private Integer daysOverdue;
        private String action;

        // Constructors
        public OverdueAlert() {}

        // Getters and Setters
        public String getBookTitle() { return bookTitle; }
        public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public Integer getDaysOverdue() { return daysOverdue; }
        public void setDaysOverdue(Integer daysOverdue) { this.daysOverdue = daysOverdue; }

        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
    }
}