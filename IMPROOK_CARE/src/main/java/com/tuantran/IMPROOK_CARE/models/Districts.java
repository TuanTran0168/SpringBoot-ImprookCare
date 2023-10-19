/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "districts")
@NamedQueries({
    @NamedQuery(name = "Districts.findAll", query = "SELECT d FROM Districts d"),
    @NamedQuery(name = "Districts.findByCode", query = "SELECT d FROM Districts d WHERE d.code = :code"),
    @NamedQuery(name = "Districts.findByName", query = "SELECT d FROM Districts d WHERE d.name = :name"),
    @NamedQuery(name = "Districts.findByNameEn", query = "SELECT d FROM Districts d WHERE d.nameEn = :nameEn"),
    @NamedQuery(name = "Districts.findByFullName", query = "SELECT d FROM Districts d WHERE d.fullName = :fullName"),
    @NamedQuery(name = "Districts.findByFullNameEn", query = "SELECT d FROM Districts d WHERE d.fullNameEn = :fullNameEn"),
    @NamedQuery(name = "Districts.findByCodeName", query = "SELECT d FROM Districts d WHERE d.codeName = :codeName")})
public class Districts implements Serializable {

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
    @JoinColumn(name = "province_code", referencedColumnName = "code")
    @ManyToOne
    private Provinces provinceCode;
    @JoinColumn(name = "vietnam_unit_id", referencedColumnName = "id")
    @ManyToOne
    private VietnamUnits vietnamUnitId;
    @JsonIgnore
    @OneToMany(mappedBy = "districtCode")
    private Set<Wards> wardsSet;

    public Districts() {
    }

    public Districts(String code) {
        this.code = code;
    }

    public Districts(String code, String name) {
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

    public Provinces getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(Provinces provinceCode) {
        this.provinceCode = provinceCode;
    }

    public VietnamUnits getVietnamUnitId() {
        return vietnamUnitId;
    }

    public void setVietnamUnitId(VietnamUnits vietnamUnitId) {
        this.vietnamUnitId = vietnamUnitId;
    }

    public Set<Wards> getWardsSet() {
        return wardsSet;
    }

    public void setWardsSet(Set<Wards> wardsSet) {
        this.wardsSet = wardsSet;
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
        if (!(object instanceof Districts)) {
            return false;
        }
        Districts other = (Districts) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.Districts[ code=" + code + " ]";
    }

}
