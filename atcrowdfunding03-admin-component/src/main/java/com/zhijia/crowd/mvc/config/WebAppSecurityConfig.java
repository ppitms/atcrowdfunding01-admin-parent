package com.zhijia.crowd.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author zhijia
 * @create 2022-03-22 20:38
 */
@Configuration
@EnableWebSecurity//启用web环境下权限控制功能
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter{

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.authorizeRequests()                                      //对请求授权
                .antMatchers("/admin/to/login/page.html")    //登录页设置无条件访问
                .permitAll()
                .antMatchers("/bootstrap/**")                //静态资源设置无条件访问
                .permitAll()
                .antMatchers("/crowd/**")
                .permitAll()
                .antMatchers("/css/**")
                .permitAll()
                .antMatchers("/fonts/**")
                .permitAll()
                .antMatchers("/script/**")
                .permitAll()
                .antMatchers("/img/**")
                .permitAll()
                .antMatchers("/jquery/**")
                .permitAll()
                .antMatchers("/layer/**")
                .permitAll()
                .antMatchers("/ztree/**")
                .permitAll()
                .anyRequest()                                            //登录后可访问
                .authenticated()
                ;
    }

}
