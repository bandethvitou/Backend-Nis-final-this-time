package com.sarana_NIS.newTown_backend.service.dashboard;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class StudentDashboardService {

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("studentName", "Student");
        stats.put("className", "Class");
        stats.put("gpa", 0.0);
        stats.put("attendanceRate", 0.0);
        stats.put("totalSubjects", 0);
        stats.put("completedAssignments", 0);
        stats.put("pendingAssignments", 0);
        stats.put("upcomingExams", 0);
        stats.put("rank", 0);
        return stats;
    }

    public Map<String, Object> getScores() {
        Map<String, Object> scores = new HashMap<>();
        scores.put("term", "Term 1");
        scores.put("averageScore", 0.0);
        scores.put("highestScore", 0.0);
        scores.put("lowestScore", 0.0);
        scores.put("subjects", new java.util.ArrayList<>());
        return scores;
    }

    public Map<String, Object> getAttendance() {
        Map<String, Object> attendance = new HashMap<>();
        attendance.put("totalDays", 0);
        attendance.put("presentDays", 0);
        attendance.put("absentDays", 0);
        attendance.put("lateDays", 0);
        attendance.put("attendanceRate", 0.0);
        attendance.put("recentAttendance", new java.util.ArrayList<>());
        return attendance;
    }

    public Map<String, Object> getHomework() {
        Map<String, Object> homework = new HashMap<>();
        homework.put("totalHomework", 0);
        homework.put("submittedHomework", 0);
        homework.put("pendingHomework", 0);
        homework.put("overdueHomework", 0);
        homework.put("averageScore", 0.0);
        homework.put("recentHomework", new java.util.ArrayList<>());
        return homework;
    }
}