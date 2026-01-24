package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.service.ReportService;
import com.lowagie.text.DocumentException;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api/reports")
public class ApiReportController {

    private final ReportService reportService;
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public ApiReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    /**
     * Generate Borrower Report PDF
     */
    @GetMapping("/borrowers")
    public ResponseEntity<byte[]> generateBorrowerReport() throws DocumentException {
        byte[] pdfContent = reportService.generateBorrowerReport();
        String filename = "Borrower_Report_" + LocalDateTime.now().format(dateTimeFormatter) + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(pdfContent);
    }

    /**
     * Generate Book Report PDF
     */
    @GetMapping("/books")
    public ResponseEntity<byte[]> generateBookReport() throws DocumentException {
        byte[] pdfContent = reportService.generateBookReport();
        String filename = "Book_Report_" + LocalDateTime.now().format(dateTimeFormatter) + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(pdfContent);
    }

    /**
     * Generate Lending Report PDF
     */
    @GetMapping("/lending")
    public ResponseEntity<byte[]> generateLendingReport(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr,
            @RequestParam(value = "status", required = false) String status
    ) throws DocumentException {
        java.time.LocalDate startDate = null;
        java.time.LocalDate endDate = null;
        try {
            if (startDateStr != null && !startDateStr.isBlank()) {
                startDate = java.time.LocalDate.parse(startDateStr);
            }
            if (endDateStr != null && !endDateStr.isBlank()) {
                endDate = java.time.LocalDate.parse(endDateStr);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(("Invalid date format. Use yyyy-MM-dd").getBytes());
        }

        byte[] pdfContent = reportService.generateLendingReport(startDate, endDate, status);
        String filename = "Lending_Report_" + LocalDateTime.now().format(dateTimeFormatter) + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(pdfContent);
    }

    /**
     * Generate Presence Report PDF with optional date filters
     */
    @GetMapping("/presence")
    public ResponseEntity<byte[]> generatePresenceReport(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr
    ) throws DocumentException {
        java.time.LocalDate startDate = null;
        java.time.LocalDate endDate = null;
        try {
            if (startDateStr != null && !startDateStr.isBlank()) {
                startDate = java.time.LocalDate.parse(startDateStr);
            }
            if (endDateStr != null && !endDateStr.isBlank()) {
                endDate = java.time.LocalDate.parse(endDateStr);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(("Invalid date format. Use yyyy-MM-dd").getBytes());
        }

        byte[] pdfContent = reportService.generatePresenceReport(startDate, endDate);
        String filename = "Presence_Report_" + LocalDateTime.now().format(dateTimeFormatter) + ".pdf";

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(pdfContent);
    }
}
