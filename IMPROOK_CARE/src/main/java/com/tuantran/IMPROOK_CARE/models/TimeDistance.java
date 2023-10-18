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
@Table(name = "time_distance")
@NamedQueries({
    @NamedQuery(name = "TimeDistance.findAll", query = "SELECT t FROM TimeDistance t"),
    @NamedQuery(name = "TimeDistance.findByTimeDistanceId", query = "SELECT t FROM TimeDistance t WHERE t.timeDistanceId = :timeDistanceId"),
    @NamedQuery(name = "TimeDistance.findByTimeDistanceValue", query = "SELECT t FROM TimeDistance t WHERE t.timeDistanceValue = :timeDistanceValue"),
    @NamedQuery(name = "TimeDistance.findByCreatedDate", query = "SELECT t FROM TimeDistance t WHERE t.createdDate = :createdDate"),
    @NamedQuery(name = "TimeDistance.findByUpdatedDate", query = "SELECT t FROM TimeDistance t WHERE t.updatedDate = :updatedDate"),
    @NamedQuery(name = "TimeDistance.findByDeletedDate", query = "SELECT t FROM TimeDistance t WHERE t.deletedDate = :deletedDate"),
    @NamedQuery(name = "TimeDistance.findByActive", query = "SELECT t FROM TimeDistance t WHERE t.active = :active")})
public class TimeDistance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "time_distance_id")
    private Integer timeDistanceId;
    @Column(name = "time_distance_value")
    private String timeDistanceValue;
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
    @OneToMany(mappedBy = "timeDistanceId")
    private Set<TimeSlot> timeSlotSet;

    public TimeDistance() {
    }

    public TimeDistance(Integer timeDistanceId) {
        this.timeDistanceId = timeDistanceId;
    }

    public Integer getTimeDistanceId() {
        return timeDistanceId;
    }

    public void setTimeDistanceId(Integer timeDistanceId) {
        this.timeDistanceId = timeDistanceId;
    }

    public String getTimeDistanceValue() {
        return timeDistanceValue;
    }

    public void setTimeDistanceValue(String timeDistanceValue) {
        this.timeDistanceValue = timeDistanceValue;
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

    public Set<TimeSlot> getTimeSlotSet() {
        return timeSlotSet;
    }

    public void setTimeSlotSet(Set<TimeSlot> timeSlotSet) {
        this.timeSlotSet = timeSlotSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (timeDistanceId != null ? timeDistanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TimeDistance)) {
            return false;
        }
        TimeDistance other = (TimeDistance) object;
        if ((this.timeDistanceId == null && other.timeDistanceId != null) || (this.timeDistanceId != null && !this.timeDistanceId.equals(other.timeDistanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.TimeDistance[ timeDistanceId=" + timeDistanceId + " ]";
    }

}
