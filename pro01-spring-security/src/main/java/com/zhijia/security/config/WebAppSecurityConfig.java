package com.zhijia.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhijia
 * @create 2022-03-21 22:11
 */
//标记为配置类
@Configuration
@EnableWebSecurity//启用web环境下权限监控功能
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication()            //内存中完成账号密码检查
                .withUser("tom")            //指定账户
                .password("123123")                    //指定密码
                .roles("ADMIN","学徒")                     //指定当前用户角色
                .and()
                .withUser("jerry")            //指定账户
                .password("123123")                    //指定密码
                .authorities("UPDATE","内门弟子")                     //指定当前用户权限
                ;
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {

        security.authorizeRequests()                         //请求授权
                .antMatchers("/index.jsp")      //路径授权
                .permitAll()                                 //无条件访问
                .antMatchers("/layui/**")       //开放目录
                .permitAll()                                 //无条件访问
                .antMatchers("/level1/**")       //开放目录
                .hasRole("学徒")                              //访问条件需学徒角色
                .antMatchers("/level2/**")       //开放目录
                .hasAuthority("内门弟子")                     //访问条件需某权限
                .antMatchers("/level3/**")       //开放目录
                .hasRole("学徒")                              //访问条件需学徒角色
                .and()
                .authorizeRequests()                         //请求授权
                .anyRequest()                                //任意请求
                .authenticated()                            //需要登录后才能访问
                .and()
                .formLogin()                                 //使用表单形式登录
                .loginPage("/index.jsp")                     //指定登录页面(访问未授权页面会跳转到此页面)
                .loginProcessingUrl("/do/login.html")        //指定提交表单地址
                .permitAll()                                 //无条件访问
                .usernameParameter("loginAcct")              //登录账号
                .passwordParameter("userPswd")               //登录密码
                .defaultSuccessUrl("/main.html")             //登录成功前往地址
                .and()
//                .csrf()
//                .disable()                                   //禁用csrf
                .logout()                                    //开启退出功能
                .logoutUrl("/do/logout.html")                   //退出地址
                .logoutSuccessUrl("/index.jsp")               //退出成功跳轉頁面
                .and()
                .exceptionHandling()                          //指定异常处理器
                .accessDeniedPage("/to/no/auth/page.html")      //访问被拒绝时前往的页面
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        httpServletRequest.setAttribute("message","抱歉！你无法访问这个页面***");
                        httpServletRequest.getRequestDispatcher("/WEB-INF/views/no_auth.jsp").forward(httpServletRequest,httpServletResponse);
                    }
                })
                .and()
                .rememberMe()                                   //开启记住我功能
                ;
    }
}
