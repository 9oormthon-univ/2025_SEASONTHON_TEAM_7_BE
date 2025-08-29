package goormthonuniv.team_7_be.common.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionType {

    HttpStatus getHttpStatus();
    String getErrorCode();
    String getMessage();
}