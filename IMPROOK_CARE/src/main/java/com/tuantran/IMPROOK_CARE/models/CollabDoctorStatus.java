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
@Table(name = "collab_doctor_status")
@NamedQueries({
    @NamedQuery(name = "CollabDoctorStatus.findAll", query = "SELECT c FROM CollabDoctorStatus c"),
    @NamedQuery(name = "CollabDoctorStatus.findByStatusId", query = "SELECT c FROM CollabDoctorStatus c WHERE c.statusId = :statusId"),
    @NamedQuery(name = "CollabDoctorStatus.findByStatusValue", query = "SELECT c FROM CollabDoctorStatus c WHERE c.statusValue = :statusValue"),
    @NamedQuery(name = "CollabDoctorStatus.findByCreatedDate", query = "SELECT c FROM CollabDoctorStatus c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "CollabDoctorStatus.findByUpdatedDate", query = "SELECT c FROM CollabDoctorStatus c WHERE c.updatedDate = :updatedDate"),
    @NamedQuery(name = "CollabDoctorStatus.findByDeletedDate", query = "SELECT c FROM CollabDoctorStatus c WHERE c.deletedDate = :deletedDate"),
    @NamedQuery(name = "CollabDoctorStatus.findByActive", query = "SELECT c FROM CollabDoctorStatus c WHERE c.active = :active")})
public class CollabDoctorStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "status_id")
    private Integer statusId;
    @Column(name = "status_value")
    private String statusValue;
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
    @OneToMany(mappedBy = "statusId")
    private Set<CollabDoctor> collabDoctorSet;

    public CollabDoctorStatus() {
    }

    public CollabDoctorStatus(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusValue() {
        return statusValue;
    }

    public void setStatusValue(String statusValue) {
        this.statusValue = statusValue;
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

    public Set<CollabDoctor> getCollabDoctorSet() {
        return collabDoctorSet;
    }

    public void setCollabDoctorSet(Set<CollabDoctor> collabDoctorSet) {
        this.collabDoctorSet = collabDoctorSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (statusId != null ? statusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CollabDoctorStatus)) {
            return false;
        }
        CollabDoctorStatus other = (CollabDoctorStatus) object;
        if ((this.statusId == null && other.statusId != null) || (this.statusId != null && !this.statusId.equals(other.statusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.CollabDoctorStatus[ statusId=" + statusId + " ]";
    }
    
}
