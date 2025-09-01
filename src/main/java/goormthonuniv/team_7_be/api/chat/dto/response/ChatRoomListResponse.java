package goormthonuniv.team_7_be.api.chat.dto.response;

import java.time.LocalDateTime;

public record ChatRoomListResponse(
    Long chatRoomId,
    Long otherMemberId,
    String otherMemberName,
    String lastMessage,
    LocalDateTime lastMessageAt,
    Integer unreadCount
) {}