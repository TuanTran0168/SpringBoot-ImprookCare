/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import com.tuantran.IMPROOK_CARE.models.User;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Administrator
 */
@Repository
@Transactional
public interface ProfilePatientRepository extends JpaRepository<ProfilePatient, Integer> {

    Optional<ProfilePatient> findProfilePatientByProfilePatientIdAndActiveTrue(int profilePatientId);

    List<ProfilePatient> findProfilePatientByUserIdAndActiveTrue(User userId);

    List<?> findProfilePatientByUserIdAndIsLockAndActiveTrue(User userId, Boolean lock);

    Page<?> findAll(Specification<?> createSpecification, Pageable page);
}
