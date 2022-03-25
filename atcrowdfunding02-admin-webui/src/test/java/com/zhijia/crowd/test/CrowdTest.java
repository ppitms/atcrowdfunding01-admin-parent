package com.zhijia.crowd.test;

import com.zhijia.crowd.entity.Admin;
import com.zhijia.crowd.entity.Role;
import com.zhijia.crowd.mapper.AdminMapper;
import com.zhijia.crowd.mapper.AuthMapper;
import com.zhijia.crowd.mapper.RoleMapper;
import com.zhijia.crowd.service.api.AdminService;
import com.zhijia.crowd.service.api.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**测试mybatis数据源
 * @author zhijia
 * @create 2022-03-14 12:04
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml","classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthMapper authMapper;

    @Test
    public void testInsertAdmin(){
        Admin admin = new Admin(null, "Tom", "12312", "tmu", "tom@qq.com", null);
        int insert = adminMapper.insert(admin);
        System.out.println("受影响的行数:"+insert);
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testTx(){
        Admin admin = new Admin(null, "Jelly", "234234", "继而", "jerrj@3434.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void test(){
        for (int i = 0; i < 200; i++) {
            adminMapper.insert(new Admin(null,"dfdf"+i,"fdff"+i,"dfdf"+i,"dfdf"+i,null));
        }
    }

    @Test
    public void testRoleSave(){
        for (int i = 0; i < 50; i++) {
            roleMapper.insert(new Role(null,"name"+i));
        }
    }


    @Test
    public void password(){
        System.out.println(passwordEncoder.encode("12312"));
    }

    @Test
    public void testAuth(){
        List<String> strings = authMapper.selectAssignedAuthNameByAdminId(206);
        for (String string : strings) {
            System.out.println(string);
        }

    }

}
