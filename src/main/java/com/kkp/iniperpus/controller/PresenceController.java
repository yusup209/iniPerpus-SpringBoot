package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.model.PresenceRecord;
import com.kkp.iniperpus.model.Borrower;
import com.kkp.iniperpus.repository.PresenceRecordRepository;
import com.kkp.iniperpus.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Controller
public class PresenceController {

    private final BorrowerRepository borrowerRepository;
    private final PresenceRecordRepository presenceRecordRepository;
    private final com.kkp.iniperpus.service.PresenceService presenceService;

    public PresenceController(BorrowerRepository borrowerRepository, PresenceRecordRepository presenceRecordRepository, com.kkp.iniperpus.service.PresenceService presenceService) {
        this.borrowerRepository = borrowerRepository;
        this.presenceRecordRepository = presenceRecordRepository;
        this.presenceService = presenceService;
    }

    @GetMapping("/presence")
    public String presencePage(Model model) {
        model.addAttribute("borrowers", borrowerRepository.findAll());
        return "presence";
    }

    @PostMapping("/presence/enroll")
    public String enroll(@RequestParam("borrowerId") Long borrowerId, @RequestParam("image") MultipartFile image, Model model) {
        Borrower s = borrowerRepository.findById(borrowerId).orElse(null);
        if (s == null) {
            model.addAttribute("error", "Borrower not found");
            return "presence";
        }

        try {
            var resp = presenceService.enroll(s.getBorrowerId(), image);
            // update borrower photo filename via BorrowerService if needed (not done here)
            model.addAttribute("message", "Enrolled: " + resp);
        } catch (Exception ex) {
            model.addAttribute("error", "Enroll failed: " + ex.getMessage());
        }

        model.addAttribute("borrowers", borrowerRepository.findAll());
        return "presence";
    }

    @PostMapping("/presence/match")
    public String match(@RequestParam("image") MultipartFile image, Model model) {
        try {
            var resp = presenceService.match(image);
            model.addAttribute("message", resp);
        } catch (Exception ex) {
            model.addAttribute("error", "Match failed: " + ex.getMessage());
        }
        model.addAttribute("borrowers", borrowerRepository.findAll());
        return "presence";
    }
}
