package com.sarana_NIS.newTown_backend.dto.response.admissions;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdmissionsDashboardResponse {

    private Integer totalApplications;
    private Integer pendingReview;
    private Integer approvedToday;
    private Integer toursToday;
    private List<ApplicationSummary> recentApplications = new ArrayList<>();
    private List<TourSummary> upcomingTours = new ArrayList<>();
    private Double conversionRate;
    private Integer vacancies;
    private Integer waitlistCount;

    // Constructors
    public AdmissionsDashboardResponse() {}

    public AdmissionsDashboardResponse(Integer totalApplications, Integer pendingReview) {
        this.totalApplications = totalApplications;
        this.pendingReview = pendingReview;
    }

    // Getters and Setters
    public Integer getTotalApplications() { return totalApplications; }
    public void setTotalApplications(Integer totalApplications) { this.totalApplications = totalApplications; }

    public Integer getPendingReview() { return pendingReview; }
    public void setPendingReview(Integer pendingReview) { this.pendingReview = pendingReview; }

    public Integer getApprovedToday() { return approvedToday; }
    public void setApprovedToday(Integer approvedToday) { this.approvedToday = approvedToday; }

    public Integer getToursToday() { return toursToday; }
    public void setToursToday(Integer toursToday) { this.toursToday = toursToday; }

    public List<ApplicationSummary> getRecentApplications() { return recentApplications; }
    public void setRecentApplications(List<ApplicationSummary> recentApplications) { this.recentApplications = recentApplications; }

    public List<TourSummary> getUpcomingTours() { return upcomingTours; }
    public void setUpcomingTours(List<TourSummary> upcomingTours) { this.upcomingTours = upcomingTours; }

    public Double getConversionRate() { return conversionRate; }
    public void setConversionRate(Double conversionRate) { this.conversionRate = conversionRate; }

    public Integer getVacancies() { return vacancies; }
    public void setVacancies(Integer vacancies) { this.vacancies = vacancies; }

    public Integer getWaitlistCount() { return waitlistCount; }
    public void setWaitlistCount(Integer waitlistCount) { this.waitlistCount = waitlistCount; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ApplicationSummary {
        private Long id;
        private String studentName;
        private String parentName;
        private String level;
        private String status;
        private LocalDate submittedDate;
        private String submittedBy;

        // Constructors
        public ApplicationSummary() {}

        public ApplicationSummary(Long id, String studentName) {
            this.id = id;
            this.studentName = studentName;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public String getParentName() { return parentName; }
        public void setParentName(String parentName) { this.parentName = parentName; }

        public String getLevel() { return level; }
        public void setLevel(String level) { this.level = level; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public LocalDate getSubmittedDate() { return submittedDate; }
        public void setSubmittedDate(LocalDate submittedDate) { this.submittedDate = submittedDate; }

        public String getSubmittedBy() { return submittedBy; }
        public void setSubmittedBy(String submittedBy) { this.submittedBy = submittedBy; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class TourSummary {
        private Long id;
        private String parentName;
        private String studentName;
        private LocalDate tourDate;
        private String time;
        private String status;

        // Constructors
        public TourSummary() {}

        public TourSummary(Long id, String parentName) {
            this.id = id;
            this.parentName = parentName;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getParentName() { return parentName; }
        public void setParentName(String parentName) { this.parentName = parentName; }

        public String getStudentName() { return studentName; }
        public void setStudentName(String studentName) { this.studentName = studentName; }

        public LocalDate getTourDate() { return tourDate; }
        public void setTourDate(LocalDate tourDate) { this.tourDate = tourDate; }

        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }
}