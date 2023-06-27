package com.it.regwm.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.regwm.common.R;
import com.it.regwm.entity.Employee;
import com.it.regwm.service.EmpolyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.LambdaConversionException;
import java.time.LocalDateTime;

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

    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());
        //employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());

        //Long empId = (Long) request.getSession().getAttribute("employee");
       // employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);
        empolyService.save(employee);

        return R.success("新增员工成功");
    }
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        Page pageInfo =new Page(page,pageSize);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        empolyService.page(pageInfo,lambdaQueryWrapper);
        return R.success(pageInfo);
    }
    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
       // employee.setUpdateUser(empId);
        empolyService.updateById(employee);
        return R.success("员工信息更新成功");
    }
    @GetMapping("/{id}")
    public R<Employee> add(@PathVariable long id){
        log.info("要修改的id",id);
        Employee employee = empolyService.getById(id);
        if (employee!=null){
            return R.success(employee);
        }

        return R.error("没有查询到此用户信息");
    }
}
