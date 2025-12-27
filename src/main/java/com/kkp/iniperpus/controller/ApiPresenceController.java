package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.model.Student;
import com.kkp.iniperpus.repository.StudentRepository;
import com.kkp.iniperpus.service.PresenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/presence")
public class ApiPresenceController {

    private final PresenceService presenceService;
    private final StudentRepository studentRepository;

    public ApiPresenceController(PresenceService presenceService, StudentRepository studentRepository) {
        this.presenceService = presenceService;
        this.studentRepository = studentRepository;
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
    public ResponseEntity<?> enroll(@RequestParam("studentId") Long studentId, @RequestParam("image") MultipartFile image) {
        Student s = studentRepository.findById(studentId).orElse(null);
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
