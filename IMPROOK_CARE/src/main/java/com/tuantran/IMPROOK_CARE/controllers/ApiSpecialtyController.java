/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.dto.AddSpecialtyDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateSpecialtyDTO;
import com.tuantran.IMPROOK_CARE.models.Specialty;
import com.tuantran.IMPROOK_CARE.service.SpecialtyService;

import jakarta.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */

@RestController
@RequestMapping("/api")
public class ApiSpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping("/public/specialty/")
    @CrossOrigin
    public ResponseEntity<List<Specialty>> listSpecialty() {
        return ResponseEntity.ok().body(this.specialtyService.findSpecialtyByActiveTrue());
    }

    @PostMapping("/auth/specialty/add-specialty/")
    @CrossOrigin
    public ResponseEntity<?> createSpecialty(@Valid AddSpecialtyDTO addSpecialtyDTO,
            @RequestPart("avatar") MultipartFile avatar) {

        Specialty specialty = new Specialty();
        specialty.setSpecialtyName(addSpecialtyDTO.getSpecialtyName());
        specialty.setDescription(addSpecialtyDTO.getDescription());
        specialty.setActive(Boolean.TRUE);
        specialty.setCreatedDate(new Date());

        return new ResponseEntity<>(this.specialtyService.addSpecialty(specialty, avatar), HttpStatus.CREATED);
    }

    @PostMapping("/auth/specialty/update-specialty/")
    @CrossOrigin
    public ResponseEntity<?> updateSpecialty(@Valid UpdateSpecialtyDTO updateSpecialtyDTO,
            @RequestPart("avatar") MultipartFile avatar) {
        String message = "Có lỗi xảy ra!";
        Optional<Specialty> specialtyOptional = this.specialtyService
                .findSpecialtyBySpecialtyIdAndActiveTrue(Integer.parseInt(updateSpecialtyDTO.getSpecialtyId()));

        if (specialtyOptional.isPresent()) {
            Specialty specialty = specialtyOptional.get();
            specialty.setSpecialtyName(updateSpecialtyDTO.getSpecialtyName());
            specialty.setDescription(updateSpecialtyDTO.getDescription());
            specialty.setUpdatedDate(new Date());
            return new ResponseEntity<>(this.specialtyService.updateSpecialty(specialty, avatar), HttpStatus.OK);
        } else {
            message = "Specialty[" + updateSpecialtyDTO.getSpecialtyId() + "] không tồn tại!";
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }
}
