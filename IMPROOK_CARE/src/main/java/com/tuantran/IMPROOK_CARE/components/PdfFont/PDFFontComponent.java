/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.components.PdfFont;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.springframework.stereotype.Component;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;

/**
 *
 * @author Administrator
 */
@Component
public class PDFFontComponent {
    ClassLoader classLoader = getClass().getClassLoader();
    // private String fontPath =
    // "C:\\Users\\Administrator\\Downloads\\Open_Sans\\OpenSans-Italic-VariableFont_wdth,wght.ttf";

    public PdfFont getVietnameseFontPDF(PdfDocument pdfDocument) throws IOException {

        URL url = classLoader.getResource("\\fonts\\Open_Sans\\OpenSans-Italic-VariableFont_wdth,wght.ttf");

        System.out.println("++++++++++++++++++++++++++");
        System.out.println(url);
        System.out.println("++++++++++++++++++++++++++");

        PdfFont vietnameseFont = PdfFontFactory.createFont(url.toString(), "Identity-H", pdfDocument);

        return vietnameseFont;
    }

}
