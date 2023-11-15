package com.scsb.t.service;

import com.scsb.t.dao.EmployeeDAO;
import com.scsb.t.entity.EmployeeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;
    @Override
    public EmployeeEntity findByEmpId(String empId){

        return employeeDAO.findByEmpId(empId);
    }
}
