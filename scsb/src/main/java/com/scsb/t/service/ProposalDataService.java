package com.scsb.t.service;


import com.scsb.t.entity.ProposalDataEntity;

import java.util.List;


public interface ProposalDataService {
    List<ProposalDataEntity> findByEmpId(String EmpId);

    List<ProposalDataEntity> findByCurrentApprover(String CurrentApprover);

    ProposalDataEntity saveFormItem(ProposalDataEntity proposalDataEntity);

    ProposalDataEntity findByformCaseId(Integer formCaseId);

    void approverUpdate(Integer formCaseId, Integer status, String currentApprover);

    String findFormIoData(int formCaseId);
    boolean putMessageIntoDB(String content, String empCaseId);

    List<ProposalDataEntity> findTemplatesByFormTemplateId(Integer formTemplateId);
}