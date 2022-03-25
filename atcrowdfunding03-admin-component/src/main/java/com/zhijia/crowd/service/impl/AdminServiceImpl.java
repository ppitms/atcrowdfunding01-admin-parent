package com.zhijia.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhijia.crowd.constart.CrowdConstant;
import com.zhijia.crowd.entity.Admin;
import com.zhijia.crowd.entity.AdminExample;
import com.zhijia.crowd.exception.LoginAcctAlreadyInUseException;
import com.zhijia.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.zhijia.crowd.exception.LoginFailedException;
import com.zhijia.crowd.mapper.AdminMapper;
import com.zhijia.crowd.service.api.AdminService;
import com.zhijia.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author zhijia
 * @create 2022-03-14 16:20
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public void saveAdmin(Admin admin) {
        //加密
//        String userPswd = CrowdUtil.md5(admin.getUserPswd());
        String userPswd=passwordEncoder.encode(admin.getUserPswd());
        admin.setUserPswd(userPswd);
        //生成时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(date);
        admin.setCreateTime(createTime);
        //保存
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct, String userPswd) {
        //1、查询数据库
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andLoginAcctEqualTo(loginAcct);
        List<Admin> list = adminMapper.selectByExample(adminExample);
        //2、对象是否为空
        if (list == null || list.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FATLED);
        }
        if (list.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin = list.get(0);
        //3、对象为空抛异常
        if (admin == null) {
            throw new LoginFailedException((CrowdConstant.MESSAGE_LOGIN_FATLED));
        }

        //4、不为空从数据库取密码
        String userPswdDB = admin.getUserPswd();
        //5、将表提交明文密码加密
        String userPswdForm = CrowdUtil.md5(userPswd);
        //6、比较
        if (!Objects.equals(userPswdDB, userPswdForm)) {
            //7、不一致抛异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FATLED);
        }

        //8、一致返回对象
        return admin;
    }

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {

        //1、调用PageHelper的静态方法开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        //2、查询
        List<Admin> list = adminMapper.selectAdminByKeyword(keyword);

        //3、封装PageInfo
        return new PageInfo<>(list);
    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void update(Admin admin) {
        //有选择更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        //先删除旧数据再添加新数据
        adminMapper.deleteRelationship(adminId);
        if(roleIdList!=null&&roleIdList.size()>0){
            adminMapper.insertNewRelationship(adminId,roleIdList);
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String username) {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andLoginAcctEqualTo(username);
        return adminMapper.selectByExample(adminExample).get(0);
    }

}
