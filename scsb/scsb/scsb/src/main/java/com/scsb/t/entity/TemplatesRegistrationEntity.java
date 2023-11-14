package com.scsb.t.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


@Entity
@Data
@Table(name = "templates_registration")
public class TemplatesRegistrationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "template_name")
    private String templateName;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "status")
    private String status;

    @Column(name = "sign_type")
    private String signType;

}
