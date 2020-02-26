package com.personal.mapper;

import com.personal.pojo.Employee;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    int insertSelective(Employee record);

    Employee selectByPrimaryKey(Long id);
    Employee selectByEmail(String email);

    int updateByPrimaryKeySelective(Employee record);

    int updateByPrimaryKey(Employee record);
}