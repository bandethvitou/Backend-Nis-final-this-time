package com.sarana_NIS.newTown_backend.dto.request.admissions;

import com.sarana_NIS.newTown_backend.model.enums.ApplicationStatus;
import com.sarana_NIS.newTown_backend.model.enums.Level;

import java.time.LocalDate;

public class ApplicationFilterRequest {

    private String keyword;
    private ApplicationStatus status;
    private Level level;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private Boolean hasTour;
    private Integer page = 0;
    private Integer size = 20;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";

    // Constructors
    public ApplicationFilterRequest() {}

    public ApplicationFilterRequest(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    // Getters and Setters
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public ApplicationStatus getStatus() { return status; }
    public void setStatus(ApplicationStatus status) { this.status = status; }

    public Level getLevel() { return level; }
    public void setLevel(Level level) { this.level = level; }

    public LocalDate getDateFrom() { return dateFrom; }
    public void setDateFrom(LocalDate dateFrom) { this.dateFrom = dateFrom; }

    public LocalDate getDateTo() { return dateTo; }
    public void setDateTo(LocalDate dateTo) { this.dateTo = dateTo; }

    public Boolean getHasTour() { return hasTour; }
    public void setHasTour(Boolean hasTour) { this.hasTour = hasTour; }

    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }

    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }

    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }

    public String getSortDirection() { return sortDirection; }
    public void setSortDirection(String sortDirection) { this.sortDirection = sortDirection; }
}