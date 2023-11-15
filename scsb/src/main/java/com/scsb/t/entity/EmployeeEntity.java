package com.scsb.t.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employee", schema = "scsb_e-sign", catalog = "")
public class EmployeeEntity {

    @Id
    @Basic
    @Column(name = "emp_id")
    private String empId;
    @Basic
    @Column(name = "password")
    private String password;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "authority")
    private String authority;
    @Basic
    @Column(name = "department")
    private String department;
    @Basic
    @Column(name = "rank")
    private String rank;
    @Basic
    @Column(name = "superior")
    private String superior;

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
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

    public String getSuperior() {
        return superior;
    }

    public void setSuperior(String superior) {
        this.superior = superior;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeEntity that = (EmployeeEntity) o;
        return Objects.equals(empId, that.empId) && Objects.equals(password, that.password) && Objects.equals(name, that.name) && Objects.equals(authority, that.authority) && Objects.equals(department, that.department) && Objects.equals(rank, that.rank) && Objects.equals(superior, that.superior);
    }

    @Override
    public int hashCode() {
        return Objects.hash(empId, password, name, authority, department, rank, superior);
    }

    @Override
    public String toString() {
        return "EmployeeEntity{" +
                "empId='" + empId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", authority='" + authority + '\'' +
                ", department='" + department + '\'' +
                ", rank='" + rank + '\'' +
                ", superior='" + superior + '\'' +
                '}';
    }


}
