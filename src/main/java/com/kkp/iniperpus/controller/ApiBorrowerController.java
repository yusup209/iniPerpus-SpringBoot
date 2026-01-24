package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.model.Borrower;
import com.kkp.iniperpus.service.PresenceService;
import com.kkp.iniperpus.service.BorrowerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/borrowers")
public class ApiBorrowerController {

    private final BorrowerService borrowerService;
    private final PresenceService presenceService;

    public ApiBorrowerController(BorrowerService borrowerService, PresenceService presenceService) {
        this.borrowerService = borrowerService;
        this.presenceService = presenceService;
    }

    @GetMapping
    public List<Borrower> list() { return borrowerService.findAll(); }

    @PostMapping
    public Borrower create(@RequestBody Borrower s) { return borrowerService.save(s); }

    @PutMapping("/{id}")
    public Borrower update(@PathVariable Long id, @RequestBody Borrower updates) {
        Borrower existing = borrowerService.findById(id);
        if (existing == null) throw new RuntimeException("Borrower not found");
        if (updates.getName() != null) existing.setName(updates.getName());
        if (updates.getBorrowerId() != null) existing.setBorrowerId(updates.getBorrowerId());
        if (updates.getDivision() != null) existing.setDivision(updates.getDivision());
        return borrowerService.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            Borrower borrower = borrowerService.findById(id);
            if (borrower != null && borrower.getBorrowerId() != null) {
                // Delete face data from face service
                presenceService.deleteFaceData(borrower.getBorrowerId());
            }
            borrowerService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Cannot delete borrower: " + e.getMessage());
        }
    }

    @PostMapping(path = "/{id}/photo", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        try {
            // store locally
            var saved = borrowerService.enrollPhoto(id, image, Path.of(System.getProperty("user.dir"), "student-photos"));
            // enroll with face service
            presenceService.enroll(saved.getBorrowerId(), image);
            return ResponseEntity.ok(saved);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
