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
@Table(name = "collab_doctor")
@NamedQueries({
    @NamedQuery(name = "CollabDoctor.findAll", query = "SELECT c FROM CollabDoctor c"),
    @NamedQuery(name = "CollabDoctor.findByCollabId", query = "SELECT c FROM CollabDoctor c WHERE c.collabId = :collabId"),
    @NamedQuery(name = "CollabDoctor.findByName", query = "SELECT c FROM CollabDoctor c WHERE c.name = :name"),
    @NamedQuery(name = "CollabDoctor.findByPhonenumber", query = "SELECT c FROM CollabDoctor c WHERE c.phonenumber = :phonenumber"),
    @NamedQuery(name = "CollabDoctor.findByEmail", query = "SELECT c FROM CollabDoctor c WHERE c.email = :email"),
    @NamedQuery(name = "CollabDoctor.findByCreatedDate", query = "SELECT c FROM CollabDoctor c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "CollabDoctor.findByUpdatedDate", query = "SELECT c FROM CollabDoctor c WHERE c.updatedDate = :updatedDate"),
    @NamedQuery(name = "CollabDoctor.findByDeletedDate", query = "SELECT c FROM CollabDoctor c WHERE c.deletedDate = :deletedDate")})
public class CollabDoctor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "collab_id")
    private Integer collabId;
    @Column(name = "name")
    private String name;
    @Column(name = "phonenumber")
    private String phonenumber;
    @Column(name = "email")
    private String email;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "deleted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    @ManyToOne
    private CollabDoctorStatus statusId;

    public CollabDoctor() {
    }

    public CollabDoctor(Integer collabId) {
        this.collabId = collabId;
    }

    public Integer getCollabId() {
        return collabId;
    }

    public void setCollabId(Integer collabId) {
        this.collabId = collabId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public CollabDoctorStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(CollabDoctorStatus statusId) {
        this.statusId = statusId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (collabId != null ? collabId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CollabDoctor)) {
            return false;
        }
        CollabDoctor other = (CollabDoctor) object;
        if ((this.collabId == null && other.collabId != null) || (this.collabId != null && !this.collabId.equals(other.collabId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.CollabDoctor[ collabId=" + collabId + " ]";
    }
    
}
