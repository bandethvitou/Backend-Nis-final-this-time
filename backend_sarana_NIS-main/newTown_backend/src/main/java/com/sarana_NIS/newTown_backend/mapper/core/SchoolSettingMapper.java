package com.sarana_NIS.newTown_backend.mapper.core;

import com.sarana_NIS.newTown_backend.dto.request.core.SettingsUpdateRequest;
import com.sarana_NIS.newTown_backend.dto.response.core.SettingsResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.SchoolSetting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SchoolSettingMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "schoolName", source = "schoolName")
    @Mapping(target = "logoUrl", source = "logoUrl")
    @Mapping(target = "academicYear", source = "academicYear")
    @Mapping(target = "currentTerm", source = "currentTerm")
    @Mapping(target = "dateFormat", source = "dateFormat")
    @Mapping(target = "timezone", source = "timezone")
    @Mapping(target = "schoolAddress", source = "schoolAddress")
    @Mapping(target = "schoolPhone", source = "schoolPhone")
    @Mapping(target = "schoolEmail", source = "schoolEmail")
    @Mapping(target = "principalName", source = "principalName")
    @Mapping(target = "allowParentRegistration", source = "allowParentRegistration")
    @Mapping(target = "allowStudentRegistration", source = "allowStudentRegistration")
    @Mapping(target = "maxStudentsPerClass", source = "maxStudentsPerClass")
    @Mapping(target = "enableEmailNotifications", source = "enableEmailNotifications")
    @Mapping(target = "enableSmsNotifications", source = "enableSmsNotifications")
    @Mapping(target = "smsProvider", source = "smsProvider")
    @Mapping(target = "emailSender", source = "emailSender")
    @Mapping(target = "schoolMotto", source = "schoolMotto")
    @Mapping(target = "schoolMission", source = "schoolMission")
    @Mapping(target = "schoolVision", source = "schoolVision")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    SettingsResponse toResponse(SchoolSetting setting);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "logoUrl", ignore = true) // Handle file upload separately
    @Mapping(target = "schoolMotto", ignore = true)
    @Mapping(target = "schoolMission", ignore = true)
    @Mapping(target = "schoolVision", ignore = true)
    void updateEntity(SettingsUpdateRequest request, @MappingTarget SchoolSetting setting);
}