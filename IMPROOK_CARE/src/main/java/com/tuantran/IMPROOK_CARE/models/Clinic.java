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
@Table(name = "clinic")
@NamedQueries({
    @NamedQuery(name = "Clinic.findAll", query = "SELECT c FROM Clinic c"),
    @NamedQuery(name = "Clinic.findByClinicId", query = "SELECT c FROM Clinic c WHERE c.clinicId = :clinicId"),
    @NamedQuery(name = "Clinic.findByClinicName", query = "SELECT c FROM Clinic c WHERE c.clinicName = :clinicName"),
    @NamedQuery(name = "Clinic.findByAddress", query = "SELECT c FROM Clinic c WHERE c.address = :address"),
    @NamedQuery(name = "Clinic.findByAvatar", query = "SELECT c FROM Clinic c WHERE c.avatar = :avatar"),
    @NamedQuery(name = "Clinic.findByActive", query = "SELECT c FROM Clinic c WHERE c.active = :active")})
public class Clinic implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "clinic_id")
    private Integer clinicId;
    @Column(name = "clinic_name")
    private String clinicName;
    @Column(name = "address")
    private String address;
    @Lob
    @Column(name = "introduction")
    private String introduction;
    @Lob
    @Column(name = "description")
    private String description;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "active")
    private Boolean active;
    @OneToMany(mappedBy = "clinicId")
    private Set<ProfileDoctor> profileDoctorSet;
    @OneToMany(mappedBy = "clinicId")
    private Set<Comment> commentSet;

    public Clinic() {
    }

    public Clinic(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Set<Comment> getCommentSet() {
        return commentSet;
    }

    public void setCommentSet(Set<Comment> commentSet) {
        this.commentSet = commentSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (clinicId != null ? clinicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clinic)) {
            return false;
        }
        Clinic other = (Clinic) object;
        if ((this.clinicId == null && other.clinicId != null) || (this.clinicId != null && !this.clinicId.equals(other.clinicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.Clinic[ clinicId=" + clinicId + " ]";
    }
    
}
