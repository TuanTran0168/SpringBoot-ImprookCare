/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.AddProfilePatientDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateProfilePatientDTO;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface ProfilePatientService {

    int addProfilePatient(AddProfilePatientDTO addProfilePatientDTO);

    int updateProfilePatient(UpdateProfilePatientDTO updateProfilePatientDTO);

    ProfilePatient findProfilePatientByProfilePatientIdAndActiveTrue(int profilePatientId);

    List<ProfilePatient> findProfilePatientByUserIdAndActiveTrue(int userId);
    
    int softDeleteProfilePatient(int profilePatientId);
}
