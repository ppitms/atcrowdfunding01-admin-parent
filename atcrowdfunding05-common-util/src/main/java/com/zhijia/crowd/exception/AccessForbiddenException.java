package com.zhijia.crowd.exception;

/**未登录并访问受保护的资源 的异常
 * @author zhijia
 * @create 2022-03-16 11:04
 */
public class AccessForbiddenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AccessForbiddenException() {
    }

    public AccessForbiddenException(String message) {
        super(message);
    }

    public AccessForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccessForbiddenException(Throwable cause) {
        super(cause);
    }

    public AccessForbiddenException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
