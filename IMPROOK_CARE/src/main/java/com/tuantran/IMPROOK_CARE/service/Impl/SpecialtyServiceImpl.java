/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.Specialty;
import com.tuantran.IMPROOK_CARE.repository.SpecialtyRepository;
import com.tuantran.IMPROOK_CARE.service.SpecialtyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Administrator
 */
public class SpecialtyServiceImpl implements SpecialtyService{

    @Autowired
    private SpecialtyRepository specialtyRepository;
    
    @Override
    public List<Specialty> findSpecialtyByActiveTrue() {
        return this.specialtyRepository.findSpecialtyByActiveTrue();
    }

    @Override
    public Specialty findSpecialtyBySpecialtyIdAndActiveTrue(int specialtyId) {
        return this.specialtyRepository.findSpecialtyBySpecialtyIdAndActiveTrue(specialtyId).get();
    }
    
}
