package com.sarana_NIS.newTown_backend.controller.communication;

import com.sarana_NIS.newTown_backend.dto.request.communication.MessageBulkRequest;
import com.sarana_NIS.newTown_backend.dto.request.communication.MessageThreadRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.ApiResponse;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.communication.MessageThreadResponse;
import com.sarana_NIS.newTown_backend.dto.response.communication.UnreadMessageResponse;
import com.sarana_NIS.newTown_backend.model.entity.communication.Message;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresPermission;
import com.sarana_NIS.newTown_backend.security.annotation.RequiresRole;
import com.sarana_NIS.newTown_backend.service.communication.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communication/messages")
@Tag(name = "Message Management", description = "Message management APIs")
public class MessageController {

    private static final Logger log = LoggerFactory.getLogger(MessageController.class);

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    @RequiresRole({"TEACHER", "PARENT", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("MESSAGE_SEND")
    @Operation(summary = "Send a message")
    public ResponseEntity<ApiResponse<Message>> sendMessage(
            @RequestParam Long receiverId,
            @RequestParam String subject,
            @RequestParam String content) {
        log.info("Sending message to: {}", receiverId);
        Long currentUserId = 1L;
        Message message = messageService.sendMessage(currentUserId, receiverId, subject, content);
        return ResponseEntity.ok(ApiResponse.success("Message sent successfully", message));
    }

    @PostMapping("/bulk")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("MESSAGE_SEND")
    @Operation(summary = "Send bulk messages")
    public ResponseEntity<ApiResponse<List<Message>>> sendBulkMessages(
            @Valid @RequestBody MessageBulkRequest request) {
        log.info("Sending bulk messages to {} recipients", request.getReceiverIds().size());
        Long currentUserId = 1L;
        List<Message> messages = messageService.sendBulkMessages(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Bulk messages sent successfully", messages));
    }

    @PostMapping("/reply")
    @RequiresRole({"TEACHER", "PARENT", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("MESSAGE_SEND")
    @Operation(summary = "Reply to a message")
    public ResponseEntity<ApiResponse<Message>> replyToMessage(
            @Valid @RequestBody MessageThreadRequest request) {
        log.info("Replying to message: {}", request.getParentMessageId());
        Long currentUserId = 1L;
        Message reply = messageService.replyToMessage(request, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Reply sent successfully", reply));
    }

    @GetMapping("/{messageId}")
    @RequiresRole({"TEACHER", "PARENT", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get message by ID")
    public ResponseEntity<ApiResponse<Message>> getMessageById(
            @PathVariable Long messageId) {
        log.info("Fetching message: {}", messageId);
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.ok(ApiResponse.success(message));
    }

    @GetMapping("/received")
    @RequiresRole({"TEACHER", "PARENT", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get received messages")
    public ResponseEntity<ApiResponse<PageResponse<Message>>> getReceivedMessages(
            Pageable pageable) {
        log.info("Fetching received messages");
        Long currentUserId = 1L;
        PageResponse<Message> response = messageService.getReceivedMessages(currentUserId, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/sent")
    @RequiresRole({"TEACHER", "PARENT", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get sent messages")
    public ResponseEntity<ApiResponse<PageResponse<Message>>> getSentMessages(
            Pageable pageable) {
        log.info("Fetching sent messages");
        Long currentUserId = 1L;
        PageResponse<Message> response = messageService.getSentMessages(currentUserId, pageable);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/conversation/{userId}")
    @RequiresRole({"TEACHER", "PARENT", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get conversation with user")
    public ResponseEntity<ApiResponse<List<Message>>> getConversation(
            @PathVariable Long userId) {
        log.info("Fetching conversation with: {}", userId);
        Long currentUserId = 1L;
        List<Message> messages = messageService.getConversation(currentUserId, userId);
        return ResponseEntity.ok(ApiResponse.success(messages));
    }

    @GetMapping("/thread/{parentMessageId}")
    @RequiresRole({"TEACHER", "PARENT", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get message thread")
    public ResponseEntity<ApiResponse<MessageThreadResponse>> getMessageThread(
            @PathVariable Long parentMessageId) {
        log.info("Fetching message thread: {}", parentMessageId);
        MessageThreadResponse response = messageService.getMessageThread(parentMessageId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/unread")
    @RequiresRole({"TEACHER", "PARENT", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get unread messages")
    public ResponseEntity<ApiResponse<UnreadMessageResponse>> getUnreadMessages() {
        log.info("Fetching unread messages");
        Long currentUserId = 1L;
        UnreadMessageResponse response = messageService.getUnreadMessages(currentUserId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/unread/count")
    @RequiresRole({"TEACHER", "PARENT", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @Operation(summary = "Get unread message count")
    public ResponseEntity<ApiResponse<Long>> countUnreadMessages() {
        log.info("Counting unread messages");
        Long currentUserId = 1L;
        long count = messageService.countUnreadMessages(currentUserId);
        return ResponseEntity.ok(ApiResponse.success(count));
    }

    @PostMapping("/{messageId}/read")
    @RequiresRole({"TEACHER", "PARENT", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("MESSAGE_UPDATE")
    @Operation(summary = "Mark message as read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @PathVariable Long messageId) {
        log.info("Marking message as read: {}", messageId);
        Long currentUserId = 1L;
        messageService.markAsRead(messageId, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Message marked as read", null));
    }

    @PostMapping("/read-all")
    @RequiresRole({"TEACHER", "PARENT", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("MESSAGE_UPDATE")
    @Operation(summary = "Mark all messages as read")
    public ResponseEntity<ApiResponse<Void>> markAllAsRead() {
        log.info("Marking all messages as read");
        Long currentUserId = 1L;
        messageService.markAllAsRead(currentUserId);
        return ResponseEntity.ok(ApiResponse.success("All messages marked as read", null));
    }

    @DeleteMapping("/{messageId}")
    @RequiresRole({"TEACHER", "PARENT", "MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("MESSAGE_DELETE")
    @Operation(summary = "Delete message")
    public ResponseEntity<ApiResponse<Void>> deleteMessage(
            @PathVariable Long messageId) {
        log.info("Deleting message: {}", messageId);
        Long currentUserId = 1L;
        messageService.deleteMessage(messageId, currentUserId);
        return ResponseEntity.ok(ApiResponse.success("Message deleted successfully", null));
    }

    @DeleteMapping("/conversation/{userId}")
    @RequiresRole({"MANAGER", "TOP_MANAGER", "DIRECTOR"})
    @RequiresPermission("MESSAGE_DELETE")
    @Operation(summary = "Delete entire conversation")
    public ResponseEntity<ApiResponse<Void>> deleteConversation(
            @PathVariable Long userId) {
        log.info("Deleting conversation with: {}", userId);
        Long currentUserId = 1L;
        messageService.deleteConversation(currentUserId, userId);
        return ResponseEntity.ok(ApiResponse.success("Conversation deleted successfully", null));
    }
}