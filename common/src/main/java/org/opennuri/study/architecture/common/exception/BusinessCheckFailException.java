package org.opennuri.study.architecture.common.exception;


/**
 * 비즈니스 체크 실패 예외
 */
public class BusinessCheckFailException extends BusinessException{

    public BusinessCheckFailException(String message) {
        super(message);
    }
}
