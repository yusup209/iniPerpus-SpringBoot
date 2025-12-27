package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.model.Lending;
import com.kkp.iniperpus.service.LendingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/lendings")
public class ApiLendingController {

    private final LendingService lendingService;
    private static final Logger log = LoggerFactory.getLogger(ApiLendingController.class);

    public ApiLendingController(LendingService lendingService) {
        this.lendingService = lendingService;
    }

    @GetMapping
    public List<Lending> list() { return lendingService.findAll(); }

    @PostMapping
    public ResponseEntity<?> lend(@Valid @RequestBody LendingRequest req) {
        try {
            log.info("LEND request studentId={}, bookId={}, dueDate={}", req.studentId, req.bookId, req.dueDate);
            var l = lendingService.lend(req.studentId, req.bookId, req.dueDate);
            log.info("LEND success id={} borrower={} book={} dueDate={} ", l.getId(),
                    l.getBorrower() != null ? l.getBorrower().getId() : null,
                    l.getBook() != null ? l.getBook().getId() : null,
                    l.getDueDate());
            return ResponseEntity.ok(l);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            log.warn("LEND failed: {}", ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        try {
            var l = lendingService.returnBook(id);
            return ResponseEntity.ok(l);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    static class LendingRequest {
        @jakarta.validation.constraints.NotNull
        public Long studentId;
        @jakarta.validation.constraints.NotNull
        public Long bookId;
        public LocalDate dueDate;
    }
}
