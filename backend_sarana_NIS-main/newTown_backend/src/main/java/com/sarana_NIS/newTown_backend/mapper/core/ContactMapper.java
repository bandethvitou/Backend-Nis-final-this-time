package com.sarana_NIS.newTown_backend.mapper.core;

import com.sarana_NIS.newTown_backend.dto.request.core.ContactRequest;
import com.sarana_NIS.newTown_backend.dto.response.core.ContactResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface ContactMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "createdBy", expression = "java(getCreatedBy(contact))")
    @Mapping(target = "updatedBy", expression = "java(getUpdatedBy(contact))")
    ContactResponse toResponse(Contact contact);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "isAvailable", constant = "true")
    Contact toEntity(ContactRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    void updateEntity(ContactRequest request, @MappingTarget Contact contact);

    // ========== Helper Methods ==========

    default String getCreatedBy(Contact contact) {
        if (contact == null || contact.getCreatedBy() == null) {
            return null;
        }
        return String.valueOf(contact.getCreatedBy());
    }

    default String getUpdatedBy(Contact contact) {
        if (contact == null || contact.getUpdatedBy() == null) {
            return null;
        }
        return String.valueOf(contact.getUpdatedBy());
    }
}