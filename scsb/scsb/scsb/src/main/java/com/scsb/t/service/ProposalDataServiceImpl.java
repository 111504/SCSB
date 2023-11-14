package com.scsb.t.service;

import com.scsb.t.dao.ProposalDataDAO;
import com.scsb.t.entity.ProposalDataEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalDataServiceImpl implements ProposalDataService{


    private ProposalDataDAO proposalDataDAO;

    public ProposalDataServiceImpl(ProposalDataDAO proposalDataDAO) {
        this.proposalDataDAO = proposalDataDAO;
    }

    @Override
    public List<ProposalDataEntity> findByEmpId(String empId) {

        return proposalDataDAO.findByEmpId(empId);
    }

    @Override
    public List<ProposalDataEntity> findByCurrentApprover(String currentApprover) {
        return proposalDataDAO.findByCurrentApprover(currentApprover);
    }

    @Override
    public ProposalDataEntity saveFormItem(ProposalDataEntity proposalDataEntity) {
        return proposalDataDAO.save(proposalDataEntity);
    }

    @Override
    public ProposalDataEntity findByformCaseId(Integer formCaseId){

        ProposalDataEntity proposalData=proposalDataDAO.findByFormCaseId(formCaseId);
        if (proposalData == null) {
            System.out.println("No data found for the given formCaseId.");
            return null;
        } else {
            System.out.println("Data found: " + proposalData);
            return  proposalData;
        }


    }

    @Override
    public void approverUpdate(Integer formCaseId, Integer status, String currentApprover){
        proposalDataDAO.approverUpdate(formCaseId, status, currentApprover);
    }

    @Override
    public String findFormIoData(int formCaseId) {
        return proposalDataDAO.findFormIoData(formCaseId);
    }

    @Override
    public boolean putMessageIntoDB(String content, String empCaseId) {
        int affectRow=proposalDataDAO.putMessageIntoDB(content,empCaseId);
        //如果sql回傳大於1代表更改成功
        return affectRow>0;
    }

    @Override
    public List<ProposalDataEntity> findTemplatesByFormTemplateId(Integer formTemplateId) {
        return proposalDataDAO.findTemplatesByFormTemplateId(formTemplateId);
    }
}
