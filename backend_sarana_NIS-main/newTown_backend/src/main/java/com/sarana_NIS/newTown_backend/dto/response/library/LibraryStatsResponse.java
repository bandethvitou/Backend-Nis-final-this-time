package com.sarana_NIS.newTown_backend.dto.response.library;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LibraryStatsResponse {

    private Integer totalBooks;
    private Integer availableBooks;
    private Integer checkedOutBooks;
    private Integer overdueBooks;
    private List<CategoryStat> categories = new ArrayList<>();
    private List<PopularBook> mostBorrowed = new ArrayList<>();
    private List<WeeklyCheckout> weeklyCheckouts = new ArrayList<>();
    private Double borrowingRate;
    private Integer totalStudentsWithBooks;
    private Integer averageBooksPerStudent;

    // Constructors
    public LibraryStatsResponse() {}

    // Getters and Setters
    public Integer getTotalBooks() { return totalBooks; }
    public void setTotalBooks(Integer totalBooks) { this.totalBooks = totalBooks; }

    public Integer getAvailableBooks() { return availableBooks; }
    public void setAvailableBooks(Integer availableBooks) { this.availableBooks = availableBooks; }

    public Integer getCheckedOutBooks() { return checkedOutBooks; }
    public void setCheckedOutBooks(Integer checkedOutBooks) { this.checkedOutBooks = checkedOutBooks; }

    public Integer getOverdueBooks() { return overdueBooks; }
    public void setOverdueBooks(Integer overdueBooks) { this.overdueBooks = overdueBooks; }

    public List<CategoryStat> getCategories() { return categories; }
    public void setCategories(List<CategoryStat> categories) { this.categories = categories; }

    public List<PopularBook> getMostBorrowed() { return mostBorrowed; }
    public void setMostBorrowed(List<PopularBook> mostBorrowed) { this.mostBorrowed = mostBorrowed; }

    public List<WeeklyCheckout> getWeeklyCheckouts() { return weeklyCheckouts; }
    public void setWeeklyCheckouts(List<WeeklyCheckout> weeklyCheckouts) { this.weeklyCheckouts = weeklyCheckouts; }

    public Double getBorrowingRate() { return borrowingRate; }
    public void setBorrowingRate(Double borrowingRate) { this.borrowingRate = borrowingRate; }

    public Integer getTotalStudentsWithBooks() { return totalStudentsWithBooks; }
    public void setTotalStudentsWithBooks(Integer totalStudentsWithBooks) { this.totalStudentsWithBooks = totalStudentsWithBooks; }

    public Integer getAverageBooksPerStudent() { return averageBooksPerStudent; }
    public void setAverageBooksPerStudent(Integer averageBooksPerStudent) { this.averageBooksPerStudent = averageBooksPerStudent; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CategoryStat {
        private String category;
        private Integer count;
        private Integer available;
        private Integer checkedOut;

        // Constructors
        public CategoryStat() {}

        // Getters and Setters
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }

        public Integer getAvailable() { return available; }
        public void setAvailable(Integer available) { this.available = available; }

        public Integer getCheckedOut() { return checkedOut; }
        public void setCheckedOut(Integer checkedOut) { this.checkedOut = checkedOut; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PopularBook {
        private String title;
        private String author;
        private Integer timesBorrowed;
        private String isbn;

        // Constructors
        public PopularBook() {}

        // Getters and Setters
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }

        public Integer getTimesBorrowed() { return timesBorrowed; }
        public void setTimesBorrowed(Integer timesBorrowed) { this.timesBorrowed = timesBorrowed; }

        public String getIsbn() { return isbn; }
        public void setIsbn(String isbn) { this.isbn = isbn; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class WeeklyCheckout {
        private String week;
        private Integer checkouts;
        private Integer returns;

        // Constructors
        public WeeklyCheckout() {}

        // Getters and Setters
        public String getWeek() { return week; }
        public void setWeek(String week) { this.week = week; }

        public Integer getCheckouts() { return checkouts; }
        public void setCheckouts(Integer checkouts) { this.checkouts = checkouts; }

        public Integer getReturns() { return returns; }
        public void setReturns(Integer returns) { this.returns = returns; }
    }
}