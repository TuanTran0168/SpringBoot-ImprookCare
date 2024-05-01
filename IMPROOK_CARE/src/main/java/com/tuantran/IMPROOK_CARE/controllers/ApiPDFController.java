package com.tuantran.IMPROOK_CARE.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.font.PdfFont;

import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.tuantran.IMPROOK_CARE.components.PdfFont.PDFFontComponent;
import com.tuantran.IMPROOK_CARE.components.cloudinary.CloudinaryComponent;

@RestController
@RequestMapping("/api")
public class ApiPDFController {

    @Autowired
    private CloudinaryComponent cloudinaryComponent;

    @Autowired
    private PDFFontComponent pdfFontComponent;

    @GetMapping("/public/generate-upload-pdf/")
    @CrossOrigin
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

    @PostMapping("/public/generate-pdf/")
    @CrossOrigin
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

    @PostMapping("/public/generate-pdf-test-result/")
    @CrossOrigin
    public ResponseEntity<byte[]> generatePDFTestResult(@RequestBody Map<String, String> params) throws IOException {

        String profilePatientName = params.get("profilePatientName");
        String profileDoctorName = params.get("profileDoctorName");
        String nurseName = params.get("nurseName");

        // Tạo ByteArrayOutputStream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Sử dụng PdfWriter để viết PDF vào ByteArrayOutputStream
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);

        // Tạo phông chữ hỗ trợ UTF-8
        // PdfFont vietnameseFont = PdfFontFactory.createFont(fontPath, "Identity-H",
        // pdfDocument);

        PdfFont vietnameseFont = pdfFontComponent.getVietnameseFontPDF(pdfDocument);

        // Thêm tiêu đề cho phiếu xét nghiệm
        document.add(new Paragraph("Phiếu Xét Nghiệm")
                .setFontSize(18)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER)
                .setFont(vietnameseFont));

        document.add(new Paragraph("Tên bác sĩ: " + profileDoctorName)
                .setFontSize(13)
                .setBold()
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(vietnameseFont));
        document.add(new Paragraph("Tên bệnh nhân: " + profilePatientName)
                .setFontSize(13)
                .setBold()
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(vietnameseFont));
        document.add(new Paragraph("Tên y tá: " + nurseName)
                .setFontSize(13)
                .setBold()
                .setTextAlignment(TextAlignment.LEFT)
                .setFont(vietnameseFont));

        // Tạo bảng dữ liệu xét nghiệm
        Table table = new Table(new float[] { 1, 2, 2, 2 });
        table.setWidth(UnitValue.createPercentValue(100));

        // Thêm hàng tiêu đề cho bảng
        table.addCell(new Cell().add(new Paragraph("STT")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Tên Xét Nghiệm")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Kết Quả")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Đơn Vị")).setBorder(Border.NO_BORDER));

        // Thêm dữ liệu xét nghiệm
        table.addCell(new Cell().add(new Paragraph("1")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Glucose")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("5.4")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("mmol/L")).setBorder(Border.NO_BORDER));

        table.addCell(new Cell().add(new Paragraph("2")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Cholesterol")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("4.7")).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("mmol/L")).setBorder(Border.NO_BORDER));

        document.add(table);

        // Thêm chữ ký và ngày
        document.add(new Paragraph("\nChữ Ký Bác Sĩ").setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("Ngày: 27/04/2024").setTextAlignment(TextAlignment.RIGHT));

        // Đóng tài liệu
        document.close();
        pdfDocument.close();

        // Lấy dữ liệu PDF từ ByteArrayOutputStream
        byte[] pdfBytes = byteArrayOutputStream.toByteArray();

        // Thiết lập tiêu đề HTTP để nhắc người dùng tải xuống
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=phieu_xet_nghiem.pdf");
        headers.add("Content-Type", "application/pdf");

        // Trả lại phản hồi HTTP với dữ liệu PDF và mã trạng thái 200 (OK)
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
