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
@Table(name = "time_slot")
@NamedQueries({
    @NamedQuery(name = "TimeSlot.findAll", query = "SELECT t FROM TimeSlot t"),
    @NamedQuery(name = "TimeSlot.findByTimeSlotId", query = "SELECT t FROM TimeSlot t WHERE t.timeSlotId = :timeSlotId"),
    @NamedQuery(name = "TimeSlot.findByTimeBegin", query = "SELECT t FROM TimeSlot t WHERE t.timeBegin = :timeBegin"),
    @NamedQuery(name = "TimeSlot.findByTimeEnd", query = "SELECT t FROM TimeSlot t WHERE t.timeEnd = :timeEnd"),
    @NamedQuery(name = "TimeSlot.findByCreatedDate", query = "SELECT t FROM TimeSlot t WHERE t.createdDate = :createdDate"),
    @NamedQuery(name = "TimeSlot.findByUpdatedDate", query = "SELECT t FROM TimeSlot t WHERE t.updatedDate = :updatedDate"),
    @NamedQuery(name = "TimeSlot.findByDeletedDate", query = "SELECT t FROM TimeSlot t WHERE t.deletedDate = :deletedDate"),
    @NamedQuery(name = "TimeSlot.findByActive", query = "SELECT t FROM TimeSlot t WHERE t.active = :active")})
public class TimeSlot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "time_slot_id")
    private Integer timeSlotId;
    @Column(name = "time_begin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeBegin;
    @Column(name = "time_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeEnd;
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
    @JoinColumn(name = "time_distance_id", referencedColumnName = "time_distance_id")
    @ManyToOne
    private TimeDistance timeDistanceId;
    @JsonIgnore
    @OneToMany(mappedBy = "timeSlotId")
    private Set<Schedule> scheduleSet;

    public TimeSlot() {
    }

    public TimeSlot(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public Integer getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Integer timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public Date getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(Date timeBegin) {
        this.timeBegin = timeBegin;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
        this.timeEnd = timeEnd;
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

    public TimeDistance getTimeDistanceId() {
        return timeDistanceId;
    }

    public void setTimeDistanceId(TimeDistance timeDistanceId) {
        this.timeDistanceId = timeDistanceId;
    }

    public Set<Schedule> getScheduleSet() {
        return scheduleSet;
    }

    public void setScheduleSet(Set<Schedule> scheduleSet) {
        this.scheduleSet = scheduleSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (timeSlotId != null ? timeSlotId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TimeSlot)) {
            return false;
        }
        TimeSlot other = (TimeSlot) object;
        if ((this.timeSlotId == null && other.timeSlotId != null) || (this.timeSlotId != null && !this.timeSlotId.equals(other.timeSlotId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.TimeSlot[ timeSlotId=" + timeSlotId + " ]";
    }

}
