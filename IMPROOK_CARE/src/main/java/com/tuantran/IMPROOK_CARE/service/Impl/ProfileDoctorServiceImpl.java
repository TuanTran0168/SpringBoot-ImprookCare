/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.dto.AddProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.Specialty;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.ProfileDoctorRepository;
import com.tuantran.IMPROOK_CARE.repository.SpecialtyRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.ProfileDoctorService;
import java.util.Date;
import java.util.NoSuchElementException;
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
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private ProfileDoctorRepository profileDoctorRepository;

    @Override
    public int addProfileDoctor(AddProfileDoctorDTO addProfileDoctorDTO) {
        try {
            ProfileDoctor profileDoctor = new ProfileDoctor();
            profileDoctor.setName(addProfileDoctorDTO.getName());
            profileDoctor.setPhonenumber(addProfileDoctorDTO.getPhonenumber());
            profileDoctor.setBookingPrice(addProfileDoctorDTO.getBookingPrice());
            profileDoctor.setEmail(addProfileDoctorDTO.getEmail());
            profileDoctor.setPosition(addProfileDoctorDTO.getPosition());

            profileDoctor.setProvinceName(addProfileDoctorDTO.getProvinceName());
            profileDoctor.setDistrictName(addProfileDoctorDTO.getDistrictName());
            profileDoctor.setWardName(addProfileDoctorDTO.getWardName());
            profileDoctor.setWorkPlace(addProfileDoctorDTO.getWorkPlace());

            profileDoctor.setWorkAddress(
                    addProfileDoctorDTO.getWorkPlace()
                    + " " + addProfileDoctorDTO.getWardName()
                    + " " + addProfileDoctorDTO.getDistrictName()
                    + " " + addProfileDoctorDTO.getProvinceName()
            );

            Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(Integer.parseInt(addProfileDoctorDTO.getUserId()));
            if (userOptional.isPresent()) {
                profileDoctor.setUserId(userOptional.get());
            }

            Optional<Specialty> specialtyIdOptional = this.specialtyRepository.findSpecialtyBySpecialtyIdAndActiveTrue(Integer.parseInt(addProfileDoctorDTO.getSpecialtyId()));

            if (specialtyIdOptional.isPresent()) {
                profileDoctor.setSpecialtyId(specialtyIdOptional.get());
            }

            profileDoctor.setActive(Boolean.TRUE);
            profileDoctor.setCreatedDate(new Date());
            this.profileDoctorRepository.save(profileDoctor);
            return 1;
        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return 0;
        } catch (NoSuchElementException ex) {
            return 0;
        }

    }

    @Override
    public int updateProfileDoctor(UpdateProfileDoctorDTO updateProfileDoctorDTO) {
        try {
            Optional<ProfileDoctor> profileDoctorOptional = this.profileDoctorRepository.findProfileDoctorByProfileDoctorIdAndActiveTrue(Integer.parseInt(updateProfileDoctorDTO.getProfileDoctorId()));

            if (profileDoctorOptional.isPresent()) {

                ProfileDoctor profileDoctor = profileDoctorOptional.get();
                profileDoctor.setName(updateProfileDoctorDTO.getName());
                profileDoctor.setPhonenumber(updateProfileDoctorDTO.getPhonenumber());
                profileDoctor.setBookingPrice(updateProfileDoctorDTO.getBookingPrice());
                profileDoctor.setEmail(updateProfileDoctorDTO.getEmail());
                Optional<Specialty> specialtyIdOptional = this.specialtyRepository.findSpecialtyBySpecialtyIdAndActiveTrue(Integer.parseInt(updateProfileDoctorDTO.getSpecialtyId()));
                profileDoctor.setProvinceName(updateProfileDoctorDTO.getProvinceName());
                profileDoctor.setDistrictName(updateProfileDoctorDTO.getDistrictName());
                profileDoctor.setWardName(updateProfileDoctorDTO.getWardName());
                profileDoctor.setWorkPlace(updateProfileDoctorDTO.getWorkPlace());

                profileDoctor.setWorkAddress(
                        updateProfileDoctorDTO.getWorkPlace()
                        + " " + updateProfileDoctorDTO.getWardName()
                        + " " + updateProfileDoctorDTO.getDistrictName()
                        + " " + updateProfileDoctorDTO.getProvinceName()
                );
                if (specialtyIdOptional.isPresent()) {
                    profileDoctor.setSpecialtyId(specialtyIdOptional.get());
                }

                profileDoctor.setActive(Boolean.TRUE);
                profileDoctor.setUpdatedDate(new Date());

                this.profileDoctorRepository.save(profileDoctor);
                return 1;
            } else {
                return 0;
            }

        } catch (DataAccessException ex) {
            ex.printStackTrace();
            return 0;
        } catch (NoSuchElementException ex) {
            return 0;
        }
    }

}
