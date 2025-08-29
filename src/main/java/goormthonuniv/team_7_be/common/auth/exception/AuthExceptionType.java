package goormthonuniv.team_7_be.common.auth.exception;

import org.springframework.http.HttpStatus;

import goormthonuniv.team_7_be.common.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthExceptionType implements ExceptionType {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "auth-001", "인증되지 않은 사용자입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "auth-002", "접근 권한이 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
