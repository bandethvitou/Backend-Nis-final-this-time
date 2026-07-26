package com.sarana_NIS.newTown_backend.dto.response.library;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OverdueBookResponse {

    private Long checkoutId;
    private String bookTitle;
    private String bookIsbn;
    private String studentName;
    private String studentId;
    private String className;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private Integer daysOverdue;
    private BigDecimal fineAmount;
    private Boolean finePaid;
    private String parentName;
    private String parentPhone;
    private String parentEmail;
    private Integer reminderCount;
    private LocalDateTime lastReminderSent;
    private String status;

    // Constructors
    public OverdueBookResponse() {}

    public OverdueBookResponse(Long checkoutId, String bookTitle, String studentName) {
        this.checkoutId = checkoutId;
        this.bookTitle = bookTitle;
        this.studentName = studentName;
    }

    // Getters and Setters
    public Long getCheckoutId() { return checkoutId; }
    public void setCheckoutId(Long checkoutId) { this.checkoutId = checkoutId; }

    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }

    public String getBookIsbn() { return bookIsbn; }
    public void setBookIsbn(String bookIsbn) { this.bookIsbn = bookIsbn; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public LocalDate getCheckoutDate() { return checkoutDate; }
    public void setCheckoutDate(LocalDate checkoutDate) { this.checkoutDate = checkoutDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Integer getDaysOverdue() { return daysOverdue; }
    public void setDaysOverdue(Integer daysOverdue) { this.daysOverdue = daysOverdue; }

    public BigDecimal getFineAmount() { return fineAmount; }
    public void setFineAmount(BigDecimal fineAmount) { this.fineAmount = fineAmount; }

    public Boolean getFinePaid() { return finePaid; }
    public void setFinePaid(Boolean finePaid) { this.finePaid = finePaid; }

    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }

    public String getParentPhone() { return parentPhone; }
    public void setParentPhone(String parentPhone) { this.parentPhone = parentPhone; }

    public String getParentEmail() { return parentEmail; }
    public void setParentEmail(String parentEmail) { this.parentEmail = parentEmail; }

    public Integer getReminderCount() { return reminderCount; }
    public void setReminderCount(Integer reminderCount) { this.reminderCount = reminderCount; }

    public LocalDateTime getLastReminderSent() { return lastReminderSent; }
    public void setLastReminderSent(LocalDateTime lastReminderSent) { this.lastReminderSent = lastReminderSent; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}