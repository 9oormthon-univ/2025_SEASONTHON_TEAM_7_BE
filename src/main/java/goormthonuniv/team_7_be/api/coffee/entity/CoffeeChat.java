package goormthonuniv.team_7_be.api.coffee.entity;

import goormthonuniv.team_7_be.api.member.entity.Member;
import goormthonuniv.team_7_be.common.utils.BaseTimeEntity;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoffeeChat extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Member receiver;

    @Enumerated(EnumType.STRING)
    private CoffeeChatStatus status;

    @Builder
    private CoffeeChat(Member sender, Member receiver, CoffeeChatStatus status) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

    public void updateStatus(CoffeeChatStatus status) {
        this.status = status;
    }

}
