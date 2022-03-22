package com.zhijia.crowd.exception;

/**保存更新Admin时账号重复抛出异常
 * @author zhijia
 * @create 2022-03-17 21:19
 */
public class LoginAcctAlreadyInUseForUpdateException extends RuntimeException {

    private static final long serialVersionUID=1L;

    public LoginAcctAlreadyInUseForUpdateException() {
    }

    public LoginAcctAlreadyInUseForUpdateException(String message) {
        super(message);
    }

    public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginAcctAlreadyInUseForUpdateException(Throwable cause) {
        super(cause);
    }

    public LoginAcctAlreadyInUseForUpdateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
