/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.models;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "prescription_detail")
@NamedQueries({
    @NamedQuery(name = "PrescriptionDetail.findAll", query = "SELECT p FROM PrescriptionDetail p"),
    @NamedQuery(name = "PrescriptionDetail.findByPrescriptionDetailId", query = "SELECT p FROM PrescriptionDetail p WHERE p.prescriptionDetailId = :prescriptionDetailId"),
    @NamedQuery(name = "PrescriptionDetail.findByMedicineName", query = "SELECT p FROM PrescriptionDetail p WHERE p.medicineName = :medicineName"),
    @NamedQuery(name = "PrescriptionDetail.findByQuantity", query = "SELECT p FROM PrescriptionDetail p WHERE p.quantity = :quantity"),
    @NamedQuery(name = "PrescriptionDetail.findByUnitPrice", query = "SELECT p FROM PrescriptionDetail p WHERE p.unitPrice = :unitPrice"),
    @NamedQuery(name = "PrescriptionDetail.findByCreatedDate", query = "SELECT p FROM PrescriptionDetail p WHERE p.createdDate = :createdDate"),
    @NamedQuery(name = "PrescriptionDetail.findByUpdatedDate", query = "SELECT p FROM PrescriptionDetail p WHERE p.updatedDate = :updatedDate"),
    @NamedQuery(name = "PrescriptionDetail.findByDeletedDate", query = "SELECT p FROM PrescriptionDetail p WHERE p.deletedDate = :deletedDate"),
    @NamedQuery(name = "PrescriptionDetail.findByActive", query = "SELECT p FROM PrescriptionDetail p WHERE p.active = :active")})
public class PrescriptionDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "prescription_detail_id")
    private Integer prescriptionDetailId;
    @Column(name = "medicine_name")
    private String medicineName;
    @Lob
    @Column(name = "usage_instruction")
    private String usageInstruction;
    @Column(name = "quantity")
    private Integer quantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "unit_price")
    private BigDecimal unitPrice;
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
    @JoinColumn(name = "medicine_id", referencedColumnName = "medicine_id")
    @ManyToOne
    private Medicine medicineId;
    @JoinColumn(name = "prescription_id", referencedColumnName = "prescription_id")
    @ManyToOne
    private Prescriptions prescriptionId;

    public PrescriptionDetail() {
    }

    public PrescriptionDetail(Integer prescriptionDetailId) {
        this.prescriptionDetailId = prescriptionDetailId;
    }

    public Integer getPrescriptionDetailId() {
        return prescriptionDetailId;
    }

    public void setPrescriptionDetailId(Integer prescriptionDetailId) {
        this.prescriptionDetailId = prescriptionDetailId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getUsageInstruction() {
        return usageInstruction;
    }

    public void setUsageInstruction(String usageInstruction) {
        this.usageInstruction = usageInstruction;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
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

    public Medicine getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Medicine medicineId) {
        this.medicineId = medicineId;
    }

    public Prescriptions getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Prescriptions prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prescriptionDetailId != null ? prescriptionDetailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrescriptionDetail)) {
            return false;
        }
        PrescriptionDetail other = (PrescriptionDetail) object;
        if ((this.prescriptionDetailId == null && other.prescriptionDetailId != null) || (this.prescriptionDetailId != null && !this.prescriptionDetailId.equals(other.prescriptionDetailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.PrescriptionDetail[ prescriptionDetailId=" + prescriptionDetailId + " ]";
    }
    
}
