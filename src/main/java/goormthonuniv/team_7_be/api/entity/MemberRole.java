package goormthonuniv.team_7_be.api.entity;

import lombok.Getter;


import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRole {

    // 스프링 시큐리티에서는 권한 코드에 항상 "ROLE_" 접두사가 앞에 붙어야 합니다.
    GUEST("ROLE_GUEST", "손님"), // 추가 정보 미입력 사용자
    USER("ROLE_USER", "일반 사용자"), // 추가 정보까지 모두 입력한 사용자
    ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;
}