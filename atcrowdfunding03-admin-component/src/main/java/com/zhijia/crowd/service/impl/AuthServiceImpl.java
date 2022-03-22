package com.zhijia.crowd.service.impl;

import com.zhijia.crowd.entity.Auth;
import com.zhijia.crowd.entity.AuthExample;
import com.zhijia.crowd.mapper.AuthMapper;
import com.zhijia.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author zhijia
 * @create 2022-03-21 10:04
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {

        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelathinship(Map<String, List<Integer>> map) {
        //获取数据
        List<Integer> roleIds = map.get("id");
        Integer roleId = roleIds.get(0);
        List<Integer> authIdArray = map.get("authIdArray");
        //删除旧数据再添加新数据
        authMapper.deleteOldRelationship(roleId);
        List<Integer> authIdList = map.get("authIdArray");
        if(authIdArray!=null&&authIdArray.size()>0){
            authMapper.insertNewRelationship(roleId,authIdArray);
        }
    }
}
