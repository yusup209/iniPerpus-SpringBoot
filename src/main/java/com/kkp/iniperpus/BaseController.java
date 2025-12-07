/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.kkp.iniperpus;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

/**
 *
 * @author thinkpad
 */
@Controller
public class BaseController {
    // Handles GET requests to the root URL
    @GetMapping("/")
    public String getIndexPage() {
        // Returns the name of the Thymeleaf template to be rendered
        return "index";
    }
    
    @GetMapping("/register")
    public String getRegisterPage(){
        return "register";
    }
    
    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }
   
    @GetMapping("/users")
    public String getUsersPage(){
        return "users";
    }
    
    @GetMapping("/books")
    public String getBooksPage(){
        return "books";
    }
    
    @GetMapping("/lending")
    public String getLendingPage(){
        return "lending";
    }
    
    @GetMapping("/report/pdf")
    public void generatePdf(HttpServletResponse response) throws Exception {
        // Prepare PDF in memory
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, baos);

        document.open();
        document.add(new Paragraph("Hello, this is your PDF report!"));
        document.add(new Paragraph("Generated at: " + java.time.LocalDateTime.now()));
        document.close();

        // Set response headers
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=report.pdf");
        response.setContentLength(baos.size());

        // Write PDF to output
        baos.writeTo(response.getOutputStream());
        response.flushBuffer();
    }
}
