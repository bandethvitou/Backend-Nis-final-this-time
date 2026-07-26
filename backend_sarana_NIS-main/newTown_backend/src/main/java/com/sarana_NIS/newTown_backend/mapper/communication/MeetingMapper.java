package com.sarana_NIS.newTown_backend.mapper.communication;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sarana_NIS.newTown_backend.dto.request.communication.MeetingRequest;
import com.sarana_NIS.newTown_backend.dto.request.communication.MeetingUpdateRequest;
import com.sarana_NIS.newTown_backend.dto.response.communication.MeetingResponse;
import com.sarana_NIS.newTown_backend.dto.response.communication.MeetingSummaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.communication.Meeting;
import com.sarana_NIS.newTown_backend.model.enums.MeetingStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MeetingMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "hostId", source = "host.id")
    @Mapping(target = "hostName", source = "host.fullName")
    @Mapping(target = "statusColor", expression = "java(getStatusColor(meeting.getStatus()))")
    @Mapping(target = "isUpcoming", expression = "java(meeting.isUpcoming())")
    @Mapping(target = "daysUntil", expression = "java(calculateDaysUntil(meeting))")
    @Mapping(target = "attendeeIds", expression = "java(parseAttendeeIds(meeting.getAttendees()))")
    @Mapping(target = "attendeeNames", expression = "java(parseAttendeeNames(meeting.getAttendees()))")
    MeetingResponse toResponse(Meeting meeting);

    // ========== Entity → Summary Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "host", source = "host.fullName")
    @Mapping(target = "hostName", source = "host.fullName")
    @Mapping(target = "attendeeCount", expression = "java(countAttendees(meeting))")
    @Mapping(target = "attendees", expression = "java(mapAttendees(meeting))")
    MeetingSummaryResponse toSummaryResponse(Meeting meeting);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "host", ignore = true)
    @Mapping(target = "status", constant = "SCHEDULED")
    @Mapping(target = "attendees", expression = "java(convertAttendeeIds(request.getAttendeeIds()))")
    Meeting toEntity(MeetingRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "host", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "attendees", expression = "java(convertAttendeeIds(request.getAttendeeIds()))")
    void updateEntity(MeetingUpdateRequest request, @MappingTarget Meeting meeting);

    // ========== Helper Methods ==========

    default String getStatusColor(MeetingStatus status) {
        if (status == null) return "#6B7280";
        switch (status) {
            case SCHEDULED: return "#3B82F6";
            case COMPLETED: return "#10B981";
            case CANCELLED: return "#EF4444";
            default: return "#6B7280";
        }
    }

    default Integer calculateDaysUntil(Meeting meeting) {
        if (meeting == null || meeting.getMeetingDate() == null) return 0;
        if (!meeting.isUpcoming()) return 0;
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), meeting.getMeetingDate());
    }

    default Integer countAttendees(Meeting meeting) {
        if (meeting == null || meeting.getAttendees() == null) return 0;
        List<Long> attendeeIds = parseAttendeeIds(meeting.getAttendees());
        return attendeeIds != null ? attendeeIds.size() : 0;
    }

    default List<Long> parseAttendeeIds(String attendeesJson) {
        if (attendeesJson == null || attendeesJson.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(attendeesJson, new TypeReference<List<Long>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    default List<String> parseAttendeeNames(String attendeesJson) {
        if (attendeesJson == null || attendeesJson.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Long> ids = mapper.readValue(attendeesJson, new TypeReference<List<Long>>() {});
            List<String> names = new ArrayList<>();
            for (Long id : ids) {
                // TODO: Fetch user name from service
                names.add("User " + id);
            }
            return names;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    default String convertAttendeeIds(List<Long> attendeeIds) {
        if (attendeeIds == null || attendeeIds.isEmpty()) {
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(attendeeIds);
        } catch (Exception e) {
            return null;
        }
    }

    default List<MeetingSummaryResponse.AttendeeInfo> mapAttendees(Meeting meeting) {
        if (meeting == null || meeting.getAttendees() == null) {
            return new ArrayList<>();
        }
        List<Long> attendeeIds = parseAttendeeIds(meeting.getAttendees());
        List<MeetingSummaryResponse.AttendeeInfo> result = new ArrayList<>();
        for (Long id : attendeeIds) {
            MeetingSummaryResponse.AttendeeInfo info = new MeetingSummaryResponse.AttendeeInfo();
            info.setUserId(id);
            // TODO: Fetch user name from service
            info.setName("User " + id);
            result.add(info);
        }
        return result;
    }
}