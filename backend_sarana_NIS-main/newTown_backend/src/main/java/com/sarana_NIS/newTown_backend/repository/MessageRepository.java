package com.sarana_NIS.newTown_backend.repository;

import com.sarana_NIS.newTown_backend.model.entity.communication.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.sender.id = :userId OR m.receiver.id = :userId")
    List<Message> findByUserId(@Param("userId") Long userId);

    @Query("SELECT m FROM Message m WHERE m.receiver.id = :userId AND m.isRead = false")
    List<Message> findUnreadByReceiver(@Param("userId") Long userId);

    @Query("SELECT m FROM Message m WHERE m.receiver.id = :userId ORDER BY m.createdAt DESC")
    Page<Message> findReceivedByReceiver(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.sender.id = :userId ORDER BY m.createdAt DESC")
    Page<Message> findSentBySender(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.parentMessage.id = :parentMessageId ORDER BY m.createdAt ASC")
    List<Message> findThreadByParentId(@Param("parentMessageId") Long parentMessageId);

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :userId1 AND m.receiver.id = :userId2) OR " +
            "(m.sender.id = :userId2 AND m.receiver.id = :userId1) ORDER BY m.createdAt ASC")
    List<Message> findConversationBetween(@Param("userId1") Long userId1,
                                          @Param("userId2") Long userId2);

    @Query("SELECT COUNT(m) FROM Message m WHERE m.receiver.id = :userId AND m.isRead = false")
    Long countUnreadByReceiver(@Param("userId") Long userId);

    @Query("SELECT m FROM Message m WHERE m.subject LIKE CONCAT('%', :keyword, '%') " +
            "OR m.content LIKE CONCAT('%', :keyword, '%')")
    Page<Message> searchMessages(@Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT m FROM Message m WHERE m.receiver.id = :userId AND m.createdAt >= :date")
    List<Message> findRecentByReceiver(@Param("userId") Long userId,
                                       @Param("date") LocalDateTime date);

    @Query("SELECT DISTINCT m.sender.id FROM Message m WHERE m.receiver.id = :userId")
    List<Long> findDistinctSendersForReceiver(@Param("userId") Long userId);

    @Query("SELECT DISTINCT m.receiver.id FROM Message m WHERE m.sender.id = :userId")
    List<Long> findDistinctReceiversForSender(@Param("userId") Long userId);
}