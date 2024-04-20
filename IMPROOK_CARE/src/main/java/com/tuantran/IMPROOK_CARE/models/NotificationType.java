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
import jakarta.persistence.Lob;
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
@Table(name = "notification_type")
@NamedQueries({
        @NamedQuery(name = "NotificationType.findAll", query = "SELECT n FROM NotificationType n"),
        @NamedQuery(name = "NotificationType.findByNotificationTypeId", query = "SELECT n FROM NotificationType n WHERE n.notificationTypeId = :notificationTypeId"),
        @NamedQuery(name = "NotificationType.findByCreatedDate", query = "SELECT n FROM NotificationType n WHERE n.createdDate = :createdDate"),
        @NamedQuery(name = "NotificationType.findByUpdatedDate", query = "SELECT n FROM NotificationType n WHERE n.updatedDate = :updatedDate"),
        @NamedQuery(name = "NotificationType.findByDeletedDate", query = "SELECT n FROM NotificationType n WHERE n.deletedDate = :deletedDate"),
        @NamedQuery(name = "NotificationType.findByActive", query = "SELECT n FROM NotificationType n WHERE n.active = :active") })
public class NotificationType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "notification_type_id")
    private Integer notificationTypeId;
    @Lob
    @Column(name = "notification_type_value")
    private String notificationTypeValue;
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
    @OneToMany(mappedBy = "notificationTypeId")
    private Set<Notification> notificationSet;

    public NotificationType() {
    }

    public NotificationType(Integer notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }

    public Integer getNotificationTypeId() {
        return notificationTypeId;
    }

    public void setNotificationTypeId(Integer notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }

    public String getNotificationTypeValue() {
        return notificationTypeValue;
    }

    public void setNotificationTypeValue(String notificationTypeValue) {
        this.notificationTypeValue = notificationTypeValue;
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

    public Set<Notification> getNotificationSet() {
        return notificationSet;
    }

    public void setNotificationSet(Set<Notification> notificationSet) {
        this.notificationSet = notificationSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificationTypeId != null ? notificationTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificationType)) {
            return false;
        }
        NotificationType other = (NotificationType) object;
        if ((this.notificationTypeId == null && other.notificationTypeId != null)
                || (this.notificationTypeId != null && !this.notificationTypeId.equals(other.notificationTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.NotificationType[ notificationTypeId=" + notificationTypeId + " ]";
    }

}
