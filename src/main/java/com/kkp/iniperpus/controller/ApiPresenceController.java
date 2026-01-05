package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.model.PresenceRecord;
import com.kkp.iniperpus.model.Borrower;
import com.kkp.iniperpus.repository.PresenceRecordRepository;
import com.kkp.iniperpus.repository.StudentRepository;
import com.kkp.iniperpus.service.PresenceService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/presence")
public class ApiPresenceController {

    private final PresenceService presenceService;
    private final StudentRepository borrowerRepository;
    private final PresenceRecordRepository presenceRecordRepository;

    public ApiPresenceController(PresenceService presenceService, StudentRepository borrowerRepository, PresenceRecordRepository presenceRecordRepository) {
        this.presenceService = presenceService;
        this.borrowerRepository = borrowerRepository;
        this.presenceRecordRepository = presenceRecordRepository;
    }

    @GetMapping("/records")
    public List<PresenceRecord> getRecords() {
        return presenceRecordRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    @PostMapping("/checkin")
    public ResponseEntity<?> checkIn(@RequestParam("image") MultipartFile image) {
        try {
            Map<String, Object> result = presenceService.match(image);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enroll(@RequestParam("borrowerId") Long borrowerId, @RequestParam("image") MultipartFile image) {
        Borrower s = borrowerRepository.findById(borrowerId).orElse(null);
        if (s == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Student not found"));
        }
        try {
            Map<String, Object> result = presenceService.enroll(s.getStudentId(), image);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
