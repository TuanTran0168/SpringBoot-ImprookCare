/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.models.Specialty;
import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
public interface SpecialtyService {

    List<Specialty> findSpecialtyByActiveTrue();

    Optional<Specialty> findSpecialtyBySpecialtyIdAndActiveTrue(int specialtyId);

    Specialty addSpecialty(Specialty specialty, MultipartFile avatar);

    Specialty updateSpecialty(Specialty specialty, MultipartFile avatar);
}
