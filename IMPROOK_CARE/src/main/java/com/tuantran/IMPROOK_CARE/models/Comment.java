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
@Table(name = "comment")
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c"),
    @NamedQuery(name = "Comment.findByCommentId", query = "SELECT c FROM Comment c WHERE c.commentId = :commentId"),
    @NamedQuery(name = "Comment.findByAvatar", query = "SELECT c FROM Comment c WHERE c.avatar = :avatar"),
    @NamedQuery(name = "Comment.findByCreatedDate", query = "SELECT c FROM Comment c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "Comment.findByRating", query = "SELECT c FROM Comment c WHERE c.rating = :rating"),
    @NamedQuery(name = "Comment.findByActive", query = "SELECT c FROM Comment c WHERE c.active = :active")})
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "comment_id")
    private Integer commentId;
    @Lob
    @Column(name = "content")
    private String content;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "created_date")
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @Column(name = "rating")
    private Integer rating;
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "clinic_id", referencedColumnName = "clinic_id")
    @ManyToOne
    private Clinic clinicId;
    @JoinColumn(name = "profile_doctor_id", referencedColumnName = "profile_doctor_id")
    @ManyToOne
    private ProfileDoctor profileDoctorId;

    public Comment() {
    }

    public Comment(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Clinic getClinicId() {
        return clinicId;
    }

    public void setClinicId(Clinic clinicId) {
        this.clinicId = clinicId;
    }

    public ProfileDoctor getProfileDoctorId() {
        return profileDoctorId;
    }

    public void setProfileDoctorId(ProfileDoctor profileDoctorId) {
        this.profileDoctorId = profileDoctorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (commentId != null ? commentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.commentId == null && other.commentId != null) || (this.commentId != null && !this.commentId.equals(other.commentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.Comment[ commentId=" + commentId + " ]";
    }
    
}
