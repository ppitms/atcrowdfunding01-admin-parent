package com.zhijia.crowd.mvc.config;

import com.google.gson.Gson;
import com.zhijia.crowd.constart.CrowdConstant;
import com.zhijia.crowd.entity.ResultEntity;
import com.zhijia.crowd.exception.LoginAcctAlreadyInUseException;
import com.zhijia.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.zhijia.crowd.exception.LoginFailedException;
import com.zhijia.crowd.util.CrowdUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**异常处理
 * @author zhijia
 * @create 2022-03-15 11:32
 */
//基于注解的异常处理器类
@ControllerAdvice
public class CrowdExceptionResolver {
    //保存更新Admin时账号重复抛出异常的处理——更新
    @ExceptionHandler(value = LoginAcctAlreadyInUseForUpdateException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseForUpdateException(LoginAcctAlreadyInUseForUpdateException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-error";
        return commonResoolve(viewName,exception,request,response);
    }

    //保存更新Admin时账号重复抛出异常的处理——添加
    @ExceptionHandler(value = LoginAcctAlreadyInUseException.class)
    public ModelAndView resolveLoginAcctAlreadyInUseException(LoginAcctAlreadyInUseException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "admin-add";
        return commonResoolve(viewName,exception,request,response);
    }

    //登录异常
    @ExceptionHandler(value = Exception.class)
    public ModelAndView resolveArithmeticException(Exception exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResoolve(viewName,exception,request,response);
    }
    //数学运算异常
    @ExceptionHandler(value = ArithmeticException.class)
    public ModelAndView resolveArithmeticException(ArithmeticException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResoolve(viewName,exception,request,response);
    }

    //将具体异常类型和方法关联起来(空指针异常)
    @ExceptionHandler(value = NullPointerException.class)
    public ModelAndView resolveNullPointerException(NullPointerException exception, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String viewName = "system-error";
        return commonResoolve(viewName,exception,request,response);
    }

    private ModelAndView commonResoolve(String viewName,Exception exception,HttpServletRequest request,HttpServletResponse response) throws IOException {
        //1、判断请求类型
        boolean judgeRequest = CrowdUtil.judgeRequestType(request);
        //2、ajax请求
        if(judgeRequest){
            //3、创建ResultEntity对象
            ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
            //4、转Gson对象
            Gson gson=new Gson();
            //5、将对象转JSON字符串
            String json = gson.toJson(resultEntity);
            //6、响应体
            response.getWriter().write(json);
            //7、用response响应过了，所以不提供ModelAndView对象
            return null;
        }
        //8、创建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        //9、异常存入模型
        modelAndView.addObject(CrowdConstant.ATTR_NAME_EXCEPTION,exception);
        //10、对应视图名称
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

}
