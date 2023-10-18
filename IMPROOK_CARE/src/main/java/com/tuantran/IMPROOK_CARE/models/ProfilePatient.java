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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "profile_patient")
@NamedQueries({
    @NamedQuery(name = "ProfilePatient.findAll", query = "SELECT p FROM ProfilePatient p"),
    @NamedQuery(name = "ProfilePatient.findByProfilePatientId", query = "SELECT p FROM ProfilePatient p WHERE p.profilePatientId = :profilePatientId"),
    @NamedQuery(name = "ProfilePatient.findByName", query = "SELECT p FROM ProfilePatient p WHERE p.name = :name"),
    @NamedQuery(name = "ProfilePatient.findByPhonenumber", query = "SELECT p FROM ProfilePatient p WHERE p.phonenumber = :phonenumber"),
    @NamedQuery(name = "ProfilePatient.findByBirthday", query = "SELECT p FROM ProfilePatient p WHERE p.birthday = :birthday"),
    @NamedQuery(name = "ProfilePatient.findByGender", query = "SELECT p FROM ProfilePatient p WHERE p.gender = :gender"),
    @NamedQuery(name = "ProfilePatient.findByProvinceName", query = "SELECT p FROM ProfilePatient p WHERE p.provinceName = :provinceName"),
    @NamedQuery(name = "ProfilePatient.findByDistrictName", query = "SELECT p FROM ProfilePatient p WHERE p.districtName = :districtName"),
    @NamedQuery(name = "ProfilePatient.findByWardName", query = "SELECT p FROM ProfilePatient p WHERE p.wardName = :wardName"),
    @NamedQuery(name = "ProfilePatient.findByPersonalAddress", query = "SELECT p FROM ProfilePatient p WHERE p.personalAddress = :personalAddress"),
    @NamedQuery(name = "ProfilePatient.findByAddress", query = "SELECT p FROM ProfilePatient p WHERE p.address = :address"),
    @NamedQuery(name = "ProfilePatient.findByEmail", query = "SELECT p FROM ProfilePatient p WHERE p.email = :email"),
    @NamedQuery(name = "ProfilePatient.findByRelationship", query = "SELECT p FROM ProfilePatient p WHERE p.relationship = :relationship"),
    @NamedQuery(name = "ProfilePatient.findByCreatedDate", query = "SELECT p FROM ProfilePatient p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "ProfilePatient.findByUpdatedDate", query = "SELECT p FROM ProfilePatient p WHERE p.updatedDate = :updatedDate"),
    @NamedQuery(name = "ProfilePatient.findByDeletedDate", query = "SELECT p FROM ProfilePatient p WHERE p.deletedDate = :deletedDate"),
    @NamedQuery(name = "ProfilePatient.findByActive", query = "SELECT p FROM ProfilePatient p WHERE p.active = :active")})
public class ProfilePatient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "profile_patient_id")
    private Integer profilePatientId;
    @Column(name = "name")
    private String name;
    @Column(name = "phonenumber")
    private String phonenumber;
    @Column(name = "birthday")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    @Column(name = "gender")
    private Boolean gender;
    @Column(name = "province_name")
    private String provinceName;
    @Column(name = "district_name")
    private String districtName;
    @Column(name = "ward_name")
    private String wardName;
    @Column(name = "personal_address")
    private String personalAddress;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "relationship")
    private String relationship;
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
    @OneToMany(mappedBy = "profilePatientId")
    private Set<Booking> bookingSet;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;

    public ProfilePatient() {
    }

    public ProfilePatient(Integer profilePatientId) {
        this.profilePatientId = profilePatientId;
    }

    public Integer getProfilePatientId() {
        return profilePatientId;
    }

    public void setProfilePatientId(Integer profilePatientId) {
        this.profilePatientId = profilePatientId;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getPersonalAddress() {
        return personalAddress;
    }

    public void setPersonalAddress(String personalAddress) {
        this.personalAddress = personalAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
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

    public Set<Booking> getBookingSet() {
        return bookingSet;
    }

    public void setBookingSet(Set<Booking> bookingSet) {
        this.bookingSet = bookingSet;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (profilePatientId != null ? profilePatientId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfilePatient)) {
            return false;
        }
        ProfilePatient other = (ProfilePatient) object;
        if ((this.profilePatientId == null && other.profilePatientId != null) || (this.profilePatientId != null && !this.profilePatientId.equals(other.profilePatientId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.ProfilePatient[ profilePatientId=" + profilePatientId + " ]";
    }

}
