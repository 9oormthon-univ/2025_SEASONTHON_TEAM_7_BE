package goormthonuniv.team_7_be.api.coffee.exception;

import org.springframework.http.HttpStatus;

import goormthonuniv.team_7_be.common.exception.ExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CoffeeChatExceptionType implements ExceptionType {
    SELF_COFFEE_CHAT_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "COFFEE-001", "자기 자신에게 커피챗을 보낼 수 없습니다."),
    DUPLICATE_COFFEE_CHAT_REQUEST(HttpStatus.BAD_REQUEST, "COFFEE-002", "이미 커피챗 요청을 보냈습니다.")
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
