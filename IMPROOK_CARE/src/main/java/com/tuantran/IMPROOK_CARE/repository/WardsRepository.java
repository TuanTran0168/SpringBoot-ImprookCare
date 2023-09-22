package com.tuantran.IMPROOK_CARE.repository;


import com.tuantran.IMPROOK_CARE.models.Districts;
import com.tuantran.IMPROOK_CARE.models.Wards;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Administrator
 */
@Repository
public interface WardsRepository extends JpaRepository<Wards, String>{
    List<Wards> findWardsByDistrictCode(Districts districtCode);
}
