package com.zhijia.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.zhijia.crowd.entity.Role;

import java.util.List;

/**
 * @author zhijia
 * @create 2022-03-18 12:16
 */
public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    void saveRole(Role role);

    void updateRole(Role role);

    void removeRole(List<Integer> roleId);

    List<Role> getAssigndRole(Integer adminId);

    List<Role> getUnAssigndRole(Integer adminId);
}
