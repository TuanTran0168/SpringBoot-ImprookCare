/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service;

import com.tuantran.IMPROOK_CARE.models.Wards;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface WardsService {
    List<Wards> findWardsByDistrictCode(String districtCode);
}
