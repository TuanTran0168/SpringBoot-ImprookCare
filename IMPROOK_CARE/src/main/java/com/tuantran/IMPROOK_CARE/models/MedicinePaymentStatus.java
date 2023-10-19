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
@Table(name = "medicine_payment_status")
@NamedQueries({
    @NamedQuery(name = "MedicinePaymentStatus.findAll", query = "SELECT m FROM MedicinePaymentStatus m"),
    @NamedQuery(name = "MedicinePaymentStatus.findByMedicinePaymentStatusId", query = "SELECT m FROM MedicinePaymentStatus m WHERE m.medicinePaymentStatusId = :medicinePaymentStatusId"),
    @NamedQuery(name = "MedicinePaymentStatus.findByMedicinePaymentStatusValue", query = "SELECT m FROM MedicinePaymentStatus m WHERE m.medicinePaymentStatusValue = :medicinePaymentStatusValue"),
    @NamedQuery(name = "MedicinePaymentStatus.findByCreatedDate", query = "SELECT m FROM MedicinePaymentStatus m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "MedicinePaymentStatus.findByUpdatedDate", query = "SELECT m FROM MedicinePaymentStatus m WHERE m.updatedDate = :updatedDate"),
    @NamedQuery(name = "MedicinePaymentStatus.findByDeletedDate", query = "SELECT m FROM MedicinePaymentStatus m WHERE m.deletedDate = :deletedDate"),
    @NamedQuery(name = "MedicinePaymentStatus.findByActive", query = "SELECT m FROM MedicinePaymentStatus m WHERE m.active = :active")})
public class MedicinePaymentStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "medicine_payment_status_id")
    private Integer medicinePaymentStatusId;
    @Column(name = "medicine_payment_status_value")
    private String medicinePaymentStatusValue;
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
    @OneToMany(mappedBy = "medicinePaymentStatusId")
    private Set<Prescriptions> prescriptionsSet;

    public MedicinePaymentStatus() {
    }

    public MedicinePaymentStatus(Integer medicinePaymentStatusId) {
        this.medicinePaymentStatusId = medicinePaymentStatusId;
    }

    public Integer getMedicinePaymentStatusId() {
        return medicinePaymentStatusId;
    }

    public void setMedicinePaymentStatusId(Integer medicinePaymentStatusId) {
        this.medicinePaymentStatusId = medicinePaymentStatusId;
    }

    public String getMedicinePaymentStatusValue() {
        return medicinePaymentStatusValue;
    }

    public void setMedicinePaymentStatusValue(String medicinePaymentStatusValue) {
        this.medicinePaymentStatusValue = medicinePaymentStatusValue;
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

    public Set<Prescriptions> getPrescriptionsSet() {
        return prescriptionsSet;
    }

    public void setPrescriptionsSet(Set<Prescriptions> prescriptionsSet) {
        this.prescriptionsSet = prescriptionsSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (medicinePaymentStatusId != null ? medicinePaymentStatusId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MedicinePaymentStatus)) {
            return false;
        }
        MedicinePaymentStatus other = (MedicinePaymentStatus) object;
        if ((this.medicinePaymentStatusId == null && other.medicinePaymentStatusId != null) || (this.medicinePaymentStatusId != null && !this.medicinePaymentStatusId.equals(other.medicinePaymentStatusId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.MedicinePaymentStatus[ medicinePaymentStatusId=" + medicinePaymentStatusId + " ]";
    }

}
