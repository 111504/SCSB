package com.scsb.t.dao;

import com.scsb.t.entity.TemplatesRegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TemplatesRegistrationRepository extends JpaRepository<TemplatesRegistrationEntity, Integer> {
    @Query(value = "select * from templates_registration where id = ?1", nativeQuery = true)
    Optional<TemplatesRegistrationEntity> findById(Integer id);
}
