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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "time_slot")
@NamedQueries({
    @NamedQuery(name = "TimeSlot.findAll", query = "SELECT t FROM TimeSlot t"),
    @NamedQuery(name = "TimeSlot.findByTimeSlotId", query = "SELECT t FROM TimeSlot t WHERE t.timeSlotId = :timeSlotId"),
    @NamedQuery(name = "TimeSlot.findByTimeSlotValue", query = "SELECT t FROM TimeSlot t WHERE t.timeSlotValue = :timeSlotValue")})
public class TimeSlot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "time_slot_id")
    private Integer timeSlotId;
    @Column(name = "time_slot_value")
    private String timeSlotValue;
    @OneToMany(mappedBy = "timeSlotId")
    private Set<Schedule> scheduleSet;
    @JoinColumn(name = "time_distance_id", referencedColumnName = "time_distance_id")
    @ManyToOne
    private TimeDistance timeDistanceId;

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

    public String getTimeSlotValue() {
        return timeSlotValue;
    }

    public void setTimeSlotValue(String timeSlotValue) {
        this.timeSlotValue = timeSlotValue;
    }

    public Set<Schedule> getScheduleSet() {
        return scheduleSet;
    }

    public void setScheduleSet(Set<Schedule> scheduleSet) {
        this.scheduleSet = scheduleSet;
    }

    public TimeDistance getTimeDistanceId() {
        return timeDistanceId;
    }

    public void setTimeDistanceId(TimeDistance timeDistanceId) {
        this.timeDistanceId = timeDistanceId;
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
