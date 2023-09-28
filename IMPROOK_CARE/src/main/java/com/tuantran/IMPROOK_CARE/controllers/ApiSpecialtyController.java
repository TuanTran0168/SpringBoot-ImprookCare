/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.controllers;

import com.tuantran.IMPROOK_CARE.models.Specialty;
import com.tuantran.IMPROOK_CARE.service.SpecialtyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
