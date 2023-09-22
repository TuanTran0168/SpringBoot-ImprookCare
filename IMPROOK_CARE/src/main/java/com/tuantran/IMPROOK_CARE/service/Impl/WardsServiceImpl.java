/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.Districts;
import com.tuantran.IMPROOK_CARE.models.Wards;
import com.tuantran.IMPROOK_CARE.repository.DistrictsRepository;
import com.tuantran.IMPROOK_CARE.repository.WardsRepository;
import com.tuantran.IMPROOK_CARE.service.WardsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */

@Service
public class WardsServiceImpl implements WardsService{
    
    @Autowired
    private WardsRepository wardsRepository;
    
    @Autowired
    private DistrictsRepository districtsRepository;

    @Override
    public List<Wards> findWardsByDistrictCode(String districtCode) {
        Districts districts = this.districtsRepository.findById(districtCode).get();
        return this.wardsRepository.findWardsByDistrictCode(districts);
    }
    
}
