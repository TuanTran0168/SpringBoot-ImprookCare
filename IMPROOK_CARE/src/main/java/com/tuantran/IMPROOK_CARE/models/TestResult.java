/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.models;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "test_result")
@NamedQueries({
        @NamedQuery(name = "TestResult.findAll", query = "SELECT t FROM TestResult t"),
        @NamedQuery(name = "TestResult.findByTestResultId", query = "SELECT t FROM TestResult t WHERE t.testResultId = :testResultId"),
        @NamedQuery(name = "TestResult.findByTestResultValue", query = "SELECT t FROM TestResult t WHERE t.testResultValue = :testResultValue"),
        @NamedQuery(name = "TestResult.findByTestResultDiagnosis", query = "SELECT t FROM TestResult t WHERE t.testResultDiagnosis = :testResultDiagnosis"),
        @NamedQuery(name = "TestResult.findByTestResultImage", query = "SELECT t FROM TestResult t WHERE t.testResultImage = :testResultImage"),
        @NamedQuery(name = "TestResult.findByCreatedDate", query = "SELECT t FROM TestResult t WHERE t.createdDate = :createdDate"),
        @NamedQuery(name = "TestResult.findByUpdatedDate", query = "SELECT t FROM TestResult t WHERE t.updatedDate = :updatedDate"),
        @NamedQuery(name = "TestResult.findByDeletedDate", query = "SELECT t FROM TestResult t WHERE t.deletedDate = :deletedDate"),
        @NamedQuery(name = "TestResult.findByActive", query = "SELECT t FROM TestResult t WHERE t.active = :active") })
public class TestResult implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "test_result_id")
    private Integer testResultId;
    @Column(name = "test_result_value")
    private String testResultValue;
    @Column(name = "test_result_diagnosis")
    private String testResultDiagnosis;
    @Column(name = "test_result_image")
    private String testResultImage;
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
    @JoinColumn(name = "booking_id", referencedColumnName = "booking_id")
    @ManyToOne
    private Booking bookingId;
    @JoinColumn(name = "test_service_id", referencedColumnName = "test_service_id")
    @ManyToOne
    private TestService testServiceId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;

    public TestResult() {
    }

    public TestResult(Integer testResultId) {
        this.testResultId = testResultId;
    }

    public Integer getTestResultId() {
        return testResultId;
    }

    public void setTestResultId(Integer testResultId) {
        this.testResultId = testResultId;
    }

    public String getTestResultValue() {
        return testResultValue;
    }

    public void setTestResultValue(String testResultValue) {
        this.testResultValue = testResultValue;
    }

    public String getTestResultDiagnosis() {
        return testResultDiagnosis;
    }

    public void setTestResultDiagnosis(String testResultDiagnosis) {
        this.testResultDiagnosis = testResultDiagnosis;
    }

    public String getTestResultImage() {
        return testResultImage;
    }

    public void setTestResultImage(String testResultImage) {
        this.testResultImage = testResultImage;
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

    public Booking getBookingId() {
        return bookingId;
    }

    public void setBookingId(Booking bookingId) {
        this.bookingId = bookingId;
    }

    public TestService getTestServiceId() {
        return testServiceId;
    }

    public void setTestServiceId(TestService testServiceId) {
        this.testServiceId = testServiceId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (testResultId != null ? testResultId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TestResult)) {
            return false;
        }
        TestResult other = (TestResult) object;
        if ((this.testResultId == null && other.testResultId != null)
                || (this.testResultId != null && !this.testResultId.equals(other.testResultId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.TestResult[ testResultId=" + testResultId + " ]";
    }

}
