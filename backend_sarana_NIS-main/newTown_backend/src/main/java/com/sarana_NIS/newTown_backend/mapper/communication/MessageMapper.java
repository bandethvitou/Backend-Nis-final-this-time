package com.sarana_NIS.newTown_backend.mapper.communication;

import com.sarana_NIS.newTown_backend.dto.request.communication.MessageRequest;
import com.sarana_NIS.newTown_backend.dto.response.communication.MessageResponse;
import com.sarana_NIS.newTown_backend.dto.response.communication.MessageThreadResponse;
import com.sarana_NIS.newTown_backend.dto.response.communication.UnreadMessageResponse;
import com.sarana_NIS.newTown_backend.model.entity.communication.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface MessageMapper {

    // ========== Entity → Response ==========
    @Mapping(target = "id", source = "id")
    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "senderName", source = "sender.fullName")
    @Mapping(target = "receiverId", source = "receiver.id")
    @Mapping(target = "receiverName", source = "receiver.fullName")
    @Mapping(target = "parentMessageId", source = "parentMessage.id")
    @Mapping(target = "hasReply", expression = "java(hasReply(message))")
    MessageResponse toResponse(Message message);

    // ========== Request → Entity ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "receiver", ignore = true)
    @Mapping(target = "parentMessage", ignore = true)
    @Mapping(target = "isRead", constant = "false")
    @Mapping(target = "readAt", ignore = true)
    Message toEntity(MessageRequest request);

    // ========== Update ==========
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "sender", ignore = true)
    @Mapping(target = "receiver", ignore = true)
    @Mapping(target = "parentMessage", ignore = true)
    @Mapping(target = "isRead", ignore = true)
    @Mapping(target = "readAt", ignore = true)
    void updateEntity(MessageRequest request, @MappingTarget Message message);

    // ========== Thread Response ==========
    default MessageThreadResponse toThreadResponse(Message parent, List<Message> replies) {
        if (parent == null) return null;

        MessageThreadResponse response = new MessageThreadResponse();
        response.setId(parent.getId());
        response.setSubject(parent.getSubject());

        // Original message
        MessageThreadResponse.MessageInfo original = new MessageThreadResponse.MessageInfo();
        original.setId(parent.getId());
        original.setSenderName(parent.getSender() != null ? parent.getSender().getFullName() : null);
        original.setContent(parent.getContent());
        original.setSentAt(parent.getCreatedAt());
        response.setOriginalMessage(original);

        // Replies
        if (replies != null) {
            response.setReplies(replies.stream()
                    .map(r -> {
                        MessageThreadResponse.ReplyInfo reply = new MessageThreadResponse.ReplyInfo();
                        reply.setId(r.getId());
                        reply.setSenderName(r.getSender() != null ? r.getSender().getFullName() : null);
                        reply.setContent(r.getContent());
                        reply.setSentAt(r.getCreatedAt());
                        reply.setIsRead(r.getIsRead());
                        return reply;
                    })
                    .collect(Collectors.toList()));
        }

        response.setTotalMessages(response.getReplies().size() + 1);
        response.setIsRead(parent.getIsRead());

        // Last activity
        if (replies != null && !replies.isEmpty()) {
            Message last = replies.get(replies.size() - 1);
            response.setLastActivity(last.getCreatedAt());
        } else {
            response.setLastActivity(parent.getCreatedAt());
        }

        return response;
    }

    // ========== Unread Response ==========
    default UnreadMessageResponse toUnreadResponse(List<Message> unreadMessages) {
        if (unreadMessages == null) return new UnreadMessageResponse();

        UnreadMessageResponse response = new UnreadMessageResponse();
        response.setTotalUnread((long) unreadMessages.size());

        response.setMessages(unreadMessages.stream()
                .map(m -> {
                    UnreadMessageResponse.UnreadMessageInfo info = new UnreadMessageResponse.UnreadMessageInfo();
                    info.setId(m.getId());
                    info.setSenderName(m.getSender() != null ? m.getSender().getFullName() : null);
                    info.setSubject(m.getSubject());
                    info.setPreview(m.getContent() != null && m.getContent().length() > 100 ?
                            m.getContent().substring(0, 100) + "..." :
                            m.getContent());
                    info.setSentAt(m.getCreatedAt());
                    return info;
                })
                .collect(Collectors.toList()));

        return response;
    }

    // ========== Helper Methods ==========

    default boolean hasReply(Message message) {
        if (message == null) return false;
        // A message has a reply if it has child messages
        // This would need to be checked via repository
        return message.getParentMessage() != null;
    }
}