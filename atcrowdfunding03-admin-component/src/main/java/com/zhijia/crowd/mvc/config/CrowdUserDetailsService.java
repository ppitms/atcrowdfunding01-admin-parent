package com.zhijia.crowd.mvc.config;

import com.zhijia.crowd.entity.Admin;
import com.zhijia.crowd.entity.Role;
import com.zhijia.crowd.service.api.AdminService;
import com.zhijia.crowd.service.api.AuthService;
import com.zhijia.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhijia
 * @create 2022-03-23 14:01
 */
@Component
public class CrowdUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据账号名称查admin对象
        Admin admin=adminService.getAdminByLoginAcct(username);
        //获取id
        Integer id = admin.getId();
        //根据用户查询角色信息
        List<Role> assigndRole = roleService.getAssigndRole(id);
        //根据adminId查询权限信息
        List<String> authNameList = authService.getAssignedAuthNameByAdminId(id);
        //存储GrantedAuthority
        List<GrantedAuthority> authorities = new ArrayList<>();
        //遍历List<GrantedAuthority>存入角色信息
        for (Role role : assigndRole) {
            String roleName="ROLE_"+role.getName();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(simpleGrantedAuthority);
        }
        //遍历authNameList存入权限信息
        for (String authName : authNameList) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authName);
            authorities.add(simpleGrantedAuthority);
        }
        return new SecurityAdmin(admin,authorities);
    }
}
