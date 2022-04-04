package com.zhijia.crowd.mvc.handler;

import com.zhijia.crowd.entity.Admin;
import com.zhijia.crowd.util.ResultEntity;
import com.zhijia.crowd.entity.Student;
import com.zhijia.crowd.service.api.AdminService;
import com.zhijia.crowd.util.CrowdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhijia
 * @create 2022-03-14 18:43
 */
@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(TestHandler.class);

    @RequestMapping("/test/ssm.html")
    public String testSsm(ModelMap modelMap, HttpServletRequest request){
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        logger.info("judgeResult="+judgeResult);

        List<Admin> adminList=adminService.getAll();
        modelMap.addAttribute("adminList",adminList);
        System.out.println(10/0);
        return "target";
    }

    @ResponseBody
    @RequestMapping("/send/array/one.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array){
        for (Integer number : array) {
            System.out.println("number:"+number);
        }
        return "success";
    }
    @ResponseBody
    @RequestMapping("/send/array/two.html")
    public String testReceiveArrayTwo(@RequestParam("array") List<Integer> array){//参数弄个实体类接收，这里没写
        for (Integer number : array) {
            System.out.println("number:"+number);
        }
        return "success";
    }
    @ResponseBody
    @RequestMapping("/send/array/three.html")
    public String testReceiveArrayThree(@RequestBody List<Integer> array){//参数弄个实体类接收，这里没写

        for (Integer number : array) {
            logger.info("number="+number);
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/compose/object.json")
    public ResultEntity<Student> testReceiveComposeObject(@RequestBody Student student, HttpServletRequest request){
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        logger.info("judgeResult="+judgeResult);
        logger.info(student.toString());
        return ResultEntity.successWithData(student);
    }

}
