package com.zhijia.crowd.mvc.interceptor;

import com.zhijia.crowd.constart.CrowdConstant;
import com.zhijia.crowd.entity.Admin;
import com.zhijia.crowd.exception.AccessForbiddenException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**拦截器
 * @author zhijia
 * @create 2022-03-16 10:56
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //1、获取session对象
        HttpSession session = request.getSession();
        //2、获取admin对象
        Admin admin = (Admin) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN);
        //3、判断是否为空
        if(admin==null){
            //4、抛异常
            throw new AccessForbiddenException(CrowdConstant.MESSAGE_ACCESS_FORBIDEN);
        }
        //5、不为空，放行
        return true;
    }
}
