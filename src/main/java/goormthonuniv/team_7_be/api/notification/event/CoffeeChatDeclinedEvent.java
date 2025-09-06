package goormthonuniv.team_7_be.api.notification.event;

import goormthonuniv.team_7_be.api.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CoffeeChatDeclinedEvent {
    private final Member requester; // 원래 요청을 보낸 사람 (알림 수신자)
    private final Member decliner;  // 거절한 사람
}
