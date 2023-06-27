package com.it.regwm.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.it.regwm.common.R;
import com.it.regwm.entity.Employee;
import com.it.regwm.service.EmpolyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.LambdaConversionException;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmpolyeeController {
    @Autowired
    private EmpolyService empolyService;

    /*
    * 员工登录
    * */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        /*
        * 密码md5加密
        * */
        String password=employee.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        /*
        * 将页面提交的用户名查询数据库
        * */
        LambdaQueryWrapper<Employee> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp =empolyService.getOne(queryWrapper);
        if (emp == null){
            return R.error("登录失败");
        }
        if (!emp.getPassword().equals(password)){
            return R.error("密码错误");
        }
        if (emp.getStatus()==0){
            return R.error("账号已经禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }
    @PostMapping("/logout")
    public R<String>  logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("账号已退出");

    }
}
