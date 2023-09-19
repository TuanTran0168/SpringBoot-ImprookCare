/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.models;

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
    @NamedQuery(name = "Prescriptions.findByActive", query = "SELECT p FROM Prescriptions p WHERE p.active = :active")})
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
    @Column(name = "active")
    private Boolean active;
    @JoinColumn(name = "booking_id", referencedColumnName = "booking_id")
    @ManyToOne
    private Booking bookingId;
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
