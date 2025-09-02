package goormthonuniv.team_7_be.api.chat.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import goormthonuniv.team_7_be.api.chat.entity.ChatMessage;
import goormthonuniv.team_7_be.api.chat.entity.ChatRoom;
import goormthonuniv.team_7_be.api.member.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRoomListResponse {
    private final Long chatRoomId;
    private final Long otherMemberId;
    private final String otherMemberName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String lastMessage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final LocalDateTime lastMessageAt;
    private final Integer unreadCount;

    public ChatRoomListResponse(Long chatRoomId, Long otherMemberId, String otherMemberName, String lastMessage, LocalDateTime lastMessageAt, Integer unreadCount) {
        this.chatRoomId = chatRoomId;
        this.otherMemberId = otherMemberId;
        this.otherMemberName = otherMemberName;
        this.lastMessage = lastMessage;
        this.lastMessageAt = lastMessageAt;
        this.unreadCount = unreadCount;
    }

    public static ChatRoomListResponse from(ChatRoom chatRoom, Member otherMember, ChatMessage lastMessage, int unreadCount) {
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
