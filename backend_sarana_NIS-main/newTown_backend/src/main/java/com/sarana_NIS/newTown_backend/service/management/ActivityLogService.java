package com.sarana_NIS.newTown_backend.service.management;

import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.entity.management.ActivityLog;
import com.sarana_NIS.newTown_backend.repository.ActivityLogRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityLogService {

    private static final Logger log = LoggerFactory.getLogger(ActivityLogService.class);

    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;

    public ActivityLogService(ActivityLogRepository activityLogRepository, UserRepository userRepository) {
        this.activityLogRepository = activityLogRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ActivityLog logActivity(Long userId, String action, String entityType,
                                   Long entityId, String oldValue, String newValue,
                                   String ipAddress, String userAgent) {
        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId).orElse(null);
        }

        ActivityLog activityLog = new ActivityLog();
        activityLog.setUser(user);
        activityLog.setAction(action);
        activityLog.setEntityType(entityType);
        activityLog.setEntityId(entityId);
        activityLog.setOldValue(oldValue);
        activityLog.setNewValue(newValue);
        activityLog.setIpAddress(ipAddress);
        activityLog.setUserAgent(userAgent);

        ActivityLog saved = activityLogRepository.save(activityLog);
        log.info("Activity logged: {} by user {}", action, userId);
        return saved;
    }

    @Transactional
    public ActivityLog logActivity(Long userId, String action, String entityType, Long entityId) {
        return logActivity(userId, action, entityType, entityId, null, null, null, null);
    }

    public ActivityLog getLogById(Long logId) {
        return activityLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Log not found"));
    }

    public List<ActivityLog> getLogsByUser(Long userId) {
        return activityLogRepository.findByUserId(userId);
    }

    public List<ActivityLog> getLogsByAction(String action) {
        return activityLogRepository.findByAction(action);
    }

    public List<ActivityLog> getLogsByEntityType(String entityType) {
        return activityLogRepository.findByEntityType(entityType);
    }

    public List<ActivityLog> getLogsByEntity(String entityType, Long entityId) {
        return activityLogRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }

    public List<ActivityLog> getLogsByIpAddress(String ipAddress) {
        return activityLogRepository.findByIpAddress(ipAddress);
    }

    public PageResponse<ActivityLog> getLogsByDateRange(LocalDateTime start, LocalDateTime end, Pageable pageable) {
        Page<ActivityLog> page = activityLogRepository.findByCreatedAtBetween(start, end, pageable);
        return new PageResponse<>(page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }

    public PageResponse<ActivityLog> getLogsByUserAndDateRange(Long userId, LocalDateTime start,
                                                               LocalDateTime end, Pageable pageable) {
        Page<ActivityLog> page = activityLogRepository.findByUserAndDateRange(userId, start, end, pageable);
        return new PageResponse<>(page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }

    public PageResponse<ActivityLog> getLatestLogs(Pageable pageable) {
        Page<ActivityLog> page = activityLogRepository.findLatest(pageable);
        return new PageResponse<>(page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages());
    }

    public List<Object[]> getActivityCountByUser(LocalDateTime start, LocalDateTime end) {
        return activityLogRepository.findActivityCountByUser(start, end);
    }

    public List<Object[]> getActivityCountByAction(LocalDateTime start, LocalDateTime end) {
        return activityLogRepository.findActivityCountByAction(start, end);
    }

    public long countByUserSince(Long userId, LocalDateTime date) {
        return activityLogRepository.countByUserSince(userId, date);
    }

    @Transactional
    public void deleteLog(Long logId) {
        ActivityLog activityLog = activityLogRepository.findById(logId)
                .orElseThrow(() -> new RuntimeException("Log not found"));
        activityLogRepository.delete(activityLog);
        log.info("Activity log deleted: {}", logId);
    }

    @Transactional
    public void deleteLogsOlderThan(LocalDateTime date) {
        Pageable pageable = Pageable.ofSize(1000);
        Page<ActivityLog> page;
        do {
            page = activityLogRepository.findByCreatedAtBetween(LocalDateTime.MIN, date, pageable);
            if (page.hasContent()) {
                activityLogRepository.deleteAll(page.getContent());
            }
        } while (page.hasNext());
        log.info("Deleted activity logs older than: {}", date);
    }
}