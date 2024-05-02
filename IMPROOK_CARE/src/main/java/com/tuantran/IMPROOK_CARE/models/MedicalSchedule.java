/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.models;

import java.io.Serializable;
import java.util.Date;
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
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "medical_schedule")
@NamedQueries({
        @NamedQuery(name = "MedicalSchedule.findAll", query = "SELECT m FROM MedicalSchedule m"),
        @NamedQuery(name = "MedicalSchedule.findByMedicalScheduleId", query = "SELECT m FROM MedicalSchedule m WHERE m.medicalScheduleId = :medicalScheduleId"),
        @NamedQuery(name = "MedicalSchedule.findByCustomTime", query = "SELECT m FROM MedicalSchedule m WHERE m.customTime = :customTime"),
        @NamedQuery(name = "MedicalSchedule.findByStartDate", query = "SELECT m FROM MedicalSchedule m WHERE m.startDate = :startDate"),
        @NamedQuery(name = "MedicalSchedule.findByCreatedDate", query = "SELECT m FROM MedicalSchedule m WHERE m.createdDate = :createdDate"),
        @NamedQuery(name = "MedicalSchedule.findByUpdatedDate", query = "SELECT m FROM MedicalSchedule m WHERE m.updatedDate = :updatedDate"),
        @NamedQuery(name = "MedicalSchedule.findByDeletedDate", query = "SELECT m FROM MedicalSchedule m WHERE m.deletedDate = :deletedDate"),
        @NamedQuery(name = "MedicalSchedule.findByActive", query = "SELECT m FROM MedicalSchedule m WHERE m.active = :active") })
public class MedicalSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "medical_schedule_id")
    private Integer medicalScheduleId;
    @Column(name = "custom_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date customTime;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
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
    @JoinColumn(name = "medical_reminder_id", referencedColumnName = "medical_reminder_id")
    @ManyToOne
    private MedicalReminder medicalReminderId;

    public MedicalSchedule() {
    }

    public MedicalSchedule(Integer medicalScheduleId) {
        this.medicalScheduleId = medicalScheduleId;
    }

    public Integer getMedicalScheduleId() {
        return medicalScheduleId;
    }

    public void setMedicalScheduleId(Integer medicalScheduleId) {
        this.medicalScheduleId = medicalScheduleId;
    }

    public Date getCustomTime() {
        return customTime;
    }

    public void setCustomTime(Date customTime) {
        this.customTime = customTime;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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

    public MedicalReminder getMedicalReminderId() {
        return medicalReminderId;
    }

    public void setMedicalReminderId(MedicalReminder medicalReminderId) {
        this.medicalReminderId = medicalReminderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicalScheduleId != null ? medicalScheduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicalSchedule)) {
            return false;
        }
        MedicalSchedule other = (MedicalSchedule) object;
        if ((this.medicalScheduleId == null && other.medicalScheduleId != null)
                || (this.medicalScheduleId != null && !this.medicalScheduleId.equals(other.medicalScheduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.MedicalSchedule[ medicalScheduleId=" + medicalScheduleId + " ]";
    }

}
