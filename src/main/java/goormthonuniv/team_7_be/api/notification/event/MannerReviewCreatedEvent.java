package goormthonuniv.team_7_be.api.notification.event;

import goormthonuniv.team_7_be.api.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MannerReviewCreatedEvent {
    private final Member reviewer; // 후기 작성자
    private final Member target;   // 후기 대상자 (알림 수신자)
    private final int rate;
    private final String review;
}
