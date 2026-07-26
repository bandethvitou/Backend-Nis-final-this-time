package com.sarana_NIS.newTown_backend.dto.response.library;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDetailResponse {

    private BookResponse book;
    private List<CheckoutHistory> checkoutHistory = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private Boolean isAvailable;
    private Integer availableCopies;
    private String locationDetails;

    // Constructors
    public BookDetailResponse() {}

    public BookDetailResponse(BookResponse book) {
        this.book = book;
    }

    // Getters and Setters
    public BookResponse getBook() { return book; }
    public void setBook(BookResponse book) { this.book = book; }

    public List<CheckoutHistory> getCheckoutHistory() { return checkoutHistory; }
    public void setCheckoutHistory(List<CheckoutHistory> checkoutHistory) { this.checkoutHistory = checkoutHistory; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public Integer getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(Integer availableCopies) { this.availableCopies = availableCopies; }

    public String getLocationDetails() { return locationDetails; }
    public void setLocationDetails(String locationDetails) { this.locationDetails = locationDetails; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CheckoutHistory {
        private Long id;
        private String studentName;
        private String studentId;
        private String studentClass;
        private LocalDate checkoutDate;
        private LocalDate dueDate;
        private LocalDate returnDate;
        private String status;
        private Boolean isOverdue;
        private Integer daysOverdue;

        // Constructors
        public CheckoutHistory() {}

        public CheckoutHistory(Long id, String studentName) {
            this.id = id;
            this.studentName = studentName;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public String getStudentId() { return studentId; }
        public void setStudentId(String studentId) { this.studentId = studentId; }

        public String getStudentClass() { return studentClass; }
        public void setStudentClass(String studentClass) { this.studentClass = studentClass; }

        public LocalDate getCheckoutDate() { return checkoutDate; }
        public void setCheckoutDate(LocalDate checkoutDate) { this.checkoutDate = checkoutDate; }

        public LocalDate getDueDate() { return dueDate; }
        public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

        public LocalDate getReturnDate() { return returnDate; }
        public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public Boolean getIsOverdue() { return isOverdue; }
        public void setIsOverdue(Boolean isOverdue) { this.isOverdue = isOverdue; }

        public Integer getDaysOverdue() { return daysOverdue; }
        public void setDaysOverdue(Integer daysOverdue) { this.daysOverdue = daysOverdue; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Review {
        private Long id;
        private String studentName;
        private String studentId;
        private Integer rating;
        private String comment;
        private LocalDateTime createdAt;
        private Boolean isVerifiedPurchase;

        // Constructors
        public Review() {}

        public Review(Long id, String studentName, Integer rating) {
            this.id = id;
            this.studentName = studentName;
            this.rating = rating;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public String getStudentId() { return studentId; }
        public void setStudentId(String studentId) { this.studentId = studentId; }

        public Integer getRating() { return rating; }
        public void setRating(Integer rating) { this.rating = rating; }

        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }

        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

        public Boolean getIsVerifiedPurchase() { return isVerifiedPurchase; }
        public void setIsVerifiedPurchase(Boolean isVerifiedPurchase) { this.isVerifiedPurchase = isVerifiedPurchase; }
    }
}