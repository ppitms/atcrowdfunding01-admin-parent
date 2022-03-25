package com.zhijia.crowd.mvc.config;

import com.zhijia.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**为了封装admin对象并添加List<GrantedAuthority>
 * @author zhijia
 * @create 2022-03-23 12:50
 */
public class SecurityAdmin extends User {

    private static final long serialVersionUID = 42L;
    private Admin originalAdmin;

    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority>authorities){
        super(originalAdmin.getUserName(),originalAdmin.getUserPswd(),authorities);
        this.originalAdmin=originalAdmin;
        //将admin里面的密码擦除，安全性会更高
        this.originalAdmin.setUserPswd(null);
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }
}
