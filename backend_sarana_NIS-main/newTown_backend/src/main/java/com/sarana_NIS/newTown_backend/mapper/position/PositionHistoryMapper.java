package com.sarana_NIS.newTown_backend.mapper.position;

import com.sarana_NIS.newTown_backend.dto.response.position.PositionHistoryResponse;
import com.sarana_NIS.newTown_backend.model.entity.position.PositionHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface PositionHistoryMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "staffPositionId", source = "staffPosition.id")
    @Mapping(target = "staffName", source = "staffPosition.staff.user.fullName")
    @Mapping(target = "positionName", source = "staffPosition.position.name")
    @Mapping(target = "changedBy", source = "changedBy.fullName")
    @Mapping(target = "changedByName", source = "changedBy.fullName")
    @Mapping(target = "action", source = "action")
    @Mapping(target = "notes", source = "notes")
    @Mapping(target = "changedAt", source = "changedAt")
    PositionHistoryResponse toResponse(PositionHistory history);

    // ========== Batch Mapping ==========
    default List<PositionHistoryResponse> toResponseList(List<PositionHistory> histories) {
        if (histories == null) return new java.util.ArrayList<>();
        return histories.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}