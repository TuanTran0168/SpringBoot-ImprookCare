package com.tuantran.IMPROOK_CARE.controllers;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.tuantran.IMPROOK_CARE.components.cloudinary.CloudinaryComponent;

@RestController
@RequestMapping("/api")
public class ApiPDFController {

    @Autowired
    private CloudinaryComponent cloudinaryComponent;

    @GetMapping("/public/generate-upload-pdf/")
    public void generateUpdatePDF() throws FileNotFoundException {
        // String path = "test.pdf";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);

        document.add(new Paragraph("HELLO WORLD"));
        document.close();
        if (document != null && byteArrayOutputStream != null) {
            String linkCloudinaryPDF = cloudinaryComponent.CloudinaryPDF(byteArrayOutputStream.toByteArray())
                    .get("secure_url")
                    .toString();

            System.out.println(linkCloudinaryPDF);
        }
    }

    // @GetMapping("/public/generate-pdf/")
    // public void generatePDF() throws FileNotFoundException {
    // String path = "test.pdf";
    // PdfWriter pdfWriter = new PdfWriter(path);
    // PdfDocument pdfDocument = new PdfDocument(pdfWriter);
    // pdfDocument.setDefaultPageSize(PageSize.A4);
    // Document document = new Document(pdfDocument);

    // document.add(new Paragraph("HELLO WORLD"));
    // document.close();
    // }

    @GetMapping("/public/generate-pdf/")
    public ResponseEntity<byte[]> generatePDF() {
        // Tạo ByteArrayOutputStream để giữ dữ liệu PDF trong bộ nhớ
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Sử dụng PdfWriter để viết PDF vào ByteArrayOutputStream
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);

        // Thêm nội dung vào tài liệu
        document.add(new Paragraph("HELLO WORLD"));

        // Đóng tài liệu và PdfDocument
        document.close();
        pdfDocument.close();

        // Lấy dữ liệu PDF từ ByteArrayOutputStream
        byte[] pdfBytes = byteArrayOutputStream.toByteArray();

        // Thiết lập tiêu đề HTTP để nhắc người dùng tải xuống tệp PDF
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=test.pdf");
        headers.add("Content-Type", "application/pdf");

        // Trả lại phản hồi HTTP với dữ liệu PDF và mã trạng thái 200 (OK)
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
