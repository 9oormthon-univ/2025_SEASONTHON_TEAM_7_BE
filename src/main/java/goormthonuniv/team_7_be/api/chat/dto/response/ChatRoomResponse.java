package goormthonuniv.team_7_be.api.chat.dto.response;

import java.time.LocalDateTime;

import goormthonuniv.team_7_be.api.chat.entity.ChatRoom;
import goormthonuniv.team_7_be.api.member.entity.Member;

public record ChatRoomResponse(
    Long chatRoomId,
    Long member1Id,
    String member1Name,
    Long member2Id,
    String member2Name,
    LocalDateTime createdAt
) {
    public  static ChatRoomResponse from(ChatRoom chatRoom, Member member1, Member member2) {
        return new ChatRoomResponse(
            chatRoom.getId(),
            member1.getId(),
            member1.getNickname(),
            member2.getId(),
            member2.getNickname(),
            chatRoom.getCreatedAt()
        );

    }
}