package goormthonuniv.team_7_be.api.chat.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatRoomStatus {
    OPEN("OPEN", "열림"),
    CLOSED("CLOSED", "닫힘");

    private final String key;
    private final String title;
}
