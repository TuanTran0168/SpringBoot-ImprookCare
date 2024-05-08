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
@Table(name = "time_reminder")
@NamedQueries({
        @NamedQuery(name = "TimeReminder.findAll", query = "SELECT t FROM TimeReminder t"),
        @NamedQuery(name = "TimeReminder.findByTimeReminderId", query = "SELECT t FROM TimeReminder t WHERE t.timeReminderId = :timeReminderId"),
        @NamedQuery(name = "TimeReminder.findByTimeReminderName", query = "SELECT t FROM TimeReminder t WHERE t.timeReminderName = :timeReminderName"),
        @NamedQuery(name = "TimeReminder.findByTimeReminderValue", query = "SELECT t FROM TimeReminder t WHERE t.timeReminderValue = :timeReminderValue"),
        @NamedQuery(name = "TimeReminder.findByCreatedDate", query = "SELECT t FROM TimeReminder t WHERE t.createdDate = :createdDate"),
        @NamedQuery(name = "TimeReminder.findByUpdatedDate", query = "SELECT t FROM TimeReminder t WHERE t.updatedDate = :updatedDate"),
        @NamedQuery(name = "TimeReminder.findByDeletedDate", query = "SELECT t FROM TimeReminder t WHERE t.deletedDate = :deletedDate"),
        @NamedQuery(name = "TimeReminder.findByActive", query = "SELECT t FROM TimeReminder t WHERE t.active = :active") })
public class TimeReminder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "time_reminder_id")
    private Integer timeReminderId;
    @Column(name = "time_reminder_name")
    private String timeReminderName;
    @Column(name = "time_reminder_value")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeReminderValue;
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
    @OneToMany(mappedBy = "timeReminderId")
    private Set<MedicalReminder> medicalReminderSet;

    public TimeReminder() {
    }

    public TimeReminder(Integer timeReminderId) {
        this.timeReminderId = timeReminderId;
    }

    public Integer getTimeReminderId() {
        return timeReminderId;
    }

    public void setTimeReminderId(Integer timeReminderId) {
        this.timeReminderId = timeReminderId;
    }

    public String getTimeReminderName() {
        return timeReminderName;
    }

    public void setTimeReminderName(String timeReminderName) {
        this.timeReminderName = timeReminderName;
    }

    public Date getTimeReminderValue() {
        return timeReminderValue;
    }

    public void setTimeReminderValue(Date timeReminderValue) {
        this.timeReminderValue = timeReminderValue;
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

    public Set<MedicalReminder> getMedicalReminderSet() {
        return medicalReminderSet;
    }

    public void setMedicalReminderSet(Set<MedicalReminder> medicalReminderSet) {
        this.medicalReminderSet = medicalReminderSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (timeReminderId != null ? timeReminderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TimeReminder)) {
            return false;
        }
        TimeReminder other = (TimeReminder) object;
        if ((this.timeReminderId == null && other.timeReminderId != null)
                || (this.timeReminderId != null && !this.timeReminderId.equals(other.timeReminderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.TimeReminder[ timeReminderId=" + timeReminderId + " ]";
    }

}
