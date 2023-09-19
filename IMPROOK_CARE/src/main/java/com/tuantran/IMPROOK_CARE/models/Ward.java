/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.models;

import java.io.Serializable;
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

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "ward")
@NamedQueries({
    @NamedQuery(name = "Ward.findAll", query = "SELECT w FROM Ward w"),
    @NamedQuery(name = "Ward.findByWardId", query = "SELECT w FROM Ward w WHERE w.wardId = :wardId"),
    @NamedQuery(name = "Ward.findByWardName", query = "SELECT w FROM Ward w WHERE w.wardName = :wardName")})
public class Ward implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ward_id")
    private Integer wardId;
    @Column(name = "ward_name")
    private String wardName;
    @JoinColumn(name = "district_id", referencedColumnName = "district_id")
    @ManyToOne
    private District districtId;

    public Ward() {
    }

    public Ward(Integer wardId) {
        this.wardId = wardId;
    }

    public Integer getWardId() {
        return wardId;
    }

    public void setWardId(Integer wardId) {
        this.wardId = wardId;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public District getDistrictId() {
        return districtId;
    }

    public void setDistrictId(District districtId) {
        this.districtId = districtId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (wardId != null ? wardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ward)) {
            return false;
        }
        Ward other = (Ward) object;
        if ((this.wardId == null && other.wardId != null) || (this.wardId != null && !this.wardId.equals(other.wardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.Ward[ wardId=" + wardId + " ]";
    }
    
}
