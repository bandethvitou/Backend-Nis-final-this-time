package com.sarana_NIS.newTown_backend.model.entity.admissions;

import com.sarana_NIS.newTown_backend.model.entity.BaseEntity;
import com.sarana_NIS.newTown_backend.model.entity.academic.Class;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.ApplicationStatus;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "applications")
public class Application extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ✅ This field exists

    @Column(name = "student_name", nullable = false, length = 100)
    private String studentName;

    @Column(name = "parent_name", nullable = false, length = 100)
    private String parentName;

    @Column(name = "parent_phone", nullable = false, length = 20)
    private String parentPhone;

    @Column(name = "parent_email", length = 100)
    private String parentEmail;

    @Column(name = "dob")
    private LocalDate dob;

    @Enumerated(EnumType.STRING)
    private Level level;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class targetClass;  // ✅ This is the field name

    @Column(columnDefinition = "TEXT")
    private String address;

    @Column(name = "previous_school")
    private String previousSchool;

    @Column(name = "medical_notes", columnDefinition = "TEXT")
    private String medicalNotes;

    @Column(columnDefinition = "JSONB")
    private String documents;

    @Column(name = "inquiry_date")
    private LocalDateTime inquiryDate;

    @Column(name = "tour_date")
    private LocalDateTime tourDate;

    @Column(name = "submitted_date")
    private LocalDateTime submittedDate;

    @Column(name = "reviewed_date")
    private LocalDateTime reviewedDate;

    @Column(name = "approved_date")
    private LocalDateTime approvedDate;

    @Column(name = "invoice_sent_date")
    private LocalDateTime invoiceSentDate;

    @Column(name = "payment_received_date")
    private LocalDateTime paymentReceivedDate;

    @Column(name = "enrolled_date")
    private LocalDateTime enrolledDate;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.DRAFT;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy;

    @Column(name = "review_notes", columnDefinition = "TEXT")
    private String reviewNotes;

    @Column(name = "rejection_reason", columnDefinition = "TEXT")
    private String rejectionReason;

    @Column(name = "documents_requested", columnDefinition = "TEXT")
    private String documentsRequested;

    // Constructors
    public Application() {}

    public Application(String studentName, String parentName) {
        this.studentName = studentName;
        this.parentName = parentName;
    }

    // Getters and Setters
    public Long getId() {  // ✅ This getter exists
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public String getParentName() { return parentName; }
    public void setParentName(String parentName) { this.parentName = parentName; }

    public String getParentPhone() { return parentPhone; }
    public void setParentPhone(String parentPhone) { this.parentPhone = parentPhone; }

    public String getParentEmail() { return parentEmail; }
    public void setParentEmail(String parentEmail) { this.parentEmail = parentEmail; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public Class getTargetClass() { return targetClass; }
    public void setTargetClass(Class targetClass) { this.targetClass = targetClass; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPreviousSchool() { return previousSchool; }
    public void setPreviousSchool(String previousSchool) { this.previousSchool = previousSchool; }

    public String getMedicalNotes() { return medicalNotes; }
    public void setMedicalNotes(String medicalNotes) { this.medicalNotes = medicalNotes; }

    public String getDocuments() { return documents; }
    public void setDocuments(String documents) { this.documents = documents; }

    public LocalDateTime getInquiryDate() { return inquiryDate; }
    public void setInquiryDate(LocalDateTime inquiryDate) { this.inquiryDate = inquiryDate; }

    public LocalDateTime getTourDate() { return tourDate; }
    public void setTourDate(LocalDateTime tourDate) { this.tourDate = tourDate; }

    public LocalDateTime getSubmittedDate() { return submittedDate; }
    public void setSubmittedDate(LocalDateTime submittedDate) { this.submittedDate = submittedDate; }

    public LocalDateTime getReviewedDate() { return reviewedDate; }
    public void setReviewedDate(LocalDateTime reviewedDate) { this.reviewedDate = reviewedDate; }

    public LocalDateTime getApprovedDate() { return approvedDate; }
    public void setApprovedDate(LocalDateTime approvedDate) { this.approvedDate = approvedDate; }

    public LocalDateTime getInvoiceSentDate() { return invoiceSentDate; }
    public void setInvoiceSentDate(LocalDateTime invoiceSentDate) { this.invoiceSentDate = invoiceSentDate; }

    public LocalDateTime getPaymentReceivedDate() { return paymentReceivedDate; }
    public void setPaymentReceivedDate(LocalDateTime paymentReceivedDate) { this.paymentReceivedDate = paymentReceivedDate; }

    public LocalDateTime getEnrolledDate() { return enrolledDate; }
    public void setEnrolledDate(LocalDateTime enrolledDate) { this.enrolledDate = enrolledDate; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public User getReviewedBy() { return reviewedBy; }
    public void setReviewedBy(User reviewedBy) { this.reviewedBy = reviewedBy; }

    public String getReviewNotes() { return reviewNotes; }
    public void setReviewNotes(String reviewNotes) { this.reviewNotes = reviewNotes; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public String getDocumentsRequested() { return documentsRequested; }
    public void setDocumentsRequested(String documentsRequested) { this.documentsRequested = documentsRequested; }
}