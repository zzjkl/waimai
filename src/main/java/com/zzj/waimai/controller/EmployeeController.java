package com.zzj.waimai.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zzj.waimai.common.R;
import com.zzj.waimai.pojo.Employee;
import com.zzj.waimai.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request,@RequestBody Employee employee){
//        4)在Controller中创建登录方法
//        处理逻辑如下：
//        1、将页面提交的密码password进行md5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
//        2、根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);//数据库中名字唯一
//        3、如果没有查询到则返回登录失败结果
        if(emp == null){
            return R.error("账户不存在");
        }
//        4、密码比对，如果不一致则返回登录失败结果
        if(!emp.getPassword().equals(password)){
            return R.error("账户密码错误");
        }
//        5、查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if(emp.getStatus() ==0){
            return R.error("账号已禁用");
        }
//        6、登录成功，将员工id存入Session:并返回登录成功结果
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }
    @PostMapping("/logout")
    public R<String> login(HttpServletRequest request) {
        //尝试删除
        try {
            request.getSession().removeAttribute("employee");
        }catch (Exception e){
            //删除失败
            return R.error("登出失败");
        }
        return R.success("登出成功");
    }
    @PostMapping
    public R<String> addEmployee(HttpServletRequest httpServletRequest,@RequestBody Employee employee) {
        //设置默认密码，顺手加密了
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //设置修改时间
       // employee.setCreateTime(LocalDateTime.now());
       // employee.setUpdateTime(LocalDateTime.now());
        //账户默认状态0
        employee.setStatus(0);
        //获取当前新增操作人员的id
      //  Long empId= (Long) httpServletRequest.getSession().getAttribute("employee");
       // employee.setCreateUser(empId);
       // employee.setUpdateUser(empId);
        //MP自动CRUD的功能，封装好了save方法
        employeeService.save(employee);
        return R.success("新增员工成功");
    }
    /**
     * 分页展示员工列表接口、查询某个员工
     * @param page 查询第几页
     * @param pageSize 每页一共几条数据
     * @param name 查询名字=name的数据
     * @return 返回Page页
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        //分页构造器,Page(第几页, 查几条)
        Page pageInfo = new Page(page, pageSize);
        //查询构造器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        //过滤条件.like(什么条件下启用模糊查询，模糊查询字段，被模糊插叙的名称)
        lambdaQueryWrapper.like(!StringUtils.isEmpty(name), Employee::getName, name);
        //添加排序
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        //查询分页、自动更新
        employeeService.page(pageInfo, lambdaQueryWrapper);
        //返回查询结果
        return R.success(pageInfo);
    }
    /**
     * 更新员工状态，是PUT请求
     * @param httpServletRequest
     * @param employee
     * @return
     */
    @PutMapping
    public R<Employee> update(HttpServletRequest httpServletRequest,@RequestBody Employee employee){
        System.out.println("更新"+Thread.currentThread().getName());
        //从Request作用域中拿到员工ID
      //  Long empId = (Long) httpServletRequest.getSession().getAttribute("employee");
        //拿新的状态值
    //    employee.setStatus(employee.getStatus());
        //更新时间
      //  employee.setUpdateTime(LocalDateTime.now());
        //更新处理人id
       // employee.setUpdateUser(empId);
        employeeService.updateById(employee);
        return R.success(employee);
    }


    /**
     * 拿到员工资料，前端自动填充列表，更新的时候复用上面的update方法
     * @param id ResultFul风格传入参数，用@PathVariable来接收同名参数
     * @return 返回员工对象
     */
    @GetMapping("/{id}")
    public R<Employee> getEmployee(@PathVariable Long id){
        Employee employee = employeeService.getById(id);
        if (employee!=null){
            return R.success(employee);
        }
        return R.error("没有查到员工信息");
    }

}
