package com.hu.choutuan.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hu.choutuan.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
