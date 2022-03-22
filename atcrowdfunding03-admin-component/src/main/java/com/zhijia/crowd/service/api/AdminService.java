package com.zhijia.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.zhijia.crowd.entity.Admin;

import java.util.List;

/**
 * @author zhijia
 * @create 2022-03-14 16:20
 */
public interface AdminService {

    void saveAdmin(Admin admin);

    List<Admin> getAll();

    Admin getAdminByLoginAcct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword,Integer pageNum,Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}
