package goormthonuniv.team_7_be.api.manner.exception;

import org.springframework.http.HttpStatus;

import goormthonuniv.team_7_be.common.exception.ExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MannerExceptionType implements ExceptionType {
    MANNER_NOT_FOUND(HttpStatus.NOT_FOUND, "MANNER-001", "매너 평가를 찾을 수 없습니다."),
    CANNOT_EVALUATE_YOURSELF(HttpStatus.BAD_REQUEST, "MANNER-002", "자기 자신에게 매너 평가를 할 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
