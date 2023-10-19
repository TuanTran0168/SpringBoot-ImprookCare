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
@Table(name = "profile_doctor")
@NamedQueries({
    @NamedQuery(name = "ProfileDoctor.findAll", query = "SELECT p FROM ProfileDoctor p"),
    @NamedQuery(name = "ProfileDoctor.findByProfileDoctorId", query = "SELECT p FROM ProfileDoctor p WHERE p.profileDoctorId = :profileDoctorId"),
    @NamedQuery(name = "ProfileDoctor.findByName", query = "SELECT p FROM ProfileDoctor p WHERE p.name = :name"),
    @NamedQuery(name = "ProfileDoctor.findByBirthday", query = "SELECT p FROM ProfileDoctor p WHERE p.birthday = :birthday"),
    @NamedQuery(name = "ProfileDoctor.findByPhonenumber", query = "SELECT p FROM ProfileDoctor p WHERE p.phonenumber = :phonenumber"),
    @NamedQuery(name = "ProfileDoctor.findByBookingPrice", query = "SELECT p FROM ProfileDoctor p WHERE p.bookingPrice = :bookingPrice"),
    @NamedQuery(name = "ProfileDoctor.findByEmail", query = "SELECT p FROM ProfileDoctor p WHERE p.email = :email"),
    @NamedQuery(name = "ProfileDoctor.findByProvinceName", query = "SELECT p FROM ProfileDoctor p WHERE p.provinceName = :provinceName"),
    @NamedQuery(name = "ProfileDoctor.findByDistrictName", query = "SELECT p FROM ProfileDoctor p WHERE p.districtName = :districtName"),
    @NamedQuery(name = "ProfileDoctor.findByWardName", query = "SELECT p FROM ProfileDoctor p WHERE p.wardName = :wardName"),
    @NamedQuery(name = "ProfileDoctor.findByWorkPlace", query = "SELECT p FROM ProfileDoctor p WHERE p.workPlace = :workPlace"),
    @NamedQuery(name = "ProfileDoctor.findByWorkAddress", query = "SELECT p FROM ProfileDoctor p WHERE p.workAddress = :workAddress"),
    @NamedQuery(name = "ProfileDoctor.findByPosition", query = "SELECT p FROM ProfileDoctor p WHERE p.position = :position"),
    @NamedQuery(name = "ProfileDoctor.findByTotalRating", query = "SELECT p FROM ProfileDoctor p WHERE p.totalRating = :totalRating"),
    @NamedQuery(name = "ProfileDoctor.findByCountRating", query = "SELECT p FROM ProfileDoctor p WHERE p.countRating = :countRating"),
    @NamedQuery(name = "ProfileDoctor.findByCreatedDate", query = "SELECT p FROM ProfileDoctor p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "ProfileDoctor.findByUpdatedDate", query = "SELECT p FROM ProfileDoctor p WHERE p.updatedDate = :updatedDate"),
    @NamedQuery(name = "ProfileDoctor.findByDeletedDate", query = "SELECT p FROM ProfileDoctor p WHERE p.deletedDate = :deletedDate"),
    @NamedQuery(name = "ProfileDoctor.findByActive", query = "SELECT p FROM ProfileDoctor p WHERE p.active = :active")})
public class ProfileDoctor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "profile_doctor_id")
    private Integer profileDoctorId;
    @Column(name = "name")
    private String name;
    @Column(name = "birthday")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    @Column(name = "phonenumber")
    private String phonenumber;
    @Column(name = "booking_price")
    private String bookingPrice;
    @Column(name = "email")
    private String email;
    @Column(name = "province_name")
    private String provinceName;
    @Column(name = "district_name")
    private String districtName;
    @Column(name = "ward_name")
    private String wardName;
    @Column(name = "work_place")
    private String workPlace;
    @Column(name = "work_address")
    private String workAddress;
    @Column(name = "position")
    private String position;
    @Column(name = "total_rating")
    private String totalRating;
    @Column(name = "count_rating")
    private String countRating;
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
    @JoinColumn(name = "specialty_id", referencedColumnName = "specialty_id")
    @ManyToOne
    private Specialty specialtyId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;
    @JsonIgnore
    @OneToMany(mappedBy = "profileDoctorId")
    private Set<Message> messageSet;
    @JsonIgnore
    @OneToMany(mappedBy = "profileDoctorId")
    private Set<Schedule> scheduleSet;
    @JsonIgnore
    @OneToMany(mappedBy = "profileDoctorId")
    private Set<Comment> commentSet;

    public ProfileDoctor() {
    }

    public ProfileDoctor(Integer profileDoctorId) {
        this.profileDoctorId = profileDoctorId;
    }

    public Integer getProfileDoctorId() {
        return profileDoctorId;
    }

    public void setProfileDoctorId(Integer profileDoctorId) {
        this.profileDoctorId = profileDoctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getBookingPrice() {
        return bookingPrice;
    }

    public void setBookingPrice(String bookingPrice) {
        this.bookingPrice = bookingPrice;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getWorkAddress() {
        return workAddress;
    }

    public void setWorkAddress(String workAddress) {
        this.workAddress = workAddress;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public String getCountRating() {
        return countRating;
    }

    public void setCountRating(String countRating) {
        this.countRating = countRating;
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

    public Specialty getSpecialtyId() {
        return specialtyId;
    }

    public void setSpecialtyId(Specialty specialtyId) {
        this.specialtyId = specialtyId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Set<Message> getMessageSet() {
        return messageSet;
    }

    public void setMessageSet(Set<Message> messageSet) {
        this.messageSet = messageSet;
    }

    public Set<Schedule> getScheduleSet() {
        return scheduleSet;
    }

    public void setScheduleSet(Set<Schedule> scheduleSet) {
        this.scheduleSet = scheduleSet;
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
        hash += (profileDoctorId != null ? profileDoctorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfileDoctor)) {
            return false;
        }
        ProfileDoctor other = (ProfileDoctor) object;
        if ((this.profileDoctorId == null && other.profileDoctorId != null) || (this.profileDoctorId != null && !this.profileDoctorId.equals(other.profileDoctorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.ProfileDoctor[ profileDoctorId=" + profileDoctorId + " ]";
    }

}
