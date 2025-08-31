package goormthonuniv.team_7_be.api.chat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MessageType {

    SYSTEM("system", "시스템"),
    CHAT("chat", "채팅");

    private final String key;
    private final String title;
}
