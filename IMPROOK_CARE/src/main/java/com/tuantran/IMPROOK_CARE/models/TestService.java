/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "test_service")
@NamedQueries({
        @NamedQuery(name = "TestService.findAll", query = "SELECT t FROM TestService t"),
        @NamedQuery(name = "TestService.findByTestServiceId", query = "SELECT t FROM TestService t WHERE t.testServiceId = :testServiceId"),
        @NamedQuery(name = "TestService.findByTestServiceName", query = "SELECT t FROM TestService t WHERE t.testServiceName = :testServiceName"),
        @NamedQuery(name = "TestService.findByCreatedDate", query = "SELECT t FROM TestService t WHERE t.createdDate = :createdDate"),
        @NamedQuery(name = "TestService.findByUpdatedDate", query = "SELECT t FROM TestService t WHERE t.updatedDate = :updatedDate"),
        @NamedQuery(name = "TestService.findByDeletedDate", query = "SELECT t FROM TestService t WHERE t.deletedDate = :deletedDate"),
        @NamedQuery(name = "TestService.findByActive", query = "SELECT t FROM TestService t WHERE t.active = :active") })
public class TestService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "test_service_id")
    private Integer testServiceId;
    @Column(name = "test_service_name")
    private String testServiceName;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "deleted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;
    @Column(name = "active")
    private Boolean active;
    @JsonIgnore
    @OneToMany(mappedBy = "testServiceId")
    private Set<TestResult> testResultSet;

    public TestService() {
    }

    public TestService(Integer testServiceId) {
        this.testServiceId = testServiceId;
    }

    public Integer getTestServiceId() {
        return testServiceId;
    }

    public void setTestServiceId(Integer testServiceId) {
        this.testServiceId = testServiceId;
    }

    public String getTestServiceName() {
        return testServiceName;
    }

    public void setTestServiceName(String testServiceName) {
        this.testServiceName = testServiceName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Date getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Date deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<TestResult> getTestResultSet() {
        return testResultSet;
    }

    public void setTestResultSet(Set<TestResult> testResultSet) {
        this.testResultSet = testResultSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (testServiceId != null ? testServiceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestService)) {
            return false;
        }
        TestService other = (TestService) object;
        if ((this.testServiceId == null && other.testServiceId != null)
                || (this.testServiceId != null && !this.testServiceId.equals(other.testServiceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.TestService[ testServiceId=" + testServiceId + " ]";
    }

}
