package goormthonuniv.team_7_be.api.chat.dto.request;

import goormthonuniv.team_7_be.api.chat.entity.MessageContentType;
import goormthonuniv.team_7_be.api.chat.entity.MessageType;

public record ChatMessageRequest(
    Long chatRoomId,
    Long senderId,
    String content,
    MessageType messageType,
    MessageContentType contentType
) {
}
