package com.sarana_NIS.newTown_backend.dto.response.it;

import java.util.HashMap;
import java.util.Map;

public class TicketStatsResponse {
    private Integer totalTickets;
    private Integer openTickets;
    private Integer inProgressTickets;
    private Integer resolvedTickets;
    private Integer closedTickets;
    private Map<String, Integer> byCategory;
    private Map<String, Integer> byPriority;
    private Integer averageResolutionTime;

    // Constructors
    public TicketStatsResponse() {
        this.totalTickets = 0;
        this.openTickets = 0;
        this.inProgressTickets = 0;
        this.resolvedTickets = 0;
        this.closedTickets = 0;
        this.byCategory = new HashMap<>();
        this.byPriority = new HashMap<>();
        this.averageResolutionTime = 0;
    }

    // Getters and Setters
    public Integer getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Integer totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Integer getOpenTickets() {
        return openTickets;
    }

    public void setOpenTickets(Integer openTickets) {
        this.openTickets = openTickets;
    }

    public Integer getInProgressTickets() {
        return inProgressTickets;
    }

    public void setInProgressTickets(Integer inProgressTickets) {
        this.inProgressTickets = inProgressTickets;
    }

    public Integer getResolvedTickets() {
        return resolvedTickets;
    }

    public void setResolvedTickets(Integer resolvedTickets) {
        this.resolvedTickets = resolvedTickets;
    }

    public Integer getClosedTickets() {
        return closedTickets;
    }

    public void setClosedTickets(Integer closedTickets) {
        this.closedTickets = closedTickets;
    }

    public Map<String, Integer> getByCategory() {
        return byCategory;
    }

    public void setByCategory(Map<String, Integer> byCategory) {
        this.byCategory = byCategory;
    }

    public Map<String, Integer> getByPriority() {
        return byPriority;
    }

    public void setByPriority(Map<String, Integer> byPriority) {
        this.byPriority = byPriority;
    }

    public Integer getAverageResolutionTime() {
        return averageResolutionTime;
    }

    public void setAverageResolutionTime(Integer averageResolutionTime) {
        this.averageResolutionTime = averageResolutionTime;
    }
}