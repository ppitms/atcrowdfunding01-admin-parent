package com.zhijia.crowd.mvc.config;

import com.zhijia.crowd.constart.CrowdConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zhijia
 * @create 2022-03-22 20:38
 */
@Configuration
@EnableWebSecurity//启用web环境下权限控制功能
@EnableGlobalMethodSecurity(prePostEnabled = true)//启用全局方法权限控制，pre...是使注解生效
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
//        builder.inMemoryAuthentication()        //内存版
//                .withUser("tom")
//                .password("12312")
//                .roles("ADMIN");
        builder.userDetailsService(userDetailsService)//使用数据库的认证
                .passwordEncoder(getPasswordEncoder());
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
//                .antMatchers("/admin/get/page.html")
//                .hasRole("经理")                                        //具备角色才能访问
                .anyRequest()                                           //其他请求
                .authenticated()                                        //认证后可访问
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        httpServletRequest.setAttribute("exception",new Exception(CrowdConstant.MESSAGE_ACCESS_DENIEC));
                        httpServletRequest.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(httpServletRequest,httpServletResponse);
                    }
                })
                .and()
                .csrf()
                .disable()
                .formLogin()                                            //开启表单登录功能
                .loginPage("/admin/to/login/page.html")                 //登录页面
                .loginProcessingUrl("/security/do/login.html")          //处理登录请求地址
                .defaultSuccessUrl("/admin/to/main/page.html")          //登录成功后前往地址
                .usernameParameter("loginAcct")                         //账号请求参数名称
                .passwordParameter("userPswd")                          //密码请求参数名称
                .and()
                .logout()                                               //开启退出登录
                .logoutUrl("/security/do/logout.html")                  //退出地址
                .logoutSuccessUrl("/admin/to/login/page.html")          //退出地址
                ;
    }

}
