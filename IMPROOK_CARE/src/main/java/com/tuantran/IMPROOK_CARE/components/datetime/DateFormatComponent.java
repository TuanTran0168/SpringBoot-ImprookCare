/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.components.datetime;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

/**
 *
 * @author Administrator
 */
@Component
public class DateFormatComponent {

    public static final String MY_DATE_FORMAT = "yyyy-MM-dd";

    public SimpleDateFormat myDateFormat() {
        return new SimpleDateFormat(MY_DATE_FORMAT);
    }
    
    public LocalDate myDateTimeFormat(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(MY_DATE_FORMAT);
        LocalDate date = LocalDate.parse(dateString, formatter);
        return date;
    }
}
