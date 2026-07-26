package com.sarana_NIS.newTown_backend.mapper.position;

import com.sarana_NIS.newTown_backend.dto.request.position.PositionRequest;
import com.sarana_NIS.newTown_backend.dto.response.position.PositionResponse;
import com.sarana_NIS.newTown_backend.model.entity.position.Position;
import com.sarana_NIS.newTown_backend.repository.StaffPositionRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public abstract class PositionMapper {

    @Autowired
    protected StaffPositionRepository staffPositionRepository;

    // ========== Entity → Response ==========
    @Mapping(target = "staffCount", expression = "java(countStaff(position))")
    @Mapping(target = "isActive", expression = "java(position.getDeletedAt() == null)")
    public abstract PositionResponse toResponse(Position position);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isTemporaryAllowed", source = "isTemporaryAllowed")
    public abstract Position toEntity(PositionRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isTemporaryAllowed", source = "isTemporaryAllowed")
    public abstract void updateEntity(PositionRequest request, @MappingTarget Position position);

    // ========== Helper Methods ==========

    /**
     * Count active staff assigned to this position.
     * Uses StaffPositionRepository to query the database.
     */
    protected Integer countStaff(Position position) {
        if (position == null || position.getId() == null) {
            return 0;
        }
        Long count = staffPositionRepository.countActiveByPositionId(position.getId());
        return count != null ? count.intValue() : 0;
    }
}