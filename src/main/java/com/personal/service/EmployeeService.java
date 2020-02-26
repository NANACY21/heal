package com.personal.service;

import com.personal.pojo.Employee;

import java.util.List;

/**
 * @author 李箎
 */
public interface EmployeeService {
    /**
     * 通过邮箱查找员工
     * @param email
     * @return
     */
    Employee getEmployeeByEmail(String email);
}
