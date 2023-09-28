/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.Specialty;
import com.tuantran.IMPROOK_CARE.repository.SpecialtyRepository;
import com.tuantran.IMPROOK_CARE.service.SpecialtyService;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Override
    public List<Specialty> findSpecialtyByActiveTrue() {
        return this.specialtyRepository.findSpecialtyByActiveTrue();
    }

    @Override
    public Specialty findSpecialtyBySpecialtyIdAndActiveTrue(int specialtyId) {
        try {
            return this.specialtyRepository.findSpecialtyBySpecialtyIdAndActiveTrue(specialtyId).get();
        } catch (NoSuchElementException ex) {
            return null;
        }

    }

}
