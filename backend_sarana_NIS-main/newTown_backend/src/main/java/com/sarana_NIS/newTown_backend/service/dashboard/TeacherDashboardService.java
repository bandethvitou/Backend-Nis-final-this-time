package com.sarana_NIS.newTown_backend.service.dashboard;

import java.util.Map;

public interface TeacherDashboardService {
    Map<String, Object> getDashboardStats();
    Map<String, Object> getClasses();
    Map<String, Object> getTasks();
}