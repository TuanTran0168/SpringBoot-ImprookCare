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
import jakarta.persistence.Lob;
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
@Table(name = "notification")
@NamedQueries({
        @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n"),
        @NamedQuery(name = "Notification.findByNotificationId", query = "SELECT n FROM Notification n WHERE n.notificationId = :notificationId"),
        @NamedQuery(name = "Notification.findByProfileDoctorId", query = "SELECT n FROM Notification n WHERE n.profileDoctorId = :profileDoctorId"),
        @NamedQuery(name = "Notification.findByCreatedDate", query = "SELECT n FROM Notification n WHERE n.createdDate = :createdDate"),
        @NamedQuery(name = "Notification.findByUpdatedDate", query = "SELECT n FROM Notification n WHERE n.updatedDate = :updatedDate"),
        @NamedQuery(name = "Notification.findByDeletedDate", query = "SELECT n FROM Notification n WHERE n.deletedDate = :deletedDate"),
        @NamedQuery(name = "Notification.findByIsSeen", query = "SELECT n FROM Notification n WHERE n.isSeen = :isSeen"),
        @NamedQuery(name = "Notification.findByActive", query = "SELECT n FROM Notification n WHERE n.active = :active") })
public class Notification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "notification_id")
    private Integer notificationId;
    @Column(name = "profile_doctor_id")
    private Integer profileDoctorId;
    @Lob
    @Column(name = "notification_content")
    private String notificationContent;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "deleted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedDate;
    @Column(name = "is_seen")
    private Boolean isSeen;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "notification_type_id", referencedColumnName = "notification_type_id")
    @ManyToOne
    private NotificationType notificationTypeId;
    @JoinColumn(name = "sender_id", referencedColumnName = "user_id")
    @ManyToOne
    private User senderId;
    @JoinColumn(name = "receiver_id", referencedColumnName = "user_id")
    @ManyToOne
    private User receiverId;

    public Notification() {
    }

    public Notification(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getProfileDoctorId() {
        return profileDoctorId;
    }

    public void setProfileDoctorId(Integer profileDoctorId) {
        this.profileDoctorId = profileDoctorId;
    }

    public String getNotificationContent() {
        return notificationContent;
    }

    public void setNotificationContent(String notificationContent) {
        this.notificationContent = notificationContent;
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

    public Boolean getIsSeen() {
        return isSeen;
    }

    public void setIsSeen(Boolean isSeen) {
        this.isSeen = isSeen;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public NotificationType getNotificationTypeId() {
        return notificationTypeId;
    }

    public void setNotificationTypeId(NotificationType notificationTypeId) {
        this.notificationTypeId = notificationTypeId;
    }

    public User getSenderId() {
        return senderId;
    }

    public void setSenderId(User senderId) {
        this.senderId = senderId;
    }

    public User getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(User receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notificationId != null ? notificationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.notificationId == null && other.notificationId != null)
                || (this.notificationId != null && !this.notificationId.equals(other.notificationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.Notification[ notificationId=" + notificationId + " ]";
    }

}
