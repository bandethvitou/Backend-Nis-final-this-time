package com.sarana_NIS.newTown_backend.service.communication;

import com.sarana_NIS.newTown_backend.dto.request.communication.NotificationBulkRequest;
import com.sarana_NIS.newTown_backend.model.entity.communication.Notification;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.model.enums.NotificationType;
import com.sarana_NIS.newTown_backend.repository.NotificationRepository;
import com.sarana_NIS.newTown_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {

    private static final Logger log = LoggerFactory.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Notification createNotification(Long userId, NotificationType type,
                                           String title, String message, String link) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setType(type);
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setLink(link);
        notification.setIsRead(false);

        Notification saved = notificationRepository.save(notification);
        log.info("Notification created for user: {}", userId);
        return saved;
    }

    @Transactional
    public List<Notification> createBulkNotifications(NotificationBulkRequest request) {
        List<User> users = userRepository.findAllById(request.getUserIds());
        List<Notification> notifications = new ArrayList<>();

        for (User user : users) {
            Notification notification = new Notification();
            notification.setUser(user);
            notification.setType(request.getType());
            notification.setTitle(request.getTitle());
            notification.setMessage(request.getMessage());
            notification.setLink(request.getLink());
            notification.setIsRead(false);
            notifications.add(notification);
        }

        List<Notification> saved = notificationRepository.saveAll(notifications);
        log.info("Bulk notifications sent to {} users", saved.size());
        return saved;
    }

    @Transactional
    public void markAsRead(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.getUser().getId().equals(userId)) {
            throw new RuntimeException("You don't have permission to mark this notification");
        }

        notification.setIsRead(true);
        notification.setReadAt(LocalDateTime.now());
        notificationRepository.save(notification);
        log.info("Notification marked as read: {}", notificationId);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        notificationRepository.markAllAsRead(userId);
        log.info("All notifications marked as read for user: {}", userId);
    }

    public Page<Notification> getNotificationsByUser(Long userId, Pageable pageable) {
        return notificationRepository.findByUserId(userId, pageable);
    }

    public Page<Notification> getUnreadNotificationsByUser(Long userId, Pageable pageable) {
        return notificationRepository.findUnreadByUserId(userId, pageable);
    }

    public List<Notification> getRecentNotificationsByUser(Long userId, int limit) {
        Pageable pageable = Pageable.ofSize(limit);
        Page<Notification> page = notificationRepository.findByUserId(userId, pageable);
        return page.getContent();
    }

    public List<Notification> getNotificationsByType(Long userId, NotificationType type) {
        return notificationRepository.findByUserAndType(userId, type);
    }

    public List<Notification> getRecentByType(NotificationType type, LocalDateTime since) {
        return notificationRepository.findRecentByType(type, since);
    }

    public long countUnreadNotifications(Long userId) {
        return notificationRepository.countUnreadByUserId(userId);
    }

    @Transactional
    public void deleteNotification(Long notificationId, Long userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        if (!notification.getUser().getId().equals(userId)) {
            throw new RuntimeException("You don't have permission to delete this notification");
        }

        notificationRepository.delete(notification);
        log.info("Notification deleted: {}", notificationId);
    }

    @Transactional
    public void deleteOldNotifications(Long userId, LocalDateTime olderThan) {
        List<Notification> oldNotifications = notificationRepository.findOldNotifications(userId, olderThan);
        notificationRepository.deleteAll(oldNotifications);
        log.info("Deleted {} old notifications for user: {}", oldNotifications.size(), userId);
    }
}