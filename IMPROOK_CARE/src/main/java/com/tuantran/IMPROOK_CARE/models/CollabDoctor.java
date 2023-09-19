/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.models;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

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
    @NamedQuery(name = "CollabDoctor.findByEmail", query = "SELECT c FROM CollabDoctor c WHERE c.email = :email")})
public class CollabDoctor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "collab_id")
    private Integer collabId;
    @Column(name = "name")
    private String name;
    @Column(name = "phonenumber")
    private String phonenumber;
    @Column(name = "email")
    private String email;

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
