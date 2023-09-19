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
@Table(name = "vietnam_regions")
@NamedQueries({
    @NamedQuery(name = "VietnamRegions.findAll", query = "SELECT v FROM VietnamRegions v"),
    @NamedQuery(name = "VietnamRegions.findById", query = "SELECT v FROM VietnamRegions v WHERE v.id = :id"),
    @NamedQuery(name = "VietnamRegions.findByName", query = "SELECT v FROM VietnamRegions v WHERE v.name = :name"),
    @NamedQuery(name = "VietnamRegions.findByNameEn", query = "SELECT v FROM VietnamRegions v WHERE v.nameEn = :nameEn"),
    @NamedQuery(name = "VietnamRegions.findByCodeName", query = "SELECT v FROM VietnamRegions v WHERE v.codeName = :codeName"),
    @NamedQuery(name = "VietnamRegions.findByCodeNameEn", query = "SELECT v FROM VietnamRegions v WHERE v.codeNameEn = :codeNameEn")})
public class VietnamRegions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "name_en")
    private String nameEn;
    @Column(name = "code_name")
    private String codeName;
    @Column(name = "code_name_en")
    private String codeNameEn;
    @JsonIgnore
    @OneToMany(mappedBy = "vietnamRegionId")
    private Set<Provinces> provincesSet;

    public VietnamRegions() {
    }

    public VietnamRegions(Integer id) {
        this.id = id;
    }

    public VietnamRegions(Integer id, String name, String nameEn) {
        this.id = id;
        this.name = name;
        this.nameEn = nameEn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof VietnamRegions)) {
            return false;
        }
        VietnamRegions other = (VietnamRegions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.VietnamRegions[ id=" + id + " ]";
    }
    
}
