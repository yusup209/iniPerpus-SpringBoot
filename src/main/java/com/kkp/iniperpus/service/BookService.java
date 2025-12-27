package com.kkp.iniperpus.service;

import com.kkp.iniperpus.model.Book;
import com.kkp.iniperpus.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() { return bookRepository.findAll(); }

    public Book findById(Long id) { return bookRepository.findById(id).orElse(null); }

    public Book create(Book b) {
        if (b.getCopiesAvailable() == null) b.setCopiesAvailable(b.getCopiesTotal());
        return bookRepository.save(b);
    }

    public Book update(Long id, Book updates) {
        Book b = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("book not found"));
        if (updates.getTitle() != null && !updates.getTitle().isBlank()) b.setTitle(updates.getTitle());
        if (updates.getAuthor() != null && !updates.getAuthor().isBlank()) b.setAuthor(updates.getAuthor());
        if (updates.getIsbn() != null && !updates.getIsbn().isBlank()) b.setIsbn(updates.getIsbn());
        if (updates.getCopiesTotal() != null && updates.getCopiesTotal() > 0) b.setCopiesTotal(updates.getCopiesTotal());
        return bookRepository.save(b);
    }

    public void delete(Long id) { bookRepository.deleteById(id); }
}
