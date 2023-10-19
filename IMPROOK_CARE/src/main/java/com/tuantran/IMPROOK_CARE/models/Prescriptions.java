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
import jakarta.persistence.Lob;
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
@Table(name = "prescriptions")
@NamedQueries({
    @NamedQuery(name = "Prescriptions.findAll", query = "SELECT p FROM Prescriptions p"),
    @NamedQuery(name = "Prescriptions.findByPrescriptionId", query = "SELECT p FROM Prescriptions p WHERE p.prescriptionId = :prescriptionId"),
    @NamedQuery(name = "Prescriptions.findByPrescriptionDate", query = "SELECT p FROM Prescriptions p WHERE p.prescriptionDate = :prescriptionDate"),
    @NamedQuery(name = "Prescriptions.findByCreatedDate", query = "SELECT p FROM Prescriptions p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "Prescriptions.findByUpdatedDate", query = "SELECT p FROM Prescriptions p WHERE p.updatedDate = :updatedDate"),
    @NamedQuery(name = "Prescriptions.findByDeletedDate", query = "SELECT p FROM Prescriptions p WHERE p.deletedDate = :deletedDate"),
    @NamedQuery(name = "Prescriptions.findByActive", query = "SELECT p FROM Prescriptions p WHERE p.active = :active"),
    @NamedQuery(name = "Prescriptions.findByMedicinepaymentTxnRef", query = "SELECT p FROM Prescriptions p WHERE p.medicinepaymentTxnRef = :medicinepaymentTxnRef"),
    @NamedQuery(name = "Prescriptions.findByServicepaymentTxnRef", query = "SELECT p FROM Prescriptions p WHERE p.servicepaymentTxnRef = :servicepaymentTxnRef")})
public class Prescriptions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prescription_id")
    private Integer prescriptionId;
    @Column(name = "prescription_date")
    @Temporal(TemporalType.DATE)
    private Date prescriptionDate;
    @Lob
    @Column(name = "diagnosis")
    private String diagnosis;
    @Lob
    @Column(name = "symptoms")
    private String symptoms;
    @Lob
    @Column(name = "service_price")
    private String servicePrice;
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
    @Column(name = "medicine_payment_Txn_Ref")
    private String medicinepaymentTxnRef;
    @Column(name = "service_payment_Txn_Ref")
    private String servicepaymentTxnRef;
    @JoinColumn(name = "booking_id", referencedColumnName = "booking_id")
    @ManyToOne
    private Booking bookingId;
    @JoinColumn(name = "medicine_payment_status_id", referencedColumnName = "medicine_payment_status_id")
    @ManyToOne
    private MedicinePaymentStatus medicinePaymentStatusId;
    @JoinColumn(name = "service_payment_status_id", referencedColumnName = "service_payment_status_id")
    @ManyToOne
    private ServicePaymentStatus servicePaymentStatusId;
    @JsonIgnore
    @OneToMany(mappedBy = "prescriptionId")
    private Set<PrescriptionDetail> prescriptionDetailSet;

    public Prescriptions() {
    }

    public Prescriptions(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
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

    public String getMedicinepaymentTxnRef() {
        return medicinepaymentTxnRef;
    }

    public void setMedicinepaymentTxnRef(String medicinepaymentTxnRef) {
        this.medicinepaymentTxnRef = medicinepaymentTxnRef;
    }

    public String getServicepaymentTxnRef() {
        return servicepaymentTxnRef;
    }

    public void setServicepaymentTxnRef(String servicepaymentTxnRef) {
        this.servicepaymentTxnRef = servicepaymentTxnRef;
    }

    public Booking getBookingId() {
        return bookingId;
    }

    public void setBookingId(Booking bookingId) {
        this.bookingId = bookingId;
    }

    public MedicinePaymentStatus getMedicinePaymentStatusId() {
        return medicinePaymentStatusId;
    }

    public void setMedicinePaymentStatusId(MedicinePaymentStatus medicinePaymentStatusId) {
        this.medicinePaymentStatusId = medicinePaymentStatusId;
    }

    public ServicePaymentStatus getServicePaymentStatusId() {
        return servicePaymentStatusId;
    }

    public void setServicePaymentStatusId(ServicePaymentStatus servicePaymentStatusId) {
        this.servicePaymentStatusId = servicePaymentStatusId;
    }

    public Set<PrescriptionDetail> getPrescriptionDetailSet() {
        return prescriptionDetailSet;
    }

    public void setPrescriptionDetailSet(Set<PrescriptionDetail> prescriptionDetailSet) {
        this.prescriptionDetailSet = prescriptionDetailSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prescriptionId != null ? prescriptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prescriptions)) {
            return false;
        }
        Prescriptions other = (Prescriptions) object;
        if ((this.prescriptionId == null && other.prescriptionId != null) || (this.prescriptionId != null && !this.prescriptionId.equals(other.prescriptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.Prescriptions[ prescriptionId=" + prescriptionId + " ]";
    }

}
