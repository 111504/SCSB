package com.scsb.t.dao;


import com.scsb.t.entity.ProposalDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProposalDataDAO extends JpaRepository<ProposalDataEntity, Long> {
    @Query(value = "select * from proposal_data where emp_id = ?1", nativeQuery = true)
    List<ProposalDataEntity> findByEmpId(String emp_id);

    @Query(value = "select * from proposal_data where current_approver = ?1", nativeQuery = true)
    List<ProposalDataEntity> findByCurrentApprover(String current_approver);

    @Query(value = "select * from proposal_data where form_case_id = ?1", nativeQuery = true)
    ProposalDataEntity findByFormCaseId(Integer formCaseId);

    @Modifying
    @Transactional
    @Query(value = "update proposal_data p set p.status = ?2, p.current_approver = ?3 where p.form_case_id = ?1", nativeQuery = true)
    void approverUpdate(Integer formCaseId, Integer status, String currentApprover);

    @Query(value = "select data from proposal_data where form_case_id=?1",nativeQuery = true)
    String findFormIoData(int formCaseId);

    @Modifying
    @org.springframework.transaction.annotation.Transactional
    @Query(value = "update  proposal_data set message =?1 where form_case_id=?2",nativeQuery = true)
    int putMessageIntoDB(String content, String formCaseId);

    @Query(value = "select * from proposal_data where form_template_id = ?1", nativeQuery = true)
    List<ProposalDataEntity> findTemplatesByFormTemplateId(int formTemplateId);
}

