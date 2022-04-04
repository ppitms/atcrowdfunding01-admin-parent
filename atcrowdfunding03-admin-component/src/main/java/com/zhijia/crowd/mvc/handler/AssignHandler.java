package com.zhijia.crowd.mvc.handler;

import com.zhijia.crowd.entity.Auth;
import com.zhijia.crowd.util.ResultEntity;
import com.zhijia.crowd.entity.Role;
import com.zhijia.crowd.service.api.AdminService;
import com.zhijia.crowd.service.api.AuthService;
import com.zhijia.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author zhijia
 * @create 2022-03-20 17:19
 */
@Controller
public class AssignHandler {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    //设置角色对应权限
    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelathinship(@RequestBody Map<String,List<Integer>> map){
        authService.saveRoleAuthRelathinship(map);
        return ResultEntity.successWithoutData();
    }

    //选中的节点数据（角色对应的权限）
    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(Integer roleId){
        List<Integer> authList=authService.getAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(authList);
    }

    //1、发送ajax请求查询auth数据
    @ResponseBody
    @RequestMapping("/assgin/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth(){
        List<Auth> authList=authService.getAll();
        return ResultEntity.successWithData(authList);
    }

    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(@RequestParam("adminId")Integer adminId,
                                            @RequestParam("pageNum")Integer pageNum,
                                            @RequestParam("keyword")String keyword,
                                            @RequestParam(value = "roleIdList",required = false)List<Integer> roleIdList){
        adminService.saveAdminRoleRelationship(adminId,roleIdList);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId, ModelMap modelMap){
        // 查询已分配角色
        List<Role> assignedRoleList= roleService.getAssigndRole(adminId);
        // 查询未分配角色
        List<Role> unAssignedRoleList= roleService.getUnAssigndRole(adminId);
        //存入模型
        modelMap.addAttribute("assignedRoleList",assignedRoleList);
        modelMap.addAttribute("unAssignedRoleList",unAssignedRoleList);
        for (Role role : assignedRoleList) {
            System.out.println(role);
        }
        return "assign-role";
    }
}
