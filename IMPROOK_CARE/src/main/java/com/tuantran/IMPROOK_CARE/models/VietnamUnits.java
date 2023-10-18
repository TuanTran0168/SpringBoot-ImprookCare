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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "vietnam_units")
@NamedQueries({
    @NamedQuery(name = "VietnamUnits.findAll", query = "SELECT v FROM VietnamUnits v"),
    @NamedQuery(name = "VietnamUnits.findById", query = "SELECT v FROM VietnamUnits v WHERE v.id = :id"),
    @NamedQuery(name = "VietnamUnits.findByFullName", query = "SELECT v FROM VietnamUnits v WHERE v.fullName = :fullName"),
    @NamedQuery(name = "VietnamUnits.findByFullNameEn", query = "SELECT v FROM VietnamUnits v WHERE v.fullNameEn = :fullNameEn"),
    @NamedQuery(name = "VietnamUnits.findByShortName", query = "SELECT v FROM VietnamUnits v WHERE v.shortName = :shortName"),
    @NamedQuery(name = "VietnamUnits.findByShortNameEn", query = "SELECT v FROM VietnamUnits v WHERE v.shortNameEn = :shortNameEn"),
    @NamedQuery(name = "VietnamUnits.findByCodeName", query = "SELECT v FROM VietnamUnits v WHERE v.codeName = :codeName"),
    @NamedQuery(name = "VietnamUnits.findByCodeNameEn", query = "SELECT v FROM VietnamUnits v WHERE v.codeNameEn = :codeNameEn")})
public class VietnamUnits implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "full_name_en")
    private String fullNameEn;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "short_name_en")
    private String shortNameEn;
    @Column(name = "code_name")
    private String codeName;
    @Column(name = "code_name_en")
    private String codeNameEn;
    @JsonIgnore
    @OneToMany(mappedBy = "vietnamUnitId")
    private Set<Districts> districtsSet;
    @JsonIgnore
    @OneToMany(mappedBy = "vietnamUnitId")
    private Set<Wards> wardsSet;
    @JsonIgnore
    @OneToMany(mappedBy = "vietnamUnitId")
    private Set<Provinces> provincesSet;

    public VietnamUnits() {
    }

    public VietnamUnits(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getShortNameEn() {
        return shortNameEn;
    }

    public void setShortNameEn(String shortNameEn) {
        this.shortNameEn = shortNameEn;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public String getCodeNameEn() {
        return codeNameEn;
    }

    public void setCodeNameEn(String codeNameEn) {
        this.codeNameEn = codeNameEn;
    }

    public Set<Districts> getDistrictsSet() {
        return districtsSet;
    }

    public void setDistrictsSet(Set<Districts> districtsSet) {
        this.districtsSet = districtsSet;
    }

    public Set<Wards> getWardsSet() {
        return wardsSet;
    }

    public void setWardsSet(Set<Wards> wardsSet) {
        this.wardsSet = wardsSet;
    }

    public Set<Provinces> getProvincesSet() {
        return provincesSet;
    }

    public void setProvincesSet(Set<Provinces> provincesSet) {
        this.provincesSet = provincesSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VietnamUnits)) {
            return false;
        }
        VietnamUnits other = (VietnamUnits) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.VietnamUnits[ id=" + id + " ]";
    }

}
