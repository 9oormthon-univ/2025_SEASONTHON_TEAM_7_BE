package goormthonuniv.team_7_be.common.exception;

public class BaseException extends RuntimeException {

    private final ExceptionType exceptionType;

    public BaseException(ExceptionType exceptionType, Exception e) {
        super(exceptionType.getMessage(), e);
        this.exceptionType = exceptionType;
    }

    public BaseException(ExceptionType exceptionType) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
    }

    public ExceptionType exceptionType() {
        return exceptionType;
    }
}