/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.dto.AddProfilePatientDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateProfilePatientDTO;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.ProfilePatientRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.ProfilePatientService;
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
public class ProfilePatientServiceImpl implements ProfilePatientService {

    @Autowired
    private ProfilePatientRepository profilePatientRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public int addProfilePatient(AddProfilePatientDTO addProfilePatientDTO) {
        try {
            ProfilePatient profilePatient = new ProfilePatient();

            profilePatient.setName(addProfilePatientDTO.getName());
            profilePatient.setPhonenumber(addProfilePatientDTO.getPhonenumber());
            profilePatient.setProvinceName(addProfilePatientDTO.getProvinceName());
            profilePatient.setDistrictName(addProfilePatientDTO.getDistrictName());
            profilePatient.setWardName(addProfilePatientDTO.getWardName());
            profilePatient.setPersonalAddress(addProfilePatientDTO.getPersionalAddress());
            profilePatient.setAddress(
                    addProfilePatientDTO.getPersionalAddress()
                    + " " + addProfilePatientDTO.getWardName()
                    + " " + addProfilePatientDTO.getDistrictName()
                    + " " + addProfilePatientDTO.getProvinceName()
            );

            profilePatient.setEmail(addProfilePatientDTO.getEmail());
            profilePatient.setRelationship(addProfilePatientDTO.getRelationship());

//            Optional<User> userOptional = this.userRepository.findById(Integer.parseInt(addProfilePatientDTO.getUserId()));
            Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(Integer.parseInt(addProfilePatientDTO.getUserId()));
            if (userOptional.isPresent()) {
                profilePatient.setUserId(userOptional.get());
            }

            profilePatient.setActive(Boolean.TRUE);
            profilePatient.setCreatedDate(new Date());

            this.profilePatientRepository.save(profilePatient);
            return 1;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return 0;
        }

    }

    @Override
    public int updateProfilePatient(UpdateProfilePatientDTO updateProfilePatientDTO) {
        try {
            Optional<ProfilePatient> profilePatientOptional = this.profilePatientRepository.findProfilePatientByProfilePatientId(Integer.parseInt(updateProfilePatientDTO.getProfilePatientId()));

            if (profilePatientOptional.isPresent()) {

                ProfilePatient profilePatient = profilePatientOptional.get();
                profilePatient.setName(updateProfilePatientDTO.getName());
                profilePatient.setPhonenumber(updateProfilePatientDTO.getPhonenumber());
                profilePatient.setProvinceName(updateProfilePatientDTO.getProvinceName());
                profilePatient.setDistrictName(updateProfilePatientDTO.getDistrictName());
                profilePatient.setWardName(updateProfilePatientDTO.getWardName());
                profilePatient.setPersonalAddress(updateProfilePatientDTO.getPersionalAddress());
                profilePatient.setAddress(
                        updateProfilePatientDTO.getPersionalAddress()
                        + " " + updateProfilePatientDTO.getWardName()
                        + " " + updateProfilePatientDTO.getDistrictName()
                        + " " + updateProfilePatientDTO.getProvinceName()
                );

                profilePatient.setEmail(updateProfilePatientDTO.getEmail());
                profilePatient.setRelationship(updateProfilePatientDTO.getRelationship());

                profilePatient.setActive(Boolean.TRUE);
                profilePatient.setUpdatedDate(new Date());

                this.profilePatientRepository.save(profilePatient);
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
