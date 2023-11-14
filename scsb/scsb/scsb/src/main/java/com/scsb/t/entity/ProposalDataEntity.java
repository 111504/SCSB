package com.scsb.t.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "proposal_data", schema = "scsb_e-sign", catalog = "")
public class ProposalDataEntity {
    @Basic
    @Column(name = "emp_id")
    private String empId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "department")
    private String department;
    @Basic
    @Column(name = "rank")
    private String rank;
    @Basic
    @Column(name = "project_title")
    private String projectTitle;
    @Basic
    @Column(name = "proposal_topic")
    private String proposalTopic;
    @Basic
    @Column(name = "issue_date")
    private String issueDate;

    @Basic
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "form_case_id")
    private Integer formCaseId;
    @Basic
    @Column(name = "priority_level")
    private String priorityLevel;
    @Basic
    @Column(name = "security_level")
    private String securityLevel;
    @Basic
    @Column(name = "status")
    private Integer status;
    @Basic
    @Column(name = "current_approver")
    private String currentApprover;
    @Basic
    @Column(name = "arrival_date")
    private String arrivalDate;
    @Basic

    @Column(name = "form_template_id")
    private Integer formTemplateId;
    @Column(name = "data")
    private String data;

    public String getMessage() {
        return message;
    }

    public String getSignatureJson() {
        return signatureJson;
    }

    public void setSignatureJson(String signatureJson) {
        this.signatureJson = signatureJson;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name="message")
    private  String message;
    public String getData() {
        return data;
    }

    @Column(name="signature_json")
    private  String signatureJson;


    public void setData(String data) {
        this.data = data;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }

    public String getProposalTopic() {
        return proposalTopic;
    }

    public void setProposalTopic(String proposalTopic) {
        this.proposalTopic = proposalTopic;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String  issueDate) {
        this.issueDate = issueDate;
    }

    public Integer getFormCaseId() {
        return formCaseId;
    }

    public void setFormCaseId(Integer formCaseId) {
        this.formCaseId = formCaseId;
    }

    public String getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(String priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCurrentApprover() {
        return currentApprover;
    }

    public void setCurrentApprover(String currentApprover) {
        this.currentApprover = currentApprover;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Integer getFormTemplateId() {
        return formTemplateId;
    }

    public void setFormTemplateId(Integer formTemplateId) {
        this.formTemplateId = formTemplateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProposalDataEntity that = (ProposalDataEntity) o;
        return Objects.equals(empId, that.empId) && Objects.equals(name, that.name) && Objects.equals(department, that.department) && Objects.equals(rank, that.rank) && Objects.equals(projectTitle, that.projectTitle) && Objects.equals(proposalTopic, that.proposalTopic) && Objects.equals(issueDate, that.issueDate) && Objects.equals(formCaseId, that.formCaseId) && Objects.equals(priorityLevel, that.priorityLevel) && Objects.equals(securityLevel, that.securityLevel) && Objects.equals(status, that.status) && Objects.equals(currentApprover, that.currentApprover) && Objects.equals(arrivalDate, that.arrivalDate) && Objects.equals(formTemplateId, that.formTemplateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, name, department, rank, projectTitle, proposalTopic, issueDate, formCaseId, priorityLevel, securityLevel, status, currentApprover, arrivalDate, formTemplateId);
    }
}

