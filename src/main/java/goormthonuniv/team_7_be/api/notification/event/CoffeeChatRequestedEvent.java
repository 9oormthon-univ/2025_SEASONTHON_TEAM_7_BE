package goormthonuniv.team_7_be.api.notification.event;

import goormthonuniv.team_7_be.api.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @param sender   요청을 보낸 사람
 * @param receiver 요청을 받은 사람
 * @param referencedId 참조 ID
 */
public record CoffeeChatRequestedEvent(Member sender, Member receiver, Long referencedId) {
}
