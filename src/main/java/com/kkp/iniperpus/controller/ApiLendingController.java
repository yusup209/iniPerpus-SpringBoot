package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.model.Lending;
import com.kkp.iniperpus.service.LendingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/lendings")
public class ApiLendingController {

    private final LendingService lendingService;

    public ApiLendingController(LendingService lendingService) {
        this.lendingService = lendingService;
    }

    @GetMapping
    public List<Lending> list() { return lendingService.findAll(); }

    @PostMapping
    public ResponseEntity<?> lend(@Valid @RequestBody LendingRequest req) {
        try {
            var l = lendingService.lend(req.userId, req.bookId, req.dueDate);
            return ResponseEntity.ok(l);
        } catch (IllegalArgumentException | IllegalStateException ex) {
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
        public Long userId;
        @jakarta.validation.constraints.NotNull
        public Long bookId;
        public LocalDate dueDate;
    }
}
