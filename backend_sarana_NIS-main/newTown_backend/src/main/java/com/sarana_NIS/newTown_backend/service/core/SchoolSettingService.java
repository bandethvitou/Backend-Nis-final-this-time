package com.sarana_NIS.newTown_backend.service.core;

import com.sarana_NIS.newTown_backend.dto.request.core.SettingsUpdateRequest;
import com.sarana_NIS.newTown_backend.dto.response.core.SettingsResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.SchoolSetting;
import com.sarana_NIS.newTown_backend.repository.SchoolSettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SchoolSettingService {

    private static final Logger log = LoggerFactory.getLogger(SchoolSettingService.class);

    private final SchoolSettingRepository schoolSettingRepository;

    public SchoolSettingService(SchoolSettingRepository schoolSettingRepository) {
        this.schoolSettingRepository = schoolSettingRepository;
    }

    public SettingsResponse getSettings() {
        SchoolSetting settings = schoolSettingRepository.findFirst()
                .orElseThrow(() -> new RuntimeException("School settings not found"));
        return mapToResponse(settings);
    }

    public String getSchoolName() {
        return schoolSettingRepository.findSchoolName();
    }

    public String getCurrentAcademicYear() {
        return schoolSettingRepository.findCurrentAcademicYear();
    }

    public String getCurrentTerm() {
        return schoolSettingRepository.findCurrentTerm();
    }

    public String getTimezone() {
        return schoolSettingRepository.findTimezone();
    }

    @Transactional
    public SettingsResponse updateSettings(SettingsUpdateRequest request) {
        SchoolSetting settings = schoolSettingRepository.findFirst()
                .orElseThrow(() -> new RuntimeException("School settings not found"));

        if (request.getSchoolName() != null) settings.setSchoolName(request.getSchoolName());
        if (request.getAcademicYear() != null) settings.setAcademicYear(request.getAcademicYear());
        if (request.getCurrentTerm() != null) settings.setCurrentTerm(request.getCurrentTerm());
        if (request.getDateFormat() != null) settings.setDateFormat(request.getDateFormat());
        if (request.getTimezone() != null) settings.setTimezone(request.getTimezone());

        SchoolSetting updated = schoolSettingRepository.save(settings);
        log.info("School settings updated");
        return mapToResponse(updated);
    }

    @Transactional
    public void updateLogo(Long id, String logoUrl) {
        schoolSettingRepository.updateLogoUrl(id, logoUrl);
        log.info("School logo updated");
    }

    @Transactional
    public void updateAcademicYear(Long id, String academicYear) {
        schoolSettingRepository.updateAcademicYear(id, academicYear);
        log.info("Academic year updated: {}", academicYear);
    }

    private SettingsResponse mapToResponse(SchoolSetting settings) {
        SettingsResponse response = new SettingsResponse();
        response.setId(settings.getId());
        response.setSchoolName(settings.getSchoolName());
        response.setLogoUrl(settings.getLogoUrl());
        response.setAcademicYear(settings.getAcademicYear());
        response.setCurrentTerm(settings.getCurrentTerm());
        response.setDateFormat(settings.getDateFormat());
        response.setTimezone(settings.getTimezone());
        response.setLastUpdated(settings.getUpdatedAt());
        response.setUpdatedBy(settings.getUpdatedBy() != null ? settings.getUpdatedBy().toString() : null);
        response.setVersion("1.0");
        return response;
    }
}