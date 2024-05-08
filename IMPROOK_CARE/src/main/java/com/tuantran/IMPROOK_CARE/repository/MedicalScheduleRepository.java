/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.repository;

import com.tuantran.IMPROOK_CARE.models.MedicalReminder;
import com.tuantran.IMPROOK_CARE.models.MedicalSchedule;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 *
 * @author Administrator
 */
@Repository
@Transactional
public interface MedicalScheduleRepository extends JpaRepository<MedicalSchedule, Integer> {

    Optional<MedicalSchedule> findByMedicalScheduleIdAndActiveTrue(Integer medicalScheduleId);

    List<MedicalSchedule> findByMedicalReminderIdAndActiveTrue(MedicalReminder medicalReminderId);

    Page<?> findAll(Specification<?> createSpecification, Pageable page);

    // Chưa test
    @Query("SELECT ms "
            + "FROM MedicalSchedule ms "
            + "JOIN ms.medicalReminderId mr "
            + "JOIN mr.prescriptionDetailId pd "
            + "JOIN pd.prescriptionId p "
            + "WHERE p.prescriptionId = :prescriptionId")
    List<?> findMedicalScheduleByPrescriptionId(@Param("prescriptionId") int prescriptionId);

    @Query("SELECT ms FROM MedicalSchedule ms "
            + "WHERE FUNCTION('TIME', ms.customTime) = :targetTime") // So sánh chỉ phần thời gian
    List<MedicalSchedule> findByCustomTime(@Param("targetTime") String targetTime);

    @Query("SELECT ms FROM MedicalSchedule ms "
            + "WHERE FUNCTION('HOUR', ms.customTime) = :hour "
            + "AND FUNCTION('MINUTE', ms.customTime) = :minute")
    List<MedicalSchedule> findByHourAndMinute(
            @Param("hour") int hour,
            @Param("minute") int minute);
}
