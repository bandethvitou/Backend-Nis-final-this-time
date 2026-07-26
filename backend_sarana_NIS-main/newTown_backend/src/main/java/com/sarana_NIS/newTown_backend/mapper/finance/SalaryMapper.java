package com.sarana_NIS.newTown_backend.mapper.finance;

import com.sarana_NIS.newTown_backend.dto.request.finance.SalaryRequest;
import com.sarana_NIS.newTown_backend.dto.response.finance.SalaryResponse;
import com.sarana_NIS.newTown_backend.model.entity.finance.Salary;
import com.sarana_NIS.newTown_backend.model.enums.SalaryStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SalaryMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "staffId", source = "staff.id")
    @Mapping(target = "staffName", source = "staff.user.fullName")
    @Mapping(target = "department", source = "staff.department.name")
    @Mapping(target = "total", source = "total")
    SalaryResponse toResponse(Salary salary);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "status", constant = "PENDING")
    Salary toEntity(SalaryRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "staff", ignore = true)
    @Mapping(target = "total", ignore = true)
    @Mapping(target = "status", ignore = true)
    void updateEntity(SalaryRequest request, @MappingTarget Salary salary);
}