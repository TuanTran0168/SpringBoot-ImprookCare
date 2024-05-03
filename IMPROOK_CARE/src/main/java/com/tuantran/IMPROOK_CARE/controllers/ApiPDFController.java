package com.tuantran.IMPROOK_CARE.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
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
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.tuantran.IMPROOK_CARE.components.PdfFont.PDFFontComponent;
import com.tuantran.IMPROOK_CARE.components.cloudinary.CloudinaryComponent;
import com.tuantran.IMPROOK_CARE.components.datetime.DateFormatComponent;

@RestController
@RequestMapping("/api")
public class ApiPDFController {

        @Autowired
        private CloudinaryComponent cloudinaryComponent;

        @Autowired
        private PDFFontComponent pdfFontComponent;

        @Autowired
        private DateFormatComponent dateFormatComponent;

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
                        String linkCloudinaryPDF = cloudinaryComponent
                                        .CloudinaryPDF(byteArrayOutputStream.toByteArray())
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
        public ResponseEntity<byte[]> generatePDFTestResult(@RequestBody Map<String, String> params)
                        throws IOException {

                String profilePatientName = params.get("profilePatientName");
                String profileDoctorName = params.get("profileDoctorName");
                String nurseName = params.get("nurseName");
                String birthday = params.get("birthday");
                String address = params.get("address");
                String specialtyName = params.get("specialtyName");
                String testResultDiagnosis = params.get("testResultDiagnosis");
                String gender = params.get("gender");
                String createdDate = params.get("gender");
                String updatedDate = params.get("gender");
                String profilePatientId = params.get("profilePatientId");

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

                Date createDate = new Date();

                // Thêm tiêu đề cho phiếu xét nghiệm
                document.add(new Paragraph("Phiếu Xét Nghiệm")
                                .setFontSize(18)
                                .setBold()
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFont(vietnameseFont));

                document.add(new Paragraph("(" + dateFormatComponent.myDateTimeFormat().format(createDate) + ")")
                                .setFontSize(11)
                                .setItalic()
                                .setMarginTop(-10)
                                .setMarginBottom(20)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFont(vietnameseFont));

                Table tableInfor = new Table(new float[] { 1, 1 });
                tableInfor.setWidth(UnitValue.createPercentValue(100));
                tableInfor.setBorder(Border.NO_BORDER);

                tableInfor.addCell(new Cell()
                                .add(new Paragraph("Họ và tên: " + profilePatientName))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setBackgroundColor(new DeviceRgb(255, 200, 200))
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setWidth(UnitValue.createPercentValue(0))
                                .setFont(vietnameseFont));

                tableInfor.addCell(new Cell()
                                .add(new Paragraph("Mã hồ sơ: " + profilePatientId))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setWidth(UnitValue.createPercentValue(50))
                                .setPaddingLeft(70)
                                .setFont(vietnameseFont));

                tableInfor.addCell(new Cell()
                                .add(new Paragraph("Năm sinh: " + birthday))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setWidth(UnitValue.createPercentValue(50))
                                .setFont(vietnameseFont));

                tableInfor.addCell(new Cell()
                                .add(new Paragraph("Giới tính: " + gender))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setWidth(UnitValue.createPercentValue(50))
                                .setPaddingLeft(70)
                                .setFont(vietnameseFont));

                tableInfor.addCell(new Cell(1, 2)
                                // .add(new Paragraph("Địa chỉ: " + address))
                                // .add(new Paragraph("Địa chỉ: aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                // address))
                                .add(new Paragraph("Địa chỉ: " + address))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setFont(vietnameseFont));

                tableInfor.addCell(new Cell()
                                .add(new Paragraph("Khoa/phòng: " + specialtyName))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setWidth(UnitValue.createPercentValue(50))
                                .setFont(vietnameseFont));

                tableInfor.addCell(new Cell()
                                .add(new Paragraph("Người lấy mẫu: " + nurseName))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setWidth(UnitValue.createPercentValue(50))
                                .setPaddingLeft(70)
                                .setFont(vietnameseFont));

                tableInfor.addCell(new Cell()
                                .add(new Paragraph("Chuẩn đoán: " + testResultDiagnosis))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setWidth(UnitValue.createPercentValue(50))
                                .setFont(vietnameseFont));

                tableInfor.addCell(new Cell()
                                .add(new Paragraph("T/gian lấy mẫu: " + createdDate))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setWidth(UnitValue.createPercentValue(50))
                                .setPaddingLeft(70)
                                .setFont(vietnameseFont));

                tableInfor.addCell(new Cell()
                                .add(new Paragraph("Bác sĩ chỉ định: " + profileDoctorName))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setWidth(UnitValue.createPercentValue(50))
                                .setFont(vietnameseFont));

                tableInfor.addCell(new Cell()
                                .add(new Paragraph("Người nhận mẫu: " + nurseName))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setWidth(UnitValue.createPercentValue(50))
                                .setPaddingLeft(70)
                                .setFont(vietnameseFont));

                tableInfor.addCell(new Cell()
                                .add(new Paragraph("Tình trạng mẫu: Tốt"))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setWidth(UnitValue.createPercentValue(50))
                                .setFont(vietnameseFont));

                tableInfor.addCell(new Cell()
                                .add(new Paragraph("T/gian trả kết quả: " + updatedDate))
                                .setBorderTop(Border.NO_BORDER)
                                .setBorderBottom(Border.NO_BORDER)
                                .setBorderLeft(Border.NO_BORDER)
                                .setBorderRight(Border.NO_BORDER)
                                .setFontSize(10)
                                .setBold()
                                .setTextAlignment(TextAlignment.LEFT)
                                .setWidth(UnitValue.createPercentValue(50))
                                .setPaddingLeft(70)
                                .setFont(vietnameseFont));

                document.add(tableInfor);

                document.add(new Paragraph("I. Kết quả xét nghiệm")
                                .setFontSize(13)
                                .setBold()
                                .setMarginTop(10)
                                .setTextAlignment(TextAlignment.LEFT)
                                .setFont(vietnameseFont));

                // Tạo bảng dữ liệu xét nghiệm
                Table table = new Table(new float[] { 1, 2, 2, 2 });
                table.setWidth(UnitValue.createPercentValue(100));

                SolidBorder solidBorder = new SolidBorder(Border.SOLID);

                // Thêm hàng tiêu đề cho bảng
                table.addCell(new Cell().add(new Paragraph("STT")).setBorder(solidBorder).setBold()
                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("Xét Nghiệm")).setBorder(solidBorder).setBold()
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFont(vietnameseFont));
                table.addCell(
                                new Cell().add(new Paragraph("Kết Quả")).setBorder(solidBorder).setBold()
                                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("Đơn Vị")).setBorder(solidBorder).setBold()
                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));

                // Thêm dữ liệu xét nghiệm
                table.addCell(new Cell().add(new Paragraph("1")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("Glucose")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("5.4")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("mmol/L")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));

                table.addCell(new Cell().add(new Paragraph("2")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("Cholesterol")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("4.7")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("mmol/L")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));

                table.addCell(new Cell().add(new Paragraph("3")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("Fructosamine")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("280")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("µmol/L")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));

                table.addCell(new Cell().add(new Paragraph("4")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("Hemoglobin")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));
                table.addCell(
                                new Cell().add(new Paragraph("15.30")).setBorder(solidBorder)
                                                .setTextAlignment(TextAlignment.CENTER)
                                                .setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("g/dL")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));

                table.addCell(new Cell().add(new Paragraph("4")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER)
                                .setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("Hematocrite")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));
                table.addCell(
                                new Cell().add(new Paragraph("44.40")).setBorder(solidBorder)
                                                .setTextAlignment(TextAlignment.CENTER)
                                                .setFont(vietnameseFont));
                table.addCell(new Cell().add(new Paragraph("%")).setBorder(solidBorder)
                                .setTextAlignment(TextAlignment.CENTER).setFont(vietnameseFont));

                document.add(table);

                // Thêm chữ ký và ngày
                document.add(new Paragraph("\nChữ Ký Bác Sĩ").setTextAlignment(TextAlignment.RIGHT)
                                .setFont(vietnameseFont));
                document.add(new Paragraph(profileDoctorName).setTextAlignment(TextAlignment.RIGHT)
                                .setFont(vietnameseFont));
                document.add(new Paragraph(dateFormatComponent.myDateFormat().format(createDate))
                                .setTextAlignment(TextAlignment.RIGHT).setFont(vietnameseFont));

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
