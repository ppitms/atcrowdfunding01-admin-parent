package com.zhijia.crowd.exception;

/**失败后抛出异常——登录异常
 * @author zhijia
 * @create 2022-03-15 21:58
 */
public class LoginFailedException extends RuntimeException {

    private static final long serialVersionUID=1L;

    public LoginFailedException() {
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoginFailedException(Throwable cause) {
        super(cause);
    }

    public LoginFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
