package com.zzj.waimai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzj.waimai.pojo.Employee;
import com.zzj.waimai.mapper.EmployeeMapper;
import com.zzj.waimai.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee>implements EmployeeService{
}
