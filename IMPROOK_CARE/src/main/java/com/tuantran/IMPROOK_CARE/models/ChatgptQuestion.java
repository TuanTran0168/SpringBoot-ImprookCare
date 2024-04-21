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
@Table(name = "chatgpt_question")
@NamedQueries({
    @NamedQuery(name = "ChatgptQuestion.findAll", query = "SELECT c FROM ChatgptQuestion c"),
    @NamedQuery(name = "ChatgptQuestion.findByChatgptQuestionId", query = "SELECT c FROM ChatgptQuestion c WHERE c.chatgptQuestionId = :chatgptQuestionId"),
    @NamedQuery(name = "ChatgptQuestion.findByChatgptQuestionContent", query = "SELECT c FROM ChatgptQuestion c WHERE c.chatgptQuestionContent = :chatgptQuestionContent"),
    @NamedQuery(name = "ChatgptQuestion.findByCreatedDate", query = "SELECT c FROM ChatgptQuestion c WHERE c.createdDate = :createdDate"),
    @NamedQuery(name = "ChatgptQuestion.findByUpdatedDate", query = "SELECT c FROM ChatgptQuestion c WHERE c.updatedDate = :updatedDate"),
    @NamedQuery(name = "ChatgptQuestion.findByDeletedDate", query = "SELECT c FROM ChatgptQuestion c WHERE c.deletedDate = :deletedDate"),
    @NamedQuery(name = "ChatgptQuestion.findByActive", query = "SELECT c FROM ChatgptQuestion c WHERE c.active = :active")})
public class ChatgptQuestion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "chatgpt_question_id")
    private Integer chatgptQuestionId;
    @Column(name = "chatgpt_question_content")
    private String chatgptQuestionContent;
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
    @OneToMany(mappedBy = "chatgptQuestionId")
    private Set<ChatgptConsult> chatgptConsultSet;

    public ChatgptQuestion() {
    }

    public ChatgptQuestion(Integer chatgptQuestionId) {
        this.chatgptQuestionId = chatgptQuestionId;
    }

    public Integer getChatgptQuestionId() {
        return chatgptQuestionId;
    }

    public void setChatgptQuestionId(Integer chatgptQuestionId) {
        this.chatgptQuestionId = chatgptQuestionId;
    }

    public String getChatgptQuestionContent() {
        return chatgptQuestionContent;
    }

    public void setChatgptQuestionContent(String chatgptQuestionContent) {
        this.chatgptQuestionContent = chatgptQuestionContent;
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

    public Set<ChatgptConsult> getChatgptConsultSet() {
        return chatgptConsultSet;
    }

    public void setChatgptConsultSet(Set<ChatgptConsult> chatgptConsultSet) {
        this.chatgptConsultSet = chatgptConsultSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chatgptQuestionId != null ? chatgptQuestionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ChatgptQuestion)) {
            return false;
        }
        ChatgptQuestion other = (ChatgptQuestion) object;
        if ((this.chatgptQuestionId == null && other.chatgptQuestionId != null) || (this.chatgptQuestionId != null && !this.chatgptQuestionId.equals(other.chatgptQuestionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.ChatgptQuestion[ chatgptQuestionId=" + chatgptQuestionId + " ]";
    }
    
}
