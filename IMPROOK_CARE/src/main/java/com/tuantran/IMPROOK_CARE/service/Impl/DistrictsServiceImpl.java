/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.models.Districts;
import com.tuantran.IMPROOK_CARE.models.Provinces;
import com.tuantran.IMPROOK_CARE.repository.DistrictsRepository;
import com.tuantran.IMPROOK_CARE.repository.ProvincesRepository;
import com.tuantran.IMPROOK_CARE.service.DistrictsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Administrator
 */
@Service
public class DistrictsServiceImpl implements DistrictsService {
    @Autowired
    private DistrictsRepository districtsRepository;
    
    @Autowired
    private ProvincesRepository provincesRepository;

    @Override
    public List<Districts> findDistrictsByProvinceCode(String provinceCode) {
        Provinces province = this.provincesRepository.findById(provinceCode).get();
        return this.districtsRepository.findDistrictsByProvinceCode(province);
    }
    
    
}
