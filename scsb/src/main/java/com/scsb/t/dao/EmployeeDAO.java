package com.scsb.t.dao;

import com.scsb.t.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeDAO  extends JpaRepository<EmployeeEntity, String> {
    @Query(value = "select * from employee where emp_id = ?1", nativeQuery = true)
    EmployeeEntity findByEmpId(String empId);
}
