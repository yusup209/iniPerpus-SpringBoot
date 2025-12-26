package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.model.Book;
import com.kkp.iniperpus.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class ApiBookController {

    private final BookService bookService;

    public ApiBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public List<Book> list() { return bookService.findAll(); }

    @PostMapping
    public Book create(@Valid @RequestBody Book b) { return bookService.create(b); }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok().build();
    }
}
