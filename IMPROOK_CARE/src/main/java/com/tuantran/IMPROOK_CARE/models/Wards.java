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
@Table(name = "wards")
@NamedQueries({
    @NamedQuery(name = "Wards.findAll", query = "SELECT w FROM Wards w"),
    @NamedQuery(name = "Wards.findByCode", query = "SELECT w FROM Wards w WHERE w.code = :code"),
    @NamedQuery(name = "Wards.findByName", query = "SELECT w FROM Wards w WHERE w.name = :name"),
    @NamedQuery(name = "Wards.findByNameEn", query = "SELECT w FROM Wards w WHERE w.nameEn = :nameEn"),
    @NamedQuery(name = "Wards.findByFullName", query = "SELECT w FROM Wards w WHERE w.fullName = :fullName"),
    @NamedQuery(name = "Wards.findByFullNameEn", query = "SELECT w FROM Wards w WHERE w.fullNameEn = :fullNameEn"),
    @NamedQuery(name = "Wards.findByCodeName", query = "SELECT w FROM Wards w WHERE w.codeName = :codeName")})
public class Wards implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "full_name_en")
    private String fullNameEn;
    @Column(name = "code_name")
    private String codeName;
    @JoinColumn(name = "district_code", referencedColumnName = "code")
    @ManyToOne
    private Districts districtCode;
    @JoinColumn(name = "vietnam_unit_id", referencedColumnName = "id")
    @ManyToOne
    private VietnamUnits vietnamUnitId;

    public Wards() {
    }

    public Wards(String code) {
        this.code = code;
    }

    public Wards(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullNameEn() {
        return fullNameEn;
    }

    public void setFullNameEn(String fullNameEn) {
        this.fullNameEn = fullNameEn;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Districts getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Districts districtCode) {
        this.districtCode = districtCode;
    }

    public VietnamUnits getVietnamUnitId() {
        return vietnamUnitId;
    }

    public void setVietnamUnitId(VietnamUnits vietnamUnitId) {
        this.vietnamUnitId = vietnamUnitId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Wards)) {
            return false;
        }
        Wards other = (Wards) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.Wards[ code=" + code + " ]";
    }
    
}
