package com.sarana_NIS.newTown_backend.dto.request.library;

import com.sarana_NIS.newTown_backend.model.enums.BookStatus;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

public class BookRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 2, max = 200, message = "Title must be between 2 and 200 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 100, message = "Author must be between 2 and 100 characters")
    private String author;

    @Pattern(regexp = "^[0-9-]{10,17}$", message = "Invalid ISBN format")
    private String isbn;

    @Size(max = 50, message = "Category must be less than 50 characters")
    private String category;

    private Level level;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @Size(max = 50, message = "Location must be less than 50 characters")
    private String location;

    private BookStatus status;

    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    @Size(max = 100, message = "Publisher must be less than 100 characters")
    private String publisher;

    private Integer publicationYear;

    @Size(max = 50, message = "Edition must be less than 50 characters")
    private String edition;

    @Min(value = 1, message = "Pages must be at least 1")
    private Integer pages;

    @Size(max = 20, message = "Language must be less than 20 characters")
    private String language;

    private List<String> tags = new ArrayList<>();

    // Constructors
    public BookRequest() {}

    public BookRequest(String title, String author, Integer quantity) {
        this.title = title;
        this.author = author;
        this.quantity = quantity;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }

    public String getEdition() { return edition; }
    public void setEdition(String edition) { this.edition = edition; }

    public Integer getPages() { return pages; }
    public void setPages(Integer pages) { this.pages = pages; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
}