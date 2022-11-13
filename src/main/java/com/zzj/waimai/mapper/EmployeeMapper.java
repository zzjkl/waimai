package com.zzj.waimai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzj.waimai.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
//对数据库进行数据持久化操作，他的方法语句是直接针对数据库操作的，
// 主要实现一些增删改查操作，在mybatis中方法主要与与xxx.xml内相互一一映射。