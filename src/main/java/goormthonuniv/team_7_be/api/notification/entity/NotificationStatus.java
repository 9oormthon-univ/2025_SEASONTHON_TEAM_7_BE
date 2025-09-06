package goormthonuniv.team_7_be.api.notification.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotificationStatus {
    UNREAD("UNREAD", "읽지 않음"),
    READ("READ", "읽음"),
    ;

    private final String key;
    private final String title;
}
