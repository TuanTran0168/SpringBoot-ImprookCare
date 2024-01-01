/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.Specifications.GenericSpecifications;
import com.tuantran.IMPROOK_CARE.dto.AddProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateProfileDoctorDTO;
import com.tuantran.IMPROOK_CARE.models.Comment;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.Role;
import com.tuantran.IMPROOK_CARE.models.Schedule;
import com.tuantran.IMPROOK_CARE.models.Specialty;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.CommentRepository;
import com.tuantran.IMPROOK_CARE.repository.ProfileDoctorRepository;
import com.tuantran.IMPROOK_CARE.repository.ScheduleRepository;
import com.tuantran.IMPROOK_CARE.repository.SpecialtyRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.CommentService;
import com.tuantran.IMPROOK_CARE.service.ProfileDoctorService;
import com.tuantran.IMPROOK_CARE.service.ScheduleService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private Environment environment;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private CommentService commentService;

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

    @Override
    public List<ProfileDoctor> findAllProfileDoctorByActiveTrue() {
        return this.profileDoctorRepository.findAllProfileDoctorByActiveTrue();
    }

    @Override
    public ProfileDoctor findProfileDoctorByProfileDoctorIdAndActiveTrue(int profileDoctorId) {
        try {
            return this.profileDoctorRepository.findProfileDoctorByProfileDoctorIdAndActiveTrue(profileDoctorId).get();
        } catch (NoSuchElementException ex) {
            return null;
        }

    }

    @Override
    public List<ProfileDoctor> findProfileDoctorByUserIdAndActiveTrue(int userId) {
        try {
            return this.profileDoctorRepository.findProfileDoctorByUserIdAndActiveTrue(this.userRepository.findUserByUserIdAndActiveTrue(userId).get());
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

//    @Cacheable(value = "findAllProfileDoctorPageSpec")
    @Override
    public Page<ProfileDoctor> findAllProfileDoctorPageSpec(Map<String, String> params) {
        String pageNumber = params.get("pageNumber");
        String name = params.get("name");
        String phonenumber = params.get("phonenumber");
        String fromPrice = params.get("fromPrice");
        String toPrice = params.get("toPrice");

        List<Specification<ProfileDoctor>> listSpec = new ArrayList<>();
        int defaultPageNumber = 0;
        Sort mySort = Sort.by("createdDate").descending();
        Pageable page = PageRequest.of(defaultPageNumber, Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);

        if (pageNumber != null && !pageNumber.isEmpty()) {
            page = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
        }

        if (name != null && !name.isEmpty()) {
            Specification<ProfileDoctor> spec = GenericSpecifications.fieldContains("name", name);
            listSpec.add(spec);
        }

        if (phonenumber != null && !phonenumber.isEmpty()) {
            Specification<ProfileDoctor> spec = GenericSpecifications.fieldContains("phonenumber", phonenumber);
            listSpec.add(spec);
        }

        if (fromPrice != null && !fromPrice.isEmpty()) {
            Specification<ProfileDoctor> spec = GenericSpecifications.greaterThan("bookingPrice", fromPrice);
            listSpec.add(spec);
        }

        if (toPrice != null && !toPrice.isEmpty()) {
            Specification<ProfileDoctor> spec = GenericSpecifications.lessThan("bookingPrice", toPrice);
            listSpec.add(spec);
        }

        Specification<ProfileDoctor> spec = GenericSpecifications.fieldEquals("active", Boolean.TRUE);
        listSpec.add(spec);

        return this.profileDoctorRepository.findAll(GenericSpecifications.createSpecification(listSpec), page);
    }

    @Override
    @Transactional
    public int softDeleteProfileDoctor(int profileDoctorId) {
        Optional<ProfileDoctor> profileDoctorOptional = this.profileDoctorRepository.findProfileDoctorByProfileDoctorIdAndActiveTrue(profileDoctorId);
        if (profileDoctorOptional.isPresent()) {
            ProfileDoctor profileDoctor = profileDoctorOptional.get();
            if (profileDoctor.getActive().equals(Boolean.TRUE)) {
                profileDoctor.setActive(Boolean.FALSE);

                List<Schedule> listSchedule = this.scheduleRepository.findScheduleByProfileDoctorIdAndActiveTrue(profileDoctor);

                for (Schedule schedule : listSchedule) {
                    this.scheduleService.softDeleteSchedule(schedule.getScheduleId());
                }

                List<Comment> listComment = this.commentRepository.findCommentByProfileDoctorIdAndActiveTrue(profileDoctor);

                for (Comment comment : listComment) {
                    this.commentService.softDeleteComment(comment.getCommentId());
                }

                return 1;
            } else {
                System.out.println("ProfileDoctor: " + profileDoctor.getActive());
                return 2;
            }
        } else {
            return 3; // Không tìm được để xóa
        }
    }

}
