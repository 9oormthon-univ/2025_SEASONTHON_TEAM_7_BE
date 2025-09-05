package goormthonuniv.team_7_be.api.chat.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import goormthonuniv.team_7_be.api.chat.entity.ChatMessage;
import goormthonuniv.team_7_be.api.chat.entity.ChatRoom;
import goormthonuniv.team_7_be.api.member.entity.Member;

public record ChatRoomListResponse(
    Long chatRoomId,
    Long otherMemberId,
    String otherMemberName,
    @JsonInclude(JsonInclude.Include.NON_NULL) String lastMessage,
    @JsonInclude(JsonInclude.Include.NON_NULL) LocalDateTime lastMessageAt,
    Integer unreadCount
) {
    public static ChatRoomListResponse from(
        ChatRoom chatRoom,
        Member otherMember,
        ChatMessage lastMessage,
        int unreadCount
    ) {
        return new ChatRoomListResponse(
            chatRoom.getId(),
            otherMember.getId(),
            otherMember.getNickname(),
            lastMessage != null ? lastMessage.getContent() : null,
            lastMessage != null ? lastMessage.getCreatedAt() : null,
            unreadCount
        );
    }
}
