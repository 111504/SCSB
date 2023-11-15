package com.scsb.t.service;

import com.scsb.t.entity.EmployeeEntity;

public interface EmployeeService {
    EmployeeEntity findByEmpId(String empId);
}
