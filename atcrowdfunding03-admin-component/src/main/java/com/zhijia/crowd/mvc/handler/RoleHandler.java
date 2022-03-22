package com.zhijia.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.zhijia.crowd.entity.ResultEntity;
import com.zhijia.crowd.entity.Role;
import com.zhijia.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author zhijia
 * @create 2022-03-18 12:18
 */
@Controller
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    @ResponseBody
    @RequestMapping("/role/remove/by/role/id/array.json")
    public ResultEntity<PageInfo<Role>> removeByRoleIdArray(@RequestBody List<Integer> roleIdrole){
        roleService.removeRole(roleIdrole);
        return ResultEntity.successWithoutData();
    }
    @ResponseBody
    @RequestMapping("/role/update.json")
    public ResultEntity<PageInfo<Role>> updateRole(Role role){
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/role/save.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(Role role){
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }

    @ResponseBody
    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                                    @RequestParam(value = "keyword",defaultValue = "") String keyword){
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);

        return ResultEntity.successWithData(pageInfo);
    }

}
