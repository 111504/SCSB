package com.scsb.t.repository;

import com.scsb.t.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<EmployeeEntity, String> {


}