package goormthonuniv.team_7_be.api.chat.entity;

import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.common.utils.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageReceipt extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_receipt_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_message_id")
    private ChatMessage chatMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @Enumerated(EnumType.STRING)
    private MessageReceiptStatus status;

    @Builder
    public MessageReceipt(ChatMessage chatMessage, Member member, ChatRoom chatRoom, MessageReceiptStatus status) {
        this.chatMessage = chatMessage;
        this.member = member;
        this.chatRoom = chatRoom;
        this.status = status;
    }
}

