package goormthonuniv.team_7_be.api.chat.exception;

import org.springframework.http.HttpStatus;

import goormthonuniv.team_7_be.common.exception.ExceptionType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatExceptionType implements ExceptionType {

    CHAT_ROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "CHATROOM-001", "채팅방을 찾을 수 없습니다."),
    CHAT_ROOM_ALREADY_EXISTS(HttpStatus.CONFLICT, "CHATROOM-002", "이미 존재하는 채팅방입니다.")
    ;

    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String message;
}
