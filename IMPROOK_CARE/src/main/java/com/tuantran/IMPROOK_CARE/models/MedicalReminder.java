/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "medical_reminder")
@NamedQueries({
        @NamedQuery(name = "MedicalReminder.findAll", query = "SELECT m FROM MedicalReminder m"),
        @NamedQuery(name = "MedicalReminder.findByMedicalReminderId", query = "SELECT m FROM MedicalReminder m WHERE m.medicalReminderId = :medicalReminderId"),
        @NamedQuery(name = "MedicalReminder.findByCreatedDate", query = "SELECT m FROM MedicalReminder m WHERE m.createdDate = :createdDate"),
        @NamedQuery(name = "MedicalReminder.findByUpdatedDate", query = "SELECT m FROM MedicalReminder m WHERE m.updatedDate = :updatedDate"),
        @NamedQuery(name = "MedicalReminder.findByDeletedDate", query = "SELECT m FROM MedicalReminder m WHERE m.deletedDate = :deletedDate"),
        @NamedQuery(name = "MedicalReminder.findByActive", query = "SELECT m FROM MedicalReminder m WHERE m.active = :active") })
public class MedicalReminder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "medical_reminder_id")
    private Integer medicalReminderId;
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
    @OneToMany(mappedBy = "medicalReminderId")
    private Set<MedicalSchedule> medicalScheduleSet;
    @JoinColumn(name = "prescription_detail_id", referencedColumnName = "prescription_detail_id")
    @ManyToOne
    private PrescriptionDetail prescriptionDetailId;
    @JoinColumn(name = "time_reminder_id", referencedColumnName = "time_reminder_id")
    @ManyToOne
    private TimeReminder timeReminderId;

    public MedicalReminder() {
    }

    public MedicalReminder(Integer medicalReminderId) {
        this.medicalReminderId = medicalReminderId;
    }

    public Integer getMedicalReminderId() {
        return medicalReminderId;
    }

    public void setMedicalReminderId(Integer medicalReminderId) {
        this.medicalReminderId = medicalReminderId;
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

    public Set<MedicalSchedule> getMedicalScheduleSet() {
        return medicalScheduleSet;
    }

    public void setMedicalScheduleSet(Set<MedicalSchedule> medicalScheduleSet) {
        this.medicalScheduleSet = medicalScheduleSet;
    }

    public PrescriptionDetail getPrescriptionDetailId() {
        return prescriptionDetailId;
    }

    public void setPrescriptionDetailId(PrescriptionDetail prescriptionDetailId) {
        this.prescriptionDetailId = prescriptionDetailId;
    }

    public TimeReminder getTimeReminderId() {
        return timeReminderId;
    }

    public void setTimeReminderId(TimeReminder timeReminderId) {
        this.timeReminderId = timeReminderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicalReminderId != null ? medicalReminderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicalReminder)) {
            return false;
        }
        MedicalReminder other = (MedicalReminder) object;
        if ((this.medicalReminderId == null && other.medicalReminderId != null)
                || (this.medicalReminderId != null && !this.medicalReminderId.equals(other.medicalReminderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.MedicalReminder[ medicalReminderId=" + medicalReminderId + " ]";
    }

}
