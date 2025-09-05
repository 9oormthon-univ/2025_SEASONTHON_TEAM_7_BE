package goormthonuniv.team_7_be.api.member.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberAge {

    TEENAGER("10대"),
    TWENTIES("20대"),
    THIRTIES("30대"),
    FORTIES("40대"),
    FIFTIES("50대"),
    SIXTIES_PLUS("60대 이상"); // 60+는 보통 이렇게 표현합니다.

    private final String displayName;
}