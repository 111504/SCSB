package com.scsb.t.jsonobj;

import lombok.Data;

@Data
public class FormInfo {
    public String id;
    public String name;
    public String department_rank;
    public String proposal_topic;
    public String arrival_date;
    public String timeliness_level;
    public String form_id;
    public String form_case_number;

    public FormInfo(String id, String name, String department_rank, String proposal_topic,
                    String arrival_date, String timeliness_level, String form_id, String form_case_number) {
        this.id = id;
        this.name = name;
        this.department_rank = department_rank;
        this.proposal_topic = proposal_topic;
        this.arrival_date = arrival_date;
        this.timeliness_level = timeliness_level;
        this.form_id = form_id;
        this.form_case_number = form_case_number;
    }

    public FormInfo() {
    }
}