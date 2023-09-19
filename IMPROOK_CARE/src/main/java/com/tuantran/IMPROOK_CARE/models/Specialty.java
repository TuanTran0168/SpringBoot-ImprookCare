/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.models;

import java.io.Serializable;
import java.util.Set;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "specialty")
@NamedQueries({
    @NamedQuery(name = "Specialty.findAll", query = "SELECT s FROM Specialty s"),
    @NamedQuery(name = "Specialty.findBySpecialtyId", query = "SELECT s FROM Specialty s WHERE s.specialtyId = :specialtyId"),
    @NamedQuery(name = "Specialty.findBySpecialtyName", query = "SELECT s FROM Specialty s WHERE s.specialtyName = :specialtyName"),
    @NamedQuery(name = "Specialty.findByImage", query = "SELECT s FROM Specialty s WHERE s.image = :image"),
    @NamedQuery(name = "Specialty.findByActive", query = "SELECT s FROM Specialty s WHERE s.active = :active")})
public class Specialty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "specialty_id")
    private Integer specialtyId;
    @Column(name = "specialty_name")
    private String specialtyName;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "image")
    private String image;
    @Column(name = "active")
    private Boolean active;
    @OneToMany(mappedBy = "specialtyId")
    private Set<ProfileDoctor> profileDoctorSet;

    public Specialty() {
    }

    public Specialty(Integer specialtyId) {
        this.specialtyId = specialtyId;
    }

    public Integer getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Integer specialtyId) {
        this.specialtyId = specialtyId;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Set<ProfileDoctor> getProfileDoctorSet() {
        return profileDoctorSet;
    }

    public void setProfileDoctorSet(Set<ProfileDoctor> profileDoctorSet) {
        this.profileDoctorSet = profileDoctorSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (specialtyId != null ? specialtyId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Specialty)) {
            return false;
        }
        Specialty other = (Specialty) object;
        if ((this.specialtyId == null && other.specialtyId != null) || (this.specialtyId != null && !this.specialtyId.equals(other.specialtyId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.Specialty[ specialtyId=" + specialtyId + " ]";
    }
    
}
