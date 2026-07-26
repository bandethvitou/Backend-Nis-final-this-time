package com.sarana_NIS.newTown_backend.dto.response.communication;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CalendarOverviewResponse {

    private String month;
    private Integer year;
    private List<CalendarDay> days = new ArrayList<>();
    private List<EventSummary> events = new ArrayList<>();
    private Integer totalEvents;
    private Integer upcomingEvents;

    // Constructors
    public CalendarOverviewResponse() {}

    public CalendarOverviewResponse(String month, Integer year) {
        this.month = month;
        this.year = year;
    }

    // Getters and Setters
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }

    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }

    public List<CalendarDay> getDays() { return days; }
    public void setDays(List<CalendarDay> days) { this.days = days; }

    public List<EventSummary> getEvents() { return events; }
    public void setEvents(List<EventSummary> events) { this.events = events; }

    public Integer getTotalEvents() { return totalEvents; }
    public void setTotalEvents(Integer totalEvents) { this.totalEvents = totalEvents; }

    public Integer getUpcomingEvents() { return upcomingEvents; }
    public void setUpcomingEvents(Integer upcomingEvents) { this.upcomingEvents = upcomingEvents; }

    // Inner classes
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CalendarDay {
        private Integer day;
        private String dayOfWeek;
        private Boolean isToday;
        private Boolean hasEvent;
        private List<EventSummary> events = new ArrayList<>();

        // Constructors
        public CalendarDay() {}

        public CalendarDay(Integer day) {
            this.day = day;
        }

        // Getters and Setters
        public Integer getDay() { return day; }
        public void setDay(Integer day) { this.day = day; }

        public String getDayOfWeek() { return dayOfWeek; }
        public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

        public Boolean getIsToday() { return isToday; }
        public void setIsToday(Boolean isToday) { this.isToday = isToday; }

        public Boolean getHasEvent() { return hasEvent; }
        public void setHasEvent(Boolean hasEvent) { this.hasEvent = hasEvent; }

        public List<EventSummary> getEvents() { return events; }
        public void setEvents(List<EventSummary> events) { this.events = events; }
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class EventSummary {
        private Long id;
        private String title;
        private String time;
        private String type;
        private String colorCode;
        private Boolean isAllDay;

        // Constructors
        public EventSummary() {}

        public EventSummary(Long id, String title) {
            this.id = id;
            this.title = title;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public String getTime() { return time; }
        public void setTime(String time) { this.time = time; }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        public String getColorCode() { return colorCode; }
        public void setColorCode(String colorCode) { this.colorCode = colorCode; }

        public Boolean getIsAllDay() { return isAllDay; }
        public void setIsAllDay(Boolean isAllDay) { this.isAllDay = isAllDay; }
    }
}