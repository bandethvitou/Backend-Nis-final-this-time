package com.sarana_NIS.newTown_backend.dto.response.library;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PopularBookResponse {

    private List<BookPopularity> mostBorrowed = new ArrayList<>();
    private List<CategoryPopularity> popularCategories = new ArrayList<>();
    private List<LevelPopularity> byLevel = new ArrayList<>();
    private List<MonthlyTrend> trends = new ArrayList<>();

    // Constructors
    public PopularBookResponse() {}

    // Getters and Setters
    public List<BookPopularity> getMostBorrowed() { return mostBorrowed; }
    public void setMostBorrowed(List<BookPopularity> mostBorrowed) { this.mostBorrowed = mostBorrowed; }

    public List<CategoryPopularity> getPopularCategories() { return popularCategories; }
    public void setPopularCategories(List<CategoryPopularity> popularCategories) { this.popularCategories = popularCategories; }

    public List<LevelPopularity> getByLevel() { return byLevel; }
    public void setByLevel(List<LevelPopularity> byLevel) { this.byLevel = byLevel; }

    public List<MonthlyTrend> getTrends() { return trends; }
    public void setTrends(List<MonthlyTrend> trends) { this.trends = trends; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BookPopularity {
        private Long id;
        private String title;
        private String author;
        private String category;
        private Integer timesBorrowed;
        private Integer currentCheckouts;
        private Double popularityScore;
        private String trend;

        // Constructors
        public BookPopularity() {}

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public Integer getTimesBorrowed() { return timesBorrowed; }
        public void setTimesBorrowed(Integer timesBorrowed) { this.timesBorrowed = timesBorrowed; }

        public Integer getCurrentCheckouts() { return currentCheckouts; }
        public void setCurrentCheckouts(Integer currentCheckouts) { this.currentCheckouts = currentCheckouts; }

        public Double getPopularityScore() { return popularityScore; }
        public void setPopularityScore(Double popularityScore) { this.popularityScore = popularityScore; }

        public String getTrend() { return trend; }
        public void setTrend(String trend) { this.trend = trend; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CategoryPopularity {
        private String category;
        private Integer bookCount;
        private Integer borrowCount;
        private Double averageRating;

        // Constructors
        public CategoryPopularity() {}

        // Getters and Setters
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public Integer getBookCount() { return bookCount; }
        public void setBookCount(Integer bookCount) { this.bookCount = bookCount; }

        public Integer getBorrowCount() { return borrowCount; }
        public void setBorrowCount(Integer borrowCount) { this.borrowCount = borrowCount; }

        public Double getAverageRating() { return averageRating; }
        public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class LevelPopularity {
        private String level;
        private Integer borrowCount;
        private Integer studentCount;
        private Double borrowPerStudent;

        // Constructors
        public LevelPopularity() {}

        // Getters and Setters
        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }

        public Integer getBorrowCount() { return borrowCount; }
        public void setBorrowCount(Integer borrowCount) { this.borrowCount = borrowCount; }

        public Integer getStudentCount() { return studentCount; }
        public void setStudentCount(Integer studentCount) { this.studentCount = studentCount; }

        public Double getBorrowPerStudent() { return borrowPerStudent; }
        public void setBorrowPerStudent(Double borrowPerStudent) { this.borrowPerStudent = borrowPerStudent; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MonthlyTrend {
        private String month;
        private Integer borrows;
        private Integer returns;
        private Integer overdue;

        // Constructors
        public MonthlyTrend() {}

        // Getters and Setters
        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }

        public Integer getBorrows() { return borrows; }
        public void setBorrows(Integer borrows) { this.borrows = borrows; }

        public Integer getReturns() { return returns; }
        public void setReturns(Integer returns) { this.returns = returns; }

        public Integer getOverdue() { return overdue; }
        public void setOverdue(Integer overdue) { this.overdue = overdue; }
    }
}