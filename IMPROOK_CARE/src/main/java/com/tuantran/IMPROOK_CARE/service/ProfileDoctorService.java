/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.dto.AddProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.User;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface ProfileDoctorService {

    int addProfileDoctor(AddProfileDoctorDTO addProfileDoctorDTO);

    int updateProfileDoctor(UpdateProfileDoctorDTO updateProfileDoctorDTO);

    List<ProfileDoctor> findAllProfileDoctorByActiveTrue();

    ProfileDoctor findProfileDoctorByProfileDoctorIdAndActiveTrue(int profileDoctorId);

    List<ProfileDoctor> findProfilePatientByUserIdAndActiveTrue(int userId);
}
