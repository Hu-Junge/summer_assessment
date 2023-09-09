package com.hu.choutuan.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hu.choutuan.common.R;
import com.hu.choutuan.entity.Employee;
import com.hu.choutuan.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 员工管理
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){

        //HttpServletRequest对象代表客户端的请求

        String password = employee.getPassword();//获取登录密码

        //根据用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();//Employee对接数据库中的表
        queryWrapper.eq(Employee::getUsername, employee.getUsername());//构造了查询条件，相当于getUsername==employee.getUsername()
        Employee emp = employeeService.getOne(queryWrapper);//找到符合查询条件的一条记录

        //用户名查询失败返回
        if(emp == null){
            return R.error("用户名错误");
        }

        //根据密码查询
        if( !emp.getPassword().equals(password) ){
            return R.error("密码错误");
        }

        //登录成功
        request.getSession().setAttribute("employee", emp.getId());//相当于存储登录ID
        log.info("登录成功");
        return R.success(emp);
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");//清除登录ID
        log.info("已退出账户");
        return R.success("已退出账户");
    }

    /**
     * 增加员工
     * @param employee
     * @return
     */
    @PostMapping("/add")
    public R<String> save(@RequestBody Employee employee){

        employee.setPassword("1234");//设置初始密码

        employeeService.save(employee);
        log.info("创建成功");
        return R.success("添加成功");
    }

    /**
     * 分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){

        Page pageInfo = new Page(page, pageSize);//首先执行分页构造

        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();//接着执行条件构造

        if ( name != null )//查询条件
            queryWrapper.like(Employee::getName,name);//查询getName是否等于name

        employeeService.page(pageInfo, queryWrapper);//执行分页

        log.info("分页查询功能正常");
        return R.success(pageInfo);
    }

    /**
     * 根据id获取对应员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){ //@PathVariable的作用是映射 URL 绑定的占位符
        Employee employee = employeeService.getById(id);//根据id获取记录
        log.info("查询成功");
        return R.success(employee);
    }

    /**
     * 根据id删除员工
     * @param id
     * @return
     */
    @GetMapping("/delete")
    public R<String> delete(int id){
        employeeService.removeById(id);//根据id删除记录
        log.info("删除成功");
        return R.success("删除成功");
    }
}
