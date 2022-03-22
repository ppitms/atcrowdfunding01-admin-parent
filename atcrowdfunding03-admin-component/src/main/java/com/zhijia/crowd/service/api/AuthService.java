package com.zhijia.crowd.service.api;

import com.zhijia.crowd.entity.Auth;

import java.util.List;
import java.util.Map;

/**
 * @author zhijia
 * @create 2022-03-21 10:04
 */
public interface AuthService {
    List<Auth> getAll();

    List<Integer> getAssignedAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelathinship(Map<String, List<Integer>> map);
}
