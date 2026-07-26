package com.sarana_NIS.newTown_backend.dto.response.core;

import java.time.LocalDateTime;

public class SettingsResponse {
    private Long id;
    private String schoolName;
    private String logoUrl;
    private String academicYear;
    private String currentTerm;
    private String dateFormat;
    private String timezone;
    private String schoolAddress;
    private String schoolPhone;
    private String schoolEmail;
    private String principalName;
    private Boolean allowParentRegistration;
    private Boolean allowStudentRegistration;
    private Integer maxStudentsPerClass;
    private Boolean enableEmailNotifications;
    private Boolean enableSmsNotifications;
    private String smsProvider;
    private String emailSender;
    private String schoolMotto;
    private String schoolMission;
    private String schoolVision;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // ✅ ADD MISSING FIELDS
    private LocalDateTime lastUpdated;
    private String updatedBy;
    private String version;

    // Constructors
    public SettingsResponse() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(String currentTerm) {
        this.currentTerm = currentTerm;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getSchoolAddress() {
        return schoolAddress;
    }

    public void setSchoolAddress(String schoolAddress) {
        this.schoolAddress = schoolAddress;
    }

    public String getSchoolPhone() {
        return schoolPhone;
    }

    public void setSchoolPhone(String schoolPhone) {
        this.schoolPhone = schoolPhone;
    }

    public String getSchoolEmail() {
        return schoolEmail;
    }

    public void setSchoolEmail(String schoolEmail) {
        this.schoolEmail = schoolEmail;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public Boolean getAllowParentRegistration() {
        return allowParentRegistration;
    }

    public void setAllowParentRegistration(Boolean allowParentRegistration) {
        this.allowParentRegistration = allowParentRegistration;
    }

    public Boolean getAllowStudentRegistration() {
        return allowStudentRegistration;
    }

    public void setAllowStudentRegistration(Boolean allowStudentRegistration) {
        this.allowStudentRegistration = allowStudentRegistration;
    }

    public Integer getMaxStudentsPerClass() {
        return maxStudentsPerClass;
    }

    public void setMaxStudentsPerClass(Integer maxStudentsPerClass) {
        this.maxStudentsPerClass = maxStudentsPerClass;
    }

    public Boolean getEnableEmailNotifications() {
        return enableEmailNotifications;
    }

    public void setEnableEmailNotifications(Boolean enableEmailNotifications) {
        this.enableEmailNotifications = enableEmailNotifications;
    }

    public Boolean getEnableSmsNotifications() {
        return enableSmsNotifications;
    }

    public void setEnableSmsNotifications(Boolean enableSmsNotifications) {
        this.enableSmsNotifications = enableSmsNotifications;
    }

    public String getSmsProvider() {
        return smsProvider;
    }

    public void setSmsProvider(String smsProvider) {
        this.smsProvider = smsProvider;
    }

    public String getEmailSender() {
        return emailSender;
    }

    public void setEmailSender(String emailSender) {
        this.emailSender = emailSender;
    }

    public String getSchoolMotto() {
        return schoolMotto;
    }

    public void setSchoolMotto(String schoolMotto) {
        this.schoolMotto = schoolMotto;
    }

    public String getSchoolMission() {
        return schoolMission;
    }

    public void setSchoolMission(String schoolMission) {
        this.schoolMission = schoolMission;
    }

    public String getSchoolVision() {
        return schoolVision;
    }

    public void setSchoolVision(String schoolVision) {
        this.schoolVision = schoolVision;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // ✅ ADD GETTERS AND SETTERS FOR NEW FIELDS
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}