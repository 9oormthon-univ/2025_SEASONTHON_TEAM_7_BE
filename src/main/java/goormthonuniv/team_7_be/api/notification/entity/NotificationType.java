package goormthonuniv.team_7_be.api.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationType {
    SYSTEM("SYSTEM", "시스템 알림"),
    MESSAGE("MESSAGE", "메시지 알림"),
    COFFEE_CHAT_REQUEST("COFFEE_CHAT_REQUEST", "커피챗 요청 알림"),
    COFFEE_CHAT_DECLINE("COFFEE_CHAT_DECLINE", "커피챗 거절 알림"),
    MANNER_REVIEW("MANNER_REVIEW", "매너 후기 알림"),
    ;

    private final String key;
    private final String title;
}
