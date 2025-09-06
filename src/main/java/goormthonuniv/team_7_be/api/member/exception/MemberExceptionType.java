package goormthonuniv.team_7_be.api.member.exception;

import org.springframework.http.HttpStatus;

import goormthonuniv.team_7_be.common.exception.ExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberExceptionType implements ExceptionType {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER-001", "멤버를 찾을 수 없습니다."),
    MEMBER_EXCEPTION_TYPE(HttpStatus.FORBIDDEN, "MEMBER-002", "권한이 없는 접근입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
