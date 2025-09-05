package goormthonuniv.team_7_be.api.coffee.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoffeeChatStatus {
    REQUESTED("REQUESTED", "요청됨"),
    ACCEPTED("ACCEPTED", "수락됨"),
    DECLINED("DECLINED", "거절됨"),
    COMPLETED("COMPLETED", "완료됨");

    private final String key;
    private final String title;

}
