package com.zzj.waimai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzj.waimai.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
