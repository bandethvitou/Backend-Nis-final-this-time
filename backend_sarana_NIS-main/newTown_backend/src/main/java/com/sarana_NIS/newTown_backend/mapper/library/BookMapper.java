package com.sarana_NIS.newTown_backend.mapper.library;

import com.sarana_NIS.newTown_backend.dto.request.library.BookRequest;
import com.sarana_NIS.newTown_backend.dto.response.library.BookDetailResponse;
import com.sarana_NIS.newTown_backend.dto.response.library.BookResponse;
import com.sarana_NIS.newTown_backend.model.entity.library.Book;
import com.sarana_NIS.newTown_backend.model.enums.BookStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "author", source = "author")
    @Mapping(target = "isbn", source = "isbn")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "publisher", source = "publisher")
    @Mapping(target = "publicationYear", source = "publicationYear")
    @Mapping(target = "edition", source = "edition")
    @Mapping(target = "available", source = "availableCopies")
    @Mapping(target = "quantity", source = "totalCopies")
    @Mapping(target = "category", source = "genre")
    @Mapping(target = "location", source = "shelfLocation")
    @Mapping(target = "tags", expression = "java(parseTags(book.getTags()))")
    // ✅ Status is derived from availableCopies - return enum or String
    @Mapping(target = "status", expression = "java(determineBookStatus(book).name())")
    BookResponse toResponse(Book book);

    // ========== Entity → Detail Response ==========
    @Mapping(target = "book", expression = "java(toResponse(book))")
    @Mapping(target = "isAvailable", expression = "java(book.getAvailableCopies() != null && book.getAvailableCopies() > 0)")
    @Mapping(target = "availableCopies", source = "availableCopies")
    @Mapping(target = "locationDetails", source = "shelfLocation")
    BookDetailResponse toDetailResponse(Book book);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "totalCopies", source = "quantity")
    @Mapping(target = "availableCopies", source = "quantity")
    @Mapping(target = "genre", source = "category")
    @Mapping(target = "shelfLocation", source = "location")
    @Mapping(target = "tags", expression = "java(convertTags(request.getTags()))")
    Book toEntity(BookRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "totalCopies", source = "quantity")
    @Mapping(target = "availableCopies", source = "quantity")
    @Mapping(target = "genre", source = "category")
    @Mapping(target = "shelfLocation", source = "location")
    @Mapping(target = "tags", expression = "java(convertTags(request.getTags()))")
    void updateEntity(BookRequest request, @MappingTarget Book book);

    // ========== List Mapping ==========
    List<BookResponse> toResponseList(List<Book> books);

    // ========== Helper Methods ==========

    /**
     * Determine BookStatus based on available copies
     */
    default BookStatus determineBookStatus(Book book) {
        if (book == null) return BookStatus.AVAILABLE;
        if (book.getAvailableCopies() != null && book.getAvailableCopies() > 0) {
            return BookStatus.AVAILABLE;
        }
        return BookStatus.CHECKED_OUT;
    }

    /**
     * Parse JSON tags string to List<String>
     */
    default List<String> parseTags(String tagsJson) {
        if (tagsJson == null || tagsJson.isEmpty()) {
            return new java.util.ArrayList<>();
        }
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper =
                    new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.readValue(tagsJson,
                    new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {});
        } catch (Exception e) {
            return new java.util.ArrayList<>();
        }
    }

    /**
     * Convert List<String> to JSON string for storage
     */
    default String convertTags(List<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return null;
        }
        try {
            com.fasterxml.jackson.databind.ObjectMapper mapper =
                    new com.fasterxml.jackson.databind.ObjectMapper();
            return mapper.writeValueAsString(tags);
        } catch (Exception e) {
            return null;
        }
    }
}