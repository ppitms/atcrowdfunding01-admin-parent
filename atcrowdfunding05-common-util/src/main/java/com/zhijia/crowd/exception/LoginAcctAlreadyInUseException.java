package com.zhijia.crowd.exception;

/**保存更新Admin时账号重复抛出异常
 * @author zhijia
 * @create 2022-03-17 21:19
 */
public class LoginAcctAlreadyInUseException extends RuntimeException {

    private static final long serialVersionUID=1L;

    public LoginAcctAlreadyInUseException() {
    }

    public LoginAcctAlreadyInUseException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInUseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
