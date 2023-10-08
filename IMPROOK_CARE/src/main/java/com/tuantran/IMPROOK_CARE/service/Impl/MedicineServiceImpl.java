/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.components.cloudinary.CloudinaryComponent;
import com.tuantran.IMPROOK_CARE.dto.AddMedicineDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateMedicineDTO;
import com.tuantran.IMPROOK_CARE.models.Medicine;
import com.tuantran.IMPROOK_CARE.models.MedicineCategory;
import com.tuantran.IMPROOK_CARE.repository.MedicineCategoryRepository;
import com.tuantran.IMPROOK_CARE.repository.MedicineRepository;
import com.tuantran.IMPROOK_CARE.service.MedicineService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
@Repository
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private MedicineCategoryRepository medicineCategoryRepository;

    @Autowired
    private CloudinaryComponent cloudinaryComponent;

    @Override
    public List<Medicine> findMedicineByActiveTrue() {
        return this.medicineRepository.findMedicineByActiveTrue();
    }

    @Override
    public Medicine findMedicineByMedicineIdAndActiveTrue(int medicineId) {

        try {
            Optional<Medicine> medicineOptional = this.medicineRepository.findMedicineByMedicineIdAndActiveTrue(medicineId);
            if (medicineOptional.isPresent()) {
                return medicineOptional.get();
            } else {
                return null;
            }
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Medicine> findMedicineByCategoryId(int medicineCategoryId) {
        try {
            Optional<MedicineCategory> medicineCategoryOptional = this.medicineCategoryRepository.findMedicineCategoryByCategoryIdAndActiveTrue(medicineCategoryId);
            if (medicineCategoryOptional.isPresent()) {
                return this.medicineRepository.findMedicineByCategoryId(medicineCategoryOptional.get());
            } else {
                return new ArrayList<>();
            }
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }

    }

    @Override
    public int addMedicine(AddMedicineDTO addMedicineDTO, MultipartFile avatar) {
        try {
            Medicine medicine = new Medicine();

            medicine.setMedicineName(addMedicineDTO.getMedicineName());
            medicine.setDescription(addMedicineDTO.getDescription());
            medicine.setIngredients(addMedicineDTO.getIngredients());
            medicine.setDosage(addMedicineDTO.getDosage());
            medicine.setUnitPrice(BigDecimal.valueOf(Double.parseDouble(addMedicineDTO.getUnitPrice())));
            if (avatar != null && !avatar.isEmpty()) {
                try {
                    String linkCloudinaryAvatar = cloudinaryComponent.Cloudinary(avatar).get("secure_url").toString();
                    medicine.setAvatar(linkCloudinaryAvatar);
                } catch (Exception ex) {
                    Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            Optional<MedicineCategory> medicineCategoryOptional = this.medicineCategoryRepository.findMedicineCategoryByCategoryIdAndActiveTrue(Integer.parseInt(addMedicineDTO.getMedicineCategoryId()));

            if (medicineCategoryOptional.isPresent()) {
                medicine.setCategoryId(medicineCategoryOptional.get());
            } else {
                return 0;
            }

            medicine.setCreatedDate(new Date());
            medicine.setActive(Boolean.TRUE);
            this.medicineRepository.save(medicine);
            return 1;
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return 0;
        } catch (DataAccessException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateMedicine(UpdateMedicineDTO updateMedicineDTO, MultipartFile avatar) {
        try {
            Optional<Medicine> medicineOptional = this.medicineRepository.findMedicineByMedicineIdAndActiveTrue(Integer.parseInt(updateMedicineDTO.getMedicineId()));

            if (medicineOptional.isPresent()) {
                Medicine medicine = medicineOptional.get();

                medicine.setMedicineName(updateMedicineDTO.getMedicineName());
                medicine.setDescription(updateMedicineDTO.getDescription());
                medicine.setIngredients(updateMedicineDTO.getIngredients());
                medicine.setDosage(updateMedicineDTO.getDosage());
                medicine.setUnitPrice(BigDecimal.valueOf(Double.parseDouble(updateMedicineDTO.getUnitPrice())));
                if (avatar != null && !avatar.isEmpty()) {
                    try {
                        String linkCloudinaryAvatar = cloudinaryComponent.Cloudinary(avatar).get("secure_url").toString();
                        medicine.setAvatar(linkCloudinaryAvatar);
                    } catch (Exception ex) {
                        Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                Optional<MedicineCategory> medicineCategoryOptional = this.medicineCategoryRepository.findMedicineCategoryByCategoryIdAndActiveTrue(Integer.parseInt(updateMedicineDTO.getMedicineCategoryId()));

                if (medicineCategoryOptional.isPresent()) {
                    medicine.setCategoryId(medicineCategoryOptional.get());
                } else {
                    return 0;
                }

                medicine.setUpdatedDate(new Date());
                medicine.setActive(Boolean.TRUE);
                this.medicineRepository.save(medicine);
                return 1;
            } else {
                return 2; // Không tìm thấy để update
            }

        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
            return 0;
        } catch (DataAccessException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

}
