package goormthonuniv.team_7_be.api.chat.dto.response;

import goormthonuniv.team_7_be.api.chat.entity.ChatMessage;
import goormthonuniv.team_7_be.api.chat.entity.MessageContentType;
import goormthonuniv.team_7_be.api.chat.entity.MessageType;

import java.time.LocalDateTime;

public record ChatMessageResponse(
    Long messageId,
    Long chatRoomId,
    Long senderId,
    String senderName,
    String content,
    MessageType messageType,
    MessageContentType contentType,
    LocalDateTime createdAt
) {
    public static ChatMessageResponse from(ChatMessage chatMessage) {
        return new ChatMessageResponse(
            chatMessage.getId(),
            chatMessage.getChatRoom().getId(),
            chatMessage.getSender().getId(),
            chatMessage.getSender().getNickname(),
            chatMessage.getContent(),
            chatMessage.getMessageType(),
            chatMessage.getMessageContentType(),
            chatMessage.getCreatedAt()
        );
    }
}