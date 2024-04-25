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
@Table(name = "payment_history")
@NamedQueries({
        @NamedQuery(name = "PaymentHistory.findAll", query = "SELECT p FROM PaymentHistory p"),
        @NamedQuery(name = "PaymentHistory.findByPaymentHistoryId", query = "SELECT p FROM PaymentHistory p WHERE p.paymentHistoryId = :paymentHistoryId"),
        @NamedQuery(name = "PaymentHistory.findByVnpResponseid", query = "SELECT p FROM PaymentHistory p WHERE p.vnpResponseid = :vnpResponseid"),
        @NamedQuery(name = "PaymentHistory.findByVnpCommand", query = "SELECT p FROM PaymentHistory p WHERE p.vnpCommand = :vnpCommand"),
        @NamedQuery(name = "PaymentHistory.findByVnpResponsecode", query = "SELECT p FROM PaymentHistory p WHERE p.vnpResponsecode = :vnpResponsecode"),
        @NamedQuery(name = "PaymentHistory.findByVnpMessage", query = "SELECT p FROM PaymentHistory p WHERE p.vnpMessage = :vnpMessage"),
        @NamedQuery(name = "PaymentHistory.findByVnpTmncode", query = "SELECT p FROM PaymentHistory p WHERE p.vnpTmncode = :vnpTmncode"),
        @NamedQuery(name = "PaymentHistory.findByVnpTxnref", query = "SELECT p FROM PaymentHistory p WHERE p.vnpTxnref = :vnpTxnref"),
        @NamedQuery(name = "PaymentHistory.findByVnpAmount", query = "SELECT p FROM PaymentHistory p WHERE p.vnpAmount = :vnpAmount"),
        @NamedQuery(name = "PaymentHistory.findByVnpOrderinfo", query = "SELECT p FROM PaymentHistory p WHERE p.vnpOrderinfo = :vnpOrderinfo"),
        @NamedQuery(name = "PaymentHistory.findByVnpBankcode", query = "SELECT p FROM PaymentHistory p WHERE p.vnpBankcode = :vnpBankcode"),
        @NamedQuery(name = "PaymentHistory.findByVnpPaydate", query = "SELECT p FROM PaymentHistory p WHERE p.vnpPaydate = :vnpPaydate"),
        @NamedQuery(name = "PaymentHistory.findByVnpTransactionno", query = "SELECT p FROM PaymentHistory p WHERE p.vnpTransactionno = :vnpTransactionno"),
        @NamedQuery(name = "PaymentHistory.findByVnpTransactiontype", query = "SELECT p FROM PaymentHistory p WHERE p.vnpTransactiontype = :vnpTransactiontype"),
        @NamedQuery(name = "PaymentHistory.findByVnpTransactionstatus", query = "SELECT p FROM PaymentHistory p WHERE p.vnpTransactionstatus = :vnpTransactionstatus"),
        @NamedQuery(name = "PaymentHistory.findByVnpSecurehash", query = "SELECT p FROM PaymentHistory p WHERE p.vnpSecurehash = :vnpSecurehash"),
        @NamedQuery(name = "PaymentHistory.findByCreatedDate", query = "SELECT p FROM PaymentHistory p WHERE p.createdDate = :createdDate"),
        @NamedQuery(name = "PaymentHistory.findByUpdatedDate", query = "SELECT p FROM PaymentHistory p WHERE p.updatedDate = :updatedDate"),
        @NamedQuery(name = "PaymentHistory.findByDeletedDate", query = "SELECT p FROM PaymentHistory p WHERE p.deletedDate = :deletedDate"),
        @NamedQuery(name = "PaymentHistory.findByActive", query = "SELECT p FROM PaymentHistory p WHERE p.active = :active") })
