package com.sarana_NIS.newTown_backend.dto.response.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponse<T> {

    private List<T> content = new ArrayList<>();
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean last;
    private boolean first;
    private int numberOfElements;
    private boolean empty;

    // Constructors
    public PageResponse() {}

    public PageResponse(List<T> content, int pageNumber, int pageSize, long totalElements, int totalPages) {
        this.content = content != null ? content : new ArrayList<>();
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.last = pageNumber >= totalPages - 1;
        this.first = pageNumber == 0;
        this.numberOfElements = this.content.size();
        this.empty = this.content.isEmpty();
    }

    // Getters and Setters
    public List<T> getContent() { return content; }

    public void setContent(List<T> content) {
        this.content = content != null ? content : new ArrayList<>();
        this.numberOfElements = this.content.size();
        this.empty = this.content.isEmpty();
    }

    public int getPageNumber() { return pageNumber; }
    public void setPageNumber(int pageNumber) { this.pageNumber = pageNumber; }

    public int getPageSize() { return pageSize; }
    public void setPageSize(int pageSize) { this.pageSize = pageSize; }

    public long getTotalElements() { return totalElements; }
    public void setTotalElements(long totalElements) { this.totalElements = totalElements; }

    public int getTotalPages() { return totalPages; }
    public void setTotalPages(int totalPages) { this.totalPages = totalPages; }

    public boolean isLast() { return last; }
    public void setLast(boolean last) { this.last = last; }

    public boolean isFirst() { return first; }
    public void setFirst(boolean first) { this.first = first; }

    public int getNumberOfElements() { return numberOfElements; }
    public void setNumberOfElements(int numberOfElements) { this.numberOfElements = numberOfElements; }

    public boolean isEmpty() { return empty; }
    public void setEmpty(boolean empty) { this.empty = empty; }
}