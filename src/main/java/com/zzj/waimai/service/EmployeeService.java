package com.zzj.waimai.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzj.waimai.pojo.Employee;

public interface EmployeeService extends IService<Employee> {
}
//用途：业务service层，给controller层的类提供接口进行调用。
// 一般就是自己写的方法封装起来，就是声明一下，具体实现在serviceImpl中。