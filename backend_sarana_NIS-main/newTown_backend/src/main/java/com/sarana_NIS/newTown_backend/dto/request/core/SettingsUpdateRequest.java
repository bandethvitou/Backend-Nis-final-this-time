package com.sarana_NIS.newTown_backend.dto.request.core;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class SettingsUpdateRequest {

    @Size(max = 200, message = "School name must be less than 200 characters")
    private String schoolName;

    private MultipartFile logo;

    @Size(max = 20, message = "Academic year must be less than 20 characters")
    private String academicYear;

    @Size(max = 50, message = "Term must be less than 50 characters")
    private String currentTerm;

    @Pattern(regexp = "^(dd/MM/yyyy|MM/dd/yyyy|yyyy-MM-dd)$",
            message = "Date format must be dd/MM/yyyy, MM/dd/yyyy, or yyyy-MM-dd")
    private String dateFormat;

    @Size(max = 50, message = "Timezone must be less than 50 characters")
    private String timezone;

    @Size(max = 255, message = "Address too long")
    private String schoolAddress;

    @Pattern(regexp = "^[0-9\\-\\+]{10,15}$", message = "Invalid phone number")
    private String schoolPhone;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email must be less than 100 characters")
    private String schoolEmail;

    @Size(max = 100, message = "Principal name must be less than 100 characters")
    private String principalName;

    private Boolean allowParentRegistration;
    private Boolean allowStudentRegistration;

    @jakarta.validation.constraints.Min(value = 1, message = "Max students per class must be at least 1")
    @jakarta.validation.constraints.Max(value = 100, message = "Max students per class must be less than 100")
    private Integer maxStudentsPerClass;

    private Boolean enableEmailNotifications;
    private Boolean enableSmsNotifications;

    @Size(max = 50, message = "SMS provider must be less than 50 characters")
    private String smsProvider;

    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email sender must be less than 100 characters")
    private String emailSender;

    // Constructors
    public SettingsUpdateRequest() {
    }

    // Getters and Setters
    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public MultipartFile getLogo() {
        return logo;
    }

    public void setLogo(MultipartFile logo) {
        this.logo = logo;
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
}