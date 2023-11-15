package com.scsb.t.dto;

import lombok.Data;

@Data
public class ProposalDataDTO {
    private String emp_id;
    //    private String name;
//    private String department;
//    private String rank;
    private String project_title;
    private String propolsal_topic;
    //    private String issue_date;
//    private String form_case_id;
    private String priority_level;
    private String security_level;
    private String status;
    //private String current_approver;
    // private String arrive_date;
    private String form_template_id;
    private String data;
    // Getters, setters, and default constructor


    public ProposalDataDTO(String emp_id, String project_title, String propolsal_topic, String priority_level, String security_level, String status, String form_template_id, String data) {
        this.emp_id = emp_id;
        this.project_title = project_title;
        this.propolsal_topic = propolsal_topic;
        this.priority_level = priority_level;
        this.security_level = security_level;
        this.status = status;
        this.form_template_id = form_template_id;
        this.data = data;
    }

    public ProposalDataDTO(String emp_id, String project_title, String propolsal_topic, String priority_level, String security_level, String status, String form_template_id) {
        this.emp_id = emp_id;
        this.project_title = project_title;
        this.propolsal_topic = propolsal_topic;
        this.priority_level = priority_level;
        this.security_level = security_level;
        this.status = status;

        this.form_template_id = form_template_id;
    }

    public ProposalDataDTO() {
    }

    @Override
    public String toString() {
        return "ProposalDataDTO{" +
                "emp_id='" + emp_id + '\'' +
                ", project_title='" + project_title + '\'' +
                ", propolsal_topic='" + propolsal_topic + '\'' +
                ", priority_level='" + priority_level + '\'' +
                ", security_level='" + security_level + '\'' +
                ", status='" + status + '\'' +
                ", form_template_id='" + form_template_id + '\'' +
                ", data=" + data +
                '}';
    }
}
