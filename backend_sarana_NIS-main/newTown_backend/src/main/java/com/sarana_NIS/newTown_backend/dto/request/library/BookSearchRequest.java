package com.sarana_NIS.newTown_backend.dto.request.library;

import com.sarana_NIS.newTown_backend.model.enums.BookStatus;
import com.sarana_NIS.newTown_backend.model.enums.Level;
import jakarta.validation.constraints.Size;

public class BookSearchRequest {

    @Size(max = 100, message = "Keyword must be less than 100 characters")
    private String keyword;

    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @Size(max = 100, message = "Author must be less than 100 characters")
    private String author;

    @Size(max = 20, message = "ISBN must be less than 20 characters")
    private String isbn;

    @Size(max = 50, message = "Category must be less than 50 characters")
    private String category;

    private Level level;
    private BookStatus status;
    private Boolean isAvailable;
    private String location;

    private Integer page = 0;
    private Integer size = 20;
    private String sortBy = "title";
    private String sortDirection = "ASC";

    // Constructors
    public BookSearchRequest() {}

    public BookSearchRequest(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    // Getters and Setters
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

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

    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }

    public Boolean getIsAvailable() { return isAvailable; }
    public void setIsAvailable(Boolean isAvailable) { this.isAvailable = isAvailable; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }

    public String getSortDirection() { return sortDirection; }
    public void setSortDirection(String sortDirection) { this.sortDirection = sortDirection; }
}