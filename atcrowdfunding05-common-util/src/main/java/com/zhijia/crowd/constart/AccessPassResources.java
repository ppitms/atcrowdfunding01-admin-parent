package com.zhijia.crowd.constart;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zhijia
 * @create 2022-03-30 10:57
 */
public class AccessPassResources {

    public static final Set<String> PASS_RES_SET=new HashSet<>();
    public static final Set<String> STATIC_RES_SET=new HashSet<>();

    //不需要拦截的页面
    static {
        PASS_RES_SET.add("/");      //主页面
        PASS_RES_SET.add("/auth/member/to/reg/page");       //注册页面
        PASS_RES_SET.add("/auth/member/to/login/page");     //登录页面
        PASS_RES_SET.add("/auth/member/logout");            //退出登录
        PASS_RES_SET.add("/auth/member/do/login");          //点击登录
        PASS_RES_SET.add("/auth/do/member/register");       //点击注册
        PASS_RES_SET.add("/auth/member/send/short/message.json");       //点击获取验证码
//        PASS_RES_SET.add("/project/get/project/detail/8");       //点击获取验证码
//        PASS_RES_SET.add("/agree/protocol/page");       //点击获取验证码
    }

    //不需要拦截的静态文件
    static{
        STATIC_RES_SET.add("bootstrap");
        STATIC_RES_SET.add("css");
        STATIC_RES_SET.add("fonts");
        STATIC_RES_SET.add("img");
        STATIC_RES_SET.add("jquery");
        STATIC_RES_SET.add("layer");
        STATIC_RES_SET.add("script");
        STATIC_RES_SET.add("ztree");
    }

    //判断某个servletpath的值是否对应某个静态资源
    public static boolean judegeCurrentServletPathWetherStaticResource(String servletPath){
        if(servletPath==null||servletPath.length()==0){
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        String[] split = servletPath.split("/");
        //第一个为空，取第二个
        String firstLevelPath = split[1];
        return STATIC_RES_SET.contains(firstLevelPath);
    }

//    public static void main(String[] args) {
//        boolean b = judegeCurrentServletPathWetherStaticResource("/");
//        System.out.println(b);
//    }
}
