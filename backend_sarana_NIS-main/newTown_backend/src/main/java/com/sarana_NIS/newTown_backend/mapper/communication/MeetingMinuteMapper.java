package com.sarana_NIS.newTown_backend.mapper.communication;

import com.sarana_NIS.newTown_backend.dto.request.communication.MeetingMinuteRequest;
import com.sarana_NIS.newTown_backend.dto.response.communication.MeetingMinuteResponse;
import com.sarana_NIS.newTown_backend.model.entity.communication.MeetingMinute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MeetingMinuteMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "meetingId", source = "meeting.id")
    @Mapping(target = "meetingTitle", source = "meeting.title")
    @Mapping(target = "meetingDate", source = "meeting.meetingDate")
    @Mapping(target = "meetingTime", expression = "java(formatMeetingTime(meetingMinute))")
    @Mapping(target = "attendees", expression = "java(parseAttendees(meetingMinute.getAttendees()))")
    @Mapping(target = "createdById", source = "createdByUser.id")
    @Mapping(target = "createdByName", source = "createdByUser.fullName")
    @Mapping(target = "createdBy", source = "createdByUser.fullName")
    MeetingMinuteResponse toResponse(MeetingMinute meetingMinute);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdByUser", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "meeting", ignore = true)
    @Mapping(target = "status", constant = "DRAFT")
    MeetingMinute toEntity(MeetingMinuteRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdByUser", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "meeting", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(MeetingMinuteRequest request, @MappingTarget MeetingMinute meetingMinute);

    // ========== Helper Methods ==========

    default String formatMeetingTime(MeetingMinute minute) {
        if (minute == null || minute.getMeeting() == null) {
            return null;
        }
        // Use getStartTime() instead of getMeetingTime()
        if (minute.getMeeting().getStartTime() != null) {
            return minute.getMeeting().getStartTime().toString();
        }
        return null;
    }

    default java.util.List<String> parseAttendees(String attendeesJson) {
        if (attendeesJson == null || attendeesJson.isEmpty()) {
            return new java.util.ArrayList<>();
        }
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper =
                    new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(attendeesJson,
                    new com.fasterxml.jackson.core.type.TypeReference<java.util.List<String>>() {});
        } catch (Exception e) {
            return new java.util.ArrayList<>();
        }
    }
}