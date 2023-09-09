package com.hu.choutuan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hu.choutuan.entity.Employee;
import com.hu.choutuan.mapper.EmployeeMapper;
import com.hu.choutuan.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceimpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
