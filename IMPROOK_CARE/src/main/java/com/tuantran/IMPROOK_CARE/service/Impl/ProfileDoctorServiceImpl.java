/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.dto.AddProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.ProfileDoctorRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.ProfileDoctorService;
import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class ProfileDoctorServiceImpl implements ProfileDoctorService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ProfileDoctorRepository profileDoctorRepository;

    @Override
    public int addProfileDoctor(AddProfileDoctorDTO addProfileDoctorDTO) {
        try {
            ProfileDoctor profileDoctor = new ProfileDoctor();
            profileDoctor.setName(addProfileDoctorDTO.getName());
            profileDoctor.setPhonenumber(addProfileDoctorDTO.getPhonenumber());
            profileDoctor.setEmail(addProfileDoctorDTO.getEmail());
            profileDoctor.setWorkPlace(addProfileDoctorDTO.getWorkPlace());
            profileDoctor.setPosition(addProfileDoctorDTO.getPosition());
            Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(Integer.parseInt(addProfileDoctorDTO.getUserId()));
            if (userOptional.isPresent()) {
                profileDoctor.setUserId(userOptional.get());
            }
            
//            @NotBlank
//            private String specialtyId;
//            Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(Integer.parseInt(addProfileDoctorDTO.getUserId()));
//            if (userOptional.isPresent()) {
//                profileDoctor.setUserId(userOptional.get());
//            }

            profileDoctor.setActive(Boolean.TRUE);
            profileDoctor.setCreatedDate(new Date());
            this.profileDoctorRepository.save(profileDoctor);
            return 1;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return 0;
        }

    }

    @Override
    public int updateProfileDoctor(UpdateProfileDoctorDTO updateProfileDoctorDTO) {
        try {
            Optional<ProfileDoctor> profileDoctorOptional = this.profileDoctorRepository.findProfileDoctorByProfileDoctorId(Integer.parseInt(updateProfileDoctorDTO.getProfileDoctorId()));

            if (profileDoctorOptional.isPresent()) {

                ProfileDoctor profileDoctor = profileDoctorOptional.get();
                profileDoctor.setName(updateProfileDoctorDTO.getName());
                profileDoctor.setPhonenumber(updateProfileDoctorDTO.getPhonenumber());
                profileDoctor.setEmail(updateProfileDoctorDTO.getEmail());

                profileDoctor.setActive(Boolean.TRUE);
//                profilePatient.setCreatedDate(new Date());

                this.profileDoctorRepository.save(profileDoctor);
                return 1;
            } else {
                return 0;
            }

        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

}
