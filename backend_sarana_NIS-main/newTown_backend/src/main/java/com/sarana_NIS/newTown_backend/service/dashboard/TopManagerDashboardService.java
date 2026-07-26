package com.sarana_NIS.newTown_backend.service.dashboard;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TopManagerDashboardService {

    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", 0);
        stats.put("totalStaff", 0);
        stats.put("totalTeachers", 0);
        stats.put("totalClasses", 0);
        stats.put("totalDepartments", 0);
        stats.put("pendingTasks", 0);
        stats.put("completedTasks", 0);
        stats.put("overdueTasks", 0);
        return stats;
    }

    public Map<String, Object> getEnrollmentSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalStudents", 0);
        summary.put("activeStudents", 0);
        summary.put("newStudents", 0);
        summary.put("graduatedStudents", 0);
        summary.put("droppedStudents", 0);
        summary.put("enrollmentRate", 0.0);
        return summary;
    }

    public Map<String, Object> getScoreCompletion() {
        Map<String, Object> status = new HashMap<>();
        status.put("totalSubjects", 0);
        status.put("completedScores", 0);
        status.put("pendingScores", 0);
        status.put("completionRate", 0.0);
        status.put("subjects", new java.util.ArrayList<>());
        return status;
    }

    public Map<String, Object> getPendingReviews() {
        Map<String, Object> reviews = new HashMap<>();
        reviews.put("pendingLessonPlans", 0);
        reviews.put("pendingReports", 0);
        reviews.put("pendingProposals", 0);
        reviews.put("pendingScores", 0);
        reviews.put("totalPending", 0);
        return reviews;
    }
}