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
@Table(name = "booking")
@NamedQueries({
    @NamedQuery(name = "Booking.findAll", query = "SELECT b FROM Booking b"),
    @NamedQuery(name = "Booking.findByBookingId", query = "SELECT b FROM Booking b WHERE b.bookingId = :bookingId"),
    @NamedQuery(name = "Booking.findByCreatedDate", query = "SELECT b FROM Booking b WHERE b.createdDate = :createdDate"),
    @NamedQuery(name = "Booking.findByUpdatedDate", query = "SELECT b FROM Booking b WHERE b.updatedDate = :updatedDate"),
    @NamedQuery(name = "Booking.findByDeletedDate", query = "SELECT b FROM Booking b WHERE b.deletedDate = :deletedDate"),
    @NamedQuery(name = "Booking.findByBookingCancel", query = "SELECT b FROM Booking b WHERE b.bookingCancel = :bookingCancel"),
    @NamedQuery(name = "Booking.findByActive", query = "SELECT b FROM Booking b WHERE b.active = :active")})
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "booking_id")
    private Integer bookingId;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "deleted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;
    @Column(name = "booking_cancel")
    private Boolean bookingCancel;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "status_id", referencedColumnName = "status_id")
    @ManyToOne
    private BookingStatus statusId;
    @JoinColumn(name = "profile_patient_id", referencedColumnName = "profile_patient_id")
    @ManyToOne
    private ProfilePatient profilePatientId;
    @JoinColumn(name = "schedule_id", referencedColumnName = "schedule_id")
    @ManyToOne
    private Schedule scheduleId;
    @JsonIgnore
    @OneToMany(mappedBy = "bookingId")
    private Set<Prescriptions> prescriptionsSet;
    @JsonIgnore
    @OneToMany(mappedBy = "bookingId")
    private Set<MedicalRecords> medicalRecordsSet;

    public Booking() {
    }

    public Booking(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
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

    public Boolean getBookingCancel() {
        return bookingCancel;
    }

    public void setBookingCancel(Boolean bookingCancel) {
        this.bookingCancel = bookingCancel;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public BookingStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(BookingStatus statusId) {
        this.statusId = statusId;
    }

    public ProfilePatient getProfilePatientId() {
        return profilePatientId;
    }

    public void setProfilePatientId(ProfilePatient profilePatientId) {
        this.profilePatientId = profilePatientId;
    }

    public Schedule getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Schedule scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Set<Prescriptions> getPrescriptionsSet() {
        return prescriptionsSet;
    }

    public void setPrescriptionsSet(Set<Prescriptions> prescriptionsSet) {
        this.prescriptionsSet = prescriptionsSet;
    }

    public Set<MedicalRecords> getMedicalRecordsSet() {
        return medicalRecordsSet;
    }

    public void setMedicalRecordsSet(Set<MedicalRecords> medicalRecordsSet) {
        this.medicalRecordsSet = medicalRecordsSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (bookingId != null ? bookingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) object;
        if ((this.bookingId == null && other.bookingId != null) || (this.bookingId != null && !this.bookingId.equals(other.bookingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.Booking[ bookingId=" + bookingId + " ]";
    }

}
