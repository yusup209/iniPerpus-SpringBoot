package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.service.PresenceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/presence")
public class ApiPresenceController {

    private final PresenceService presenceService;

    public ApiPresenceController(PresenceService presenceService) {
        this.presenceService = presenceService;
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
}
