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
@Table(name = "chatgpt_consult")
@NamedQueries({
        @NamedQuery(name = "ChatgptConsult.findAll", query = "SELECT c FROM ChatgptConsult c"),
        @NamedQuery(name = "ChatgptConsult.findByChatgptConsultId", query = "SELECT c FROM ChatgptConsult c WHERE c.chatgptConsultId = :chatgptConsultId"),
        @NamedQuery(name = "ChatgptConsult.findByPatientQuestion", query = "SELECT c FROM ChatgptConsult c WHERE c.patientQuestion = :patientQuestion"),
        @NamedQuery(name = "ChatgptConsult.findByChatgptConsultAnswer", query = "SELECT c FROM ChatgptConsult c WHERE c.chatgptConsultAnswer = :chatgptConsultAnswer"),
        @NamedQuery(name = "ChatgptConsult.findByCreatedDate", query = "SELECT c FROM ChatgptConsult c WHERE c.createdDate = :createdDate"),
        @NamedQuery(name = "ChatgptConsult.findByUpdatedDate", query = "SELECT c FROM ChatgptConsult c WHERE c.updatedDate = :updatedDate"),
        @NamedQuery(name = "ChatgptConsult.findByDeletedDate", query = "SELECT c FROM ChatgptConsult c WHERE c.deletedDate = :deletedDate"),
        @NamedQuery(name = "ChatgptConsult.findByActive", query = "SELECT c FROM ChatgptConsult c WHERE c.active = :active") })
public class ChatgptConsult implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "chatgpt_consult_id")
    private Integer chatgptConsultId;
    @Column(name = "patient_question")
    private String patientQuestion;
    @Column(name = "chatgpt_consult_answer")
    private String chatgptConsultAnswer;
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
    @JoinColumn(name = "chatgpt_question_id", referencedColumnName = "chatgpt_question_id")
    @ManyToOne
    private ChatgptQuestion chatgptQuestionId;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;

    public ChatgptConsult() {
    }

    public ChatgptConsult(Integer chatgptConsultId) {
        this.chatgptConsultId = chatgptConsultId;
    }

    public Integer getChatgptConsultId() {
        return chatgptConsultId;
    }

    public void setChatgptConsultId(Integer chatgptConsultId) {
        this.chatgptConsultId = chatgptConsultId;
    }

    public String getPatientQuestion() {
        return patientQuestion;
    }

    public void setPatientQuestion(String patientQuestion) {
        this.patientQuestion = patientQuestion;
    }

    public String getChatgptConsultAnswer() {
        return chatgptConsultAnswer;
    }

    public void setChatgptConsultAnswer(String chatgptConsultAnswer) {
        this.chatgptConsultAnswer = chatgptConsultAnswer;
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

    public ChatgptQuestion getChatgptQuestionId() {
        return chatgptQuestionId;
    }

    public void setChatgptQuestionId(ChatgptQuestion chatgptQuestionId) {
        this.chatgptQuestionId = chatgptQuestionId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chatgptConsultId != null ? chatgptConsultId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChatgptConsult)) {
            return false;
        }
        ChatgptConsult other = (ChatgptConsult) object;
        if ((this.chatgptConsultId == null && other.chatgptConsultId != null)
                || (this.chatgptConsultId != null && !this.chatgptConsultId.equals(other.chatgptConsultId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.ChatgptConsult[ chatgptConsultId=" + chatgptConsultId + " ]";
    }

}
