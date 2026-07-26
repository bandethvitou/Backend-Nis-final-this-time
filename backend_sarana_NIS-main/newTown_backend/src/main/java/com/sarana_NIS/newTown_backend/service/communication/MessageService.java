package com.sarana_NIS.newTown_backend.service.communication;

import com.sarana_NIS.newTown_backend.dto.request.communication.MessageBulkRequest;
import com.sarana_NIS.newTown_backend.dto.request.communication.MessageThreadRequest;
import com.sarana_NIS.newTown_backend.dto.response.common.PageResponse;
import com.sarana_NIS.newTown_backend.dto.response.communication.MessageThreadResponse;
import com.sarana_NIS.newTown_backend.dto.response.communication.UnreadMessageResponse;
import com.sarana_NIS.newTown_backend.model.entity.communication.Message;
import com.sarana_NIS.newTown_backend.model.entity.core.User;
import com.sarana_NIS.newTown_backend.repository.MessageRepository;
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
import java.util.stream.Collectors;

@Service
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    // ============================================================
    // SEND MESSAGE
    // ============================================================

    @Transactional
    public Message sendMessage(Long senderId, Long receiverId, String subject, String content) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setSubject(subject);
        message.setContent(content);
        message.setIsRead(false);

        Message saved = messageRepository.save(message);
        log.info("Message sent from {} to {}", sender.getUsername(), receiver.getUsername());
        return saved;
    }

    // ============================================================
    // SEND BULK MESSAGES
    // ============================================================

    @Transactional
    public List<Message> sendBulkMessages(MessageBulkRequest request, Long senderId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        List<User> receivers = userRepository.findAllById(request.getReceiverIds());
        List<Message> messages = new ArrayList<>();

        for (User receiver : receivers) {
            Message message = new Message();
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setSubject(request.getSubject());
            message.setContent(request.getContent());
            message.setIsRead(false);
            messages.add(message);
        }

        List<Message> saved = messageRepository.saveAll(messages);
        log.info("Bulk messages sent to {} recipients", saved.size());
        return saved;
    }

    // ============================================================
    // REPLY TO MESSAGE (Thread)
    // ============================================================

    @Transactional
    public Message replyToMessage(MessageThreadRequest request, Long senderId) {
        Message parentMessage = messageRepository.findById(request.getParentMessageId())
                .orElseThrow(() -> new RuntimeException("Parent message not found"));

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        // Determine receiver (the other party)
        User receiver;
        if (parentMessage.getSender().getId().equals(senderId)) {
            receiver = parentMessage.getReceiver();
        } else {
            receiver = parentMessage.getSender();
        }

        Message reply = new Message();
        reply.setSender(sender);
        reply.setReceiver(receiver);
        reply.setParentMessage(parentMessage);
        reply.setSubject("Re: " + parentMessage.getSubject());
        reply.setContent(request.getContent());
        reply.setIsRead(false);

        Message saved = messageRepository.save(reply);
        log.info("Reply sent to message: {}", request.getParentMessageId());
        return saved;
    }

    // ============================================================
    // MARK AS READ
    // ============================================================

    @Transactional
    public void markAsRead(Long messageId, Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (!message.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("Only receiver can mark message as read");
        }

        message.setIsRead(true);
        message.setReadAt(LocalDateTime.now());
        messageRepository.save(message);
        log.info("Message marked as read: {}", messageId);
    }

    @Transactional
    public void markAllAsRead(Long userId) {
        List<Message> unread = messageRepository.findUnreadByReceiver(userId);
        for (Message message : unread) {
            message.setIsRead(true);
            message.setReadAt(LocalDateTime.now());
        }
        messageRepository.saveAll(unread);
        log.info("All messages marked as read for user: {}", userId);
    }

    // ============================================================
    // GET MESSAGES
    // ============================================================

    public Message getMessageById(Long messageId) {
        return messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
    }

    public PageResponse<Message> getReceivedMessages(Long userId, Pageable pageable) {
        Page<Message> page = messageRepository.findReceivedByReceiver(userId, pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public PageResponse<Message> getSentMessages(Long userId, Pageable pageable) {
        Page<Message> page = messageRepository.findSentBySender(userId, pageable);
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public List<Message> getConversation(Long userId1, Long userId2) {
        return messageRepository.findConversationBetween(userId1, userId2);
    }

    public MessageThreadResponse getMessageThread(Long parentMessageId) {
        Message parent = messageRepository.findById(parentMessageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        List<Message> replies = messageRepository.findThreadByParentId(parentMessageId);

        MessageThreadResponse response = new MessageThreadResponse();
        response.setId(parent.getId());
        response.setSubject(parent.getSubject());

        // Original message
        MessageThreadResponse.MessageInfo original = new MessageThreadResponse.MessageInfo();
        original.setId(parent.getId());
        original.setSenderName(parent.getSender().getFullName());
        original.setContent(parent.getContent());
        original.setSentAt(parent.getCreatedAt());
        response.setOriginalMessage(original);

        // Replies
        List<MessageThreadResponse.ReplyInfo> replyInfos = replies.stream()
                .map(reply -> {
                    MessageThreadResponse.ReplyInfo info = new MessageThreadResponse.ReplyInfo();
                    info.setId(reply.getId());
                    info.setSenderName(reply.getSender().getFullName());
                    info.setContent(reply.getContent());
                    info.setSentAt(reply.getCreatedAt());
                    info.setIsRead(reply.getIsRead());
                    return info;
                })
                .collect(Collectors.toList());
        response.setReplies(replyInfos);
        response.setTotalMessages(1 + replies.size());

        return response;
    }

    // ✅ FIXED: Returns UnreadMessageResponse
    public UnreadMessageResponse getUnreadMessages(Long userId) {
        List<Message> unread = messageRepository.findUnreadByReceiver(userId);

        UnreadMessageResponse response = new UnreadMessageResponse();
        response.setTotalUnread((long) unread.size());

        List<UnreadMessageResponse.UnreadMessageInfo> messages = unread.stream()
                .map(msg -> {
                    UnreadMessageResponse.UnreadMessageInfo um = new UnreadMessageResponse.UnreadMessageInfo();
                    um.setId(msg.getId());
                    um.setSenderName(msg.getSender().getFullName());
                    um.setSubject(msg.getSubject());
                    um.setPreview(msg.getContent().length() > 100 ?
                            msg.getContent().substring(0, 100) + "..." :
                            msg.getContent());
                    um.setSentAt(msg.getCreatedAt());
                    return um;
                })
                .collect(Collectors.toList());

        response.setMessages(messages);
        return response;
    }

    public long countUnreadMessages(Long userId) {
        return messageRepository.countUnreadByReceiver(userId);
    }

    // ============================================================
    // DELETE
    // ============================================================

    @Transactional
    public void deleteMessage(Long messageId, Long userId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));

        if (!message.getSender().getId().equals(userId) &&
                !message.getReceiver().getId().equals(userId)) {
            throw new RuntimeException("You don't have permission to delete this message");
        }

        messageRepository.delete(message);
        log.info("Message deleted: {}", messageId);
    }

    @Transactional
    public void deleteConversation(Long userId1, Long userId2) {
        List<Message> conversation = messageRepository.findConversationBetween(userId1, userId2);
        messageRepository.deleteAll(conversation);
        log.info("Conversation deleted between {} and {}", userId1, userId2);
    }
}