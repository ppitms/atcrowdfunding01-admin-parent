package com.zhijia.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.zhijia.crowd.constart.CrowdConstant;
import com.zhijia.crowd.entity.Admin;
import com.zhijia.crowd.service.api.AdminService;
import com.zhijia.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhijia
 * @create 2022-03-15 22:12
 */
@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    //点击修改
    @RequestMapping("/admin/unpdate.html")
    public String update(Admin admin,@RequestParam("pageNum") Integer pageNum,
                         @RequestParam("keyword") String keyword){
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    //点击修改页面
    @RequestMapping("/admin/to/adit/page.html")
    public String toEditPage(@RequestParam("adminId") Integer adminId,
                             ModelMap modelMap){
        Admin admin=adminService.getAdminById(adminId);

        modelMap.addAttribute("admin",admin);

        return "admin-edit";
    }

    //新增
    @RequestMapping("/admin/save.html")
    public String save(Admin admin){

        //保存
        adminService.saveAdmin(admin);
        Integer pageNum=Integer.MAX_VALUE;
        return "redirect:/admin/get/page.html?pageNum="+pageNum;
    }

    //删除
    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(@PathVariable("adminId") Integer adminId,
                         @PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyword") String keyword){
        adminService.remove(adminId);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }

    //去数据列表
    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(@RequestParam(value = "keyword",defaultValue = "") String keyword,
                              @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                              @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                              ModelMap modelMap){
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);
        return "admin-page";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session){
        //强制session失效
        session.invalidate();

        return "redirect:/admin/to/login/page.html";
    }

    //登录
    @RequestMapping(value = "/admin/do/login.html")
    public String doLogin(Admin admin, HttpSession session){
        //登录检查
        Admin admin1=adminService.getAdminByLoginAcct(admin.getLoginAcct(),admin.getUserPswd());

        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin1);
        //重定向
        return "redirect:/admin/to/main/page.html";
    }
}
