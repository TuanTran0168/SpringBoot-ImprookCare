/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "service_payment_status")
@NamedQueries({
    @NamedQuery(name = "ServicePaymentStatus.findAll", query = "SELECT s FROM ServicePaymentStatus s"),
    @NamedQuery(name = "ServicePaymentStatus.findByServicePaymentStatusId", query = "SELECT s FROM ServicePaymentStatus s WHERE s.servicePaymentStatusId = :servicePaymentStatusId"),
    @NamedQuery(name = "ServicePaymentStatus.findByServicePaymentStatusValue", query = "SELECT s FROM ServicePaymentStatus s WHERE s.servicePaymentStatusValue = :servicePaymentStatusValue"),
    @NamedQuery(name = "ServicePaymentStatus.findByCreatedDate", query = "SELECT s FROM ServicePaymentStatus s WHERE s.createdDate = :createdDate"),
    @NamedQuery(name = "ServicePaymentStatus.findByUpdatedDate", query = "SELECT s FROM ServicePaymentStatus s WHERE s.updatedDate = :updatedDate"),
    @NamedQuery(name = "ServicePaymentStatus.findByDeletedDate", query = "SELECT s FROM ServicePaymentStatus s WHERE s.deletedDate = :deletedDate"),
    @NamedQuery(name = "ServicePaymentStatus.findByActive", query = "SELECT s FROM ServicePaymentStatus s WHERE s.active = :active")})
public class ServicePaymentStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "service_payment_status_id")
    private Integer servicePaymentStatusId;
    @Column(name = "service_payment_status_value")
    private String servicePaymentStatusValue;
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
    @OneToMany(mappedBy = "servicePaymentStatusId")
    private Set<Prescriptions> prescriptionsSet;

    public ServicePaymentStatus() {
    }

    public ServicePaymentStatus(Integer servicePaymentStatusId) {
        this.servicePaymentStatusId = servicePaymentStatusId;
    }

    public Integer getServicePaymentStatusId() {
        return servicePaymentStatusId;
    }

    public void setServicePaymentStatusId(Integer servicePaymentStatusId) {
        this.servicePaymentStatusId = servicePaymentStatusId;
    }

    public String getServicePaymentStatusValue() {
        return servicePaymentStatusValue;
    }

    public void setServicePaymentStatusValue(String servicePaymentStatusValue) {
        this.servicePaymentStatusValue = servicePaymentStatusValue;
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

    public Set<Prescriptions> getPrescriptionsSet() {
        return prescriptionsSet;
    }

    public void setPrescriptionsSet(Set<Prescriptions> prescriptionsSet) {
        this.prescriptionsSet = prescriptionsSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (servicePaymentStatusId != null ? servicePaymentStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ServicePaymentStatus)) {
            return false;
        }
        ServicePaymentStatus other = (ServicePaymentStatus) object;
        if ((this.servicePaymentStatusId == null && other.servicePaymentStatusId != null) || (this.servicePaymentStatusId != null && !this.servicePaymentStatusId.equals(other.servicePaymentStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.ServicePaymentStatus[ servicePaymentStatusId=" + servicePaymentStatusId + " ]";
    }

}
