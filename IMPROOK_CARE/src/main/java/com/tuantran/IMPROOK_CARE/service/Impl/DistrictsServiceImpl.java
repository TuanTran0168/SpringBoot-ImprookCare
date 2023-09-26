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
import java.util.NoSuchElementException;
import java.util.Optional;
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

        try {
            Optional<Provinces> provincesOptional = this.provincesRepository.findById(provinceCode);
            if (provincesOptional.isPresent()) {
                Provinces province = provincesOptional.get();
                return this.districtsRepository.findDistrictsByProvinceCode(province);
            } else {
                return null;
            }
        } catch (NoSuchElementException ex) {
            return null;
        }

    }

}
