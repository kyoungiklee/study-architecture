package org.opennuri.study.architecture.common.exception;

public class BusinessCreateException extends BusinessException{

    public BusinessCreateException(String message) {
        super(message);
    }

    public BusinessCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessCreateException(Throwable cause) {
        super(cause);
    }

    protected BusinessCreateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
