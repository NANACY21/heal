package com.personal.service.impl;

import com.personal.mapper.EmployeeMapper;
import com.personal.mapper.UsersMapper;
import com.personal.pojo.Employee;
import com.personal.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 李箎
 */
@SuppressWarnings("ALL")
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper mapper;

    /**
     * 通过邮箱查找员工
     *
     * @param email
     * @return
     */
    @Override
    public Employee getEmployeeByEmail(String email) {
        Employee employee = null;
        employee = mapper.selectByEmail(email);
        System.out.println("MySQL查到的员工");
        System.out.println(employee);
        return employee;
    }
}
