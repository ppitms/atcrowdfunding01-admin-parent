package com.zhijia.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhijia.crowd.entity.Role;
import com.zhijia.crowd.entity.RoleExample;
import com.zhijia.crowd.mapper.RoleMapper;
import com.zhijia.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhijia
 * @create 2022-03-18 12:16
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    //分页查询
    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        //开启分页功能
        PageHelper.startPage(pageNum,pageSize);
        //查询
        List<Role> roleList = roleMapper.selectByKeyword(keyword);

        //封装为pageInfo返回
        return new PageInfo<>(roleList);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKey(role);
    }

    @Override
    public void removeRole(List<Integer> roleId) {
        RoleExample roleExample = new RoleExample();
        roleExample.createCriteria().andIdIn(roleId);
        roleMapper.deleteByExample(roleExample);
    }

    @Override
    public List<Role> getAssigndRole(Integer adminId) {
        return roleMapper.selectAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnAssigndRole(Integer adminId) {
        return roleMapper.selectUnAssignedRole(adminId);
    }
}
