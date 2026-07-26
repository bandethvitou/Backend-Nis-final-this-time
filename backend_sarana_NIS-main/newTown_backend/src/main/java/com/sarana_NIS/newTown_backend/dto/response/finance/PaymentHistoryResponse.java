package com.sarana_NIS.newTown_backend.dto.response.finance;

import com.sarana_NIS.newTown_backend.model.enums.PaymentMethod;
import com.sarana_NIS.newTown_backend.model.enums.PaymentStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryResponse {
    private Long studentId;
    private String studentName;
    private List<PaymentHistoryItem> payments = new ArrayList<>();
    private Double totalPaid;
    private Double totalPending;
    private Double totalOverdue;

    // Constructors
    public PaymentHistoryResponse() {
        this.totalPaid = 0.0;
        this.totalPending = 0.0;
        this.totalOverdue = 0.0;
    }

    // Getters and Setters
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public List<PaymentHistoryItem> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentHistoryItem> payments) {
        this.payments = payments;
    }

    public Double getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(Double totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Double getTotalPending() {
        return totalPending;
    }

    public void setTotalPending(Double totalPending) {
        this.totalPending = totalPending;
    }

    public Double getTotalOverdue() {
        return totalOverdue;
    }

    public void setTotalOverdue(Double totalOverdue) {
        this.totalOverdue = totalOverdue;
    }

    // Inner class for payment history items
    public static class PaymentHistoryItem {
        private Long id;
        private Double amount;
        private LocalDate paymentDate;
        private PaymentMethod paymentMethod;
        private String description;
        private PaymentStatus status;
        private String referenceNumber;

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }

        public LocalDate getPaymentDate() {
            return paymentDate;
        }

        public void setPaymentDate(LocalDate paymentDate) {
            this.paymentDate = paymentDate;
        }

        public PaymentMethod getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public PaymentStatus getStatus() {
            return status;
        }

        public void setStatus(PaymentStatus status) {
            this.status = status;
        }

        public String getReferenceNumber() {
            return referenceNumber;
        }

        public void setReferenceNumber(String referenceNumber) {
            this.referenceNumber = referenceNumber;
        }
    }
}