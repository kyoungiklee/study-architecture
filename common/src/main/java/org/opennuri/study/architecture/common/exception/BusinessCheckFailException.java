package org.opennuri.study.architecture.common.exception;


/**
 * 비즈니스 체크 실패 예외
 */
public class BusinessCheckFailException extends BusinessException{

    public BusinessCheckFailException(String message) {
        super(message);
    }

    public BusinessCheckFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessCheckFailException(Throwable cause) {
        super(cause);
    }

    protected BusinessCheckFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