public class PaymentHistory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "payment_history_id")
    private Integer paymentHistoryId;
    @Column(name = "vnp_responseid")
    private String vnpResponseid;
    @Column(name = "vnp_command")
    private String vnpCommand;
    @Column(name = "vnp_responsecode")
    private String vnpResponsecode;
    @Column(name = "vnp_message")
    private String vnpMessage;
    @Column(name = "vnp_tmncode")
    private String vnpTmncode;
    @Column(name = "vnp_txnref")
    private String vnpTxnref;
    @Column(name = "vnp_amount")
    private String vnpAmount;
    @Column(name = "vnp_orderinfo")
    private String vnpOrderinfo;
    @Column(name = "vnp_bankcode")
    private String vnpBankcode;
    @Column(name = "vnp_paydate")
    private String vnpPaydate;
    @Column(name = "vnp_transactionno")
    private String vnpTransactionno;
    @Column(name = "vnp_transactiontype")
    private String vnpTransactiontype;
    @Column(name = "vnp_transactionstatus")
    private String vnpTransactionstatus;
    @Column(name = "vnp_securehash")
    private String vnpSecurehash;
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
    @JoinColumn(name = "booking_id", referencedColumnName = "booking_id")
    @ManyToOne
    private Booking bookingId;

    public PaymentHistory() {
    }

    public PaymentHistory(Integer paymentHistoryId) {
        this.paymentHistoryId = paymentHistoryId;
    }

    public Integer getPaymentHistoryId() {
        return paymentHistoryId;
    }

    public void setPaymentHistoryId(Integer paymentHistoryId) {
        this.paymentHistoryId = paymentHistoryId;
    }

    public String getVnpResponseid() {
        return vnpResponseid;
    }

    public void setVnpResponseid(String vnpResponseid) {
        this.vnpResponseid = vnpResponseid;
    }

    public String getVnpCommand() {
        return vnpCommand;
    }

    public void setVnpCommand(String vnpCommand) {
        this.vnpCommand = vnpCommand;
    }

    public String getVnpResponsecode() {
        return vnpResponsecode;
    }

    public void setVnpResponsecode(String vnpResponsecode) {
        this.vnpResponsecode = vnpResponsecode;
    }

    public String getVnpMessage() {
        return vnpMessage;
    }

    public void setVnpMessage(String vnpMessage) {
        this.vnpMessage = vnpMessage;
    }

    public String getVnpTmncode() {
        return vnpTmncode;
    }

    public void setVnpTmncode(String vnpTmncode) {
        this.vnpTmncode = vnpTmncode;
    }

    public String getVnpTxnref() {
        return vnpTxnref;
    }

    public void setVnpTxnref(String vnpTxnref) {
        this.vnpTxnref = vnpTxnref;
    }

    public String getVnpAmount() {
        return vnpAmount;
    }

    public void setVnpAmount(String vnpAmount) {
        this.vnpAmount = vnpAmount;
    }

    public String getVnpOrderinfo() {
        return vnpOrderinfo;
    }

    public void setVnpOrderinfo(String vnpOrderinfo) {
        this.vnpOrderinfo = vnpOrderinfo;
    }

    public String getVnpBankcode() {
        return vnpBankcode;
    }

    public void setVnpBankcode(String vnpBankcode) {
        this.vnpBankcode = vnpBankcode;
    }

    public String getVnpPaydate() {
        return vnpPaydate;
    }

    public void setVnpPaydate(String vnpPaydate) {
        this.vnpPaydate = vnpPaydate;
    }

    public String getVnpTransactionno() {
        return vnpTransactionno;
    }

    public void setVnpTransactionno(String vnpTransactionno) {
        this.vnpTransactionno = vnpTransactionno;
    }

    public String getVnpTransactiontype() {
        return vnpTransactiontype;
    }

    public void setVnpTransactiontype(String vnpTransactiontype) {
        this.vnpTransactiontype = vnpTransactiontype;
    }

    public String getVnpTransactionstatus() {
        return vnpTransactionstatus;
    }

    public void setVnpTransactionstatus(String vnpTransactionstatus) {
        this.vnpTransactionstatus = vnpTransactionstatus;
    }

    public String getVnpSecurehash() {
        return vnpSecurehash;
    }

    public void setVnpSecurehash(String vnpSecurehash) {
        this.vnpSecurehash = vnpSecurehash;
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

    public Booking getBookingId() {
        return bookingId;
    }

    public void setBookingId(Booking bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentHistoryId != null ? paymentHistoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentHistory)) {
            return false;
        }
        PaymentHistory other = (PaymentHistory) object;
        if ((this.paymentHistoryId == null && other.paymentHistoryId != null)
                || (this.paymentHistoryId != null && !this.paymentHistoryId.equals(other.paymentHistoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.PaymentHistory[ paymentHistoryId=" + paymentHistoryId + " ]";
    }

}
