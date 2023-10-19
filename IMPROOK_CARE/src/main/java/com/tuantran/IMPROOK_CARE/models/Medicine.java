/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "medicine")
@NamedQueries({
    @NamedQuery(name = "Medicine.findAll", query = "SELECT m FROM Medicine m"),
    @NamedQuery(name = "Medicine.findByMedicineId", query = "SELECT m FROM Medicine m WHERE m.medicineId = :medicineId"),
    @NamedQuery(name = "Medicine.findByMedicineName", query = "SELECT m FROM Medicine m WHERE m.medicineName = :medicineName"),
    @NamedQuery(name = "Medicine.findByAvatar", query = "SELECT m FROM Medicine m WHERE m.avatar = :avatar"),
    @NamedQuery(name = "Medicine.findByUnitPrice", query = "SELECT m FROM Medicine m WHERE m.unitPrice = :unitPrice"),
    @NamedQuery(name = "Medicine.findByCreatedDate", query = "SELECT m FROM Medicine m WHERE m.createdDate = :createdDate"),
    @NamedQuery(name = "Medicine.findByUpdatedDate", query = "SELECT m FROM Medicine m WHERE m.updatedDate = :updatedDate"),
    @NamedQuery(name = "Medicine.findByDeletedDate", query = "SELECT m FROM Medicine m WHERE m.deletedDate = :deletedDate"),
    @NamedQuery(name = "Medicine.findByActive", query = "SELECT m FROM Medicine m WHERE m.active = :active")})
public class Medicine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "medicine_id")
    private Integer medicineId;
    @Column(name = "medicine_name")
    private String medicineName;
    @Lob
    @Column(name = "description")
    private String description;
    @Lob
    @Column(name = "ingredients")
    private String ingredients;
    @Lob
    @Column(name = "dosage")
    private String dosage;
    @Column(name = "avatar")
    private String avatar;
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
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    @ManyToOne
    private MedicineCategory categoryId;
    @JsonIgnore
    @OneToMany(mappedBy = "medicineId")
    private Set<PrescriptionDetail> prescriptionDetailSet;

    public Medicine() {
    }

    public Medicine(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public MedicineCategory getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(MedicineCategory categoryId) {
        this.categoryId = categoryId;
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
        hash += (medicineId != null ? medicineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Medicine)) {
            return false;
        }
        Medicine other = (Medicine) object;
        if ((this.medicineId == null && other.medicineId != null) || (this.medicineId != null && !this.medicineId.equals(other.medicineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tuantran.IMPROOK_CARE.models.Medicine[ medicineId=" + medicineId + " ]";
    }

}
