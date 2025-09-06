package goormthonuniv.team_7_be.api.notification.event;

import goormthonuniv.team_7_be.api.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CoffeeChatRequestedEvent {
    private final Member sender;   // 요청을 보낸 사람
    private final Member receiver; // 요청을 받은 사람
}
