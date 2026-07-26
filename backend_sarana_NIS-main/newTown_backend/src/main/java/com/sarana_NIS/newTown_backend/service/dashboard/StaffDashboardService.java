package com.sarana_NIS.newTown_backend.service.dashboard;

import com.sarana_NIS.newTown_backend.repository.LeaveRequestRepository;
import com.sarana_NIS.newTown_backend.repository.StaffRepository;
import com.sarana_NIS.newTown_backend.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
public class StaffDashboardService {

    private static final Logger log = LoggerFactory.getLogger(StaffDashboardService.class);

    private final StaffRepository staffRepository;
    private final TaskRepository taskRepository;
    private final LeaveRequestRepository leaveRequestRepository;

    public StaffDashboardService(StaffRepository staffRepository,
                                 TaskRepository taskRepository,
                                 LeaveRequestRepository leaveRequestRepository) {
        this.staffRepository = staffRepository;
        this.taskRepository = taskRepository;
        this.leaveRequestRepository = leaveRequestRepository;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getDashboardStats() {
        log.info("Fetching staff dashboard statistics");

        Map<String, Object> stats = new HashMap<>();
        stats.put("pendingTasks", 0);
        stats.put("completedTasks", 0);
        stats.put("leaveBalance", 0);
        stats.put("pendingLeave", 0);
        stats.put("todayShift", "Morning");
        stats.put("nextShift", "Afternoon");

        return stats;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getTasks() {
        log.info("Fetching staff tasks");

        Map<String, Object> tasks = new HashMap<>();
        tasks.put("pendingTasks", new java.util.ArrayList<>());
        tasks.put("completedTasks", new java.util.ArrayList<>());

        return tasks;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getSchedule() {
        log.info("Fetching staff schedule");

        Map<String, Object> schedule = new HashMap<>();
        schedule.put("currentShift", "Morning (8:00 AM - 12:00 PM)");
        schedule.put("nextShift", "Afternoon (1:00 PM - 5:00 PM)");
        schedule.put("upcoming", new java.util.ArrayList<>());

        return schedule;
    }
}