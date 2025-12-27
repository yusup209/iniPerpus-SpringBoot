package com.kkp.iniperpus.service;

import com.kkp.iniperpus.model.Book;
import com.kkp.iniperpus.model.Lending;
import com.kkp.iniperpus.model.Student;
import com.kkp.iniperpus.repository.BookRepository;
import com.kkp.iniperpus.repository.LendingRepository;
import com.kkp.iniperpus.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LendingService {

    private final LendingRepository lendingRepository;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    public LendingService(LendingRepository lendingRepository, BookRepository bookRepository, StudentRepository studentRepository) {
        this.lendingRepository = lendingRepository;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    public List<Lending> findAll() { return lendingRepository.findAll(); }

    public Lending lend(Long studentId, Long bookId, LocalDate dueDate) {
        Student s = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("invalid student"));
        Book b = bookRepository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("invalid book"));
        if (b.getCopiesAvailable() == null || b.getCopiesAvailable() <= 0) throw new IllegalStateException("no copies available");

        Lending l = new Lending();
        l.setBorrower(s);
        l.setBook(b);
        l.setLendDate(LocalDate.now());
        l.setDueDate(dueDate != null ? dueDate : LocalDate.now().plusDays(7));
        lendingRepository.save(l);

        b.setCopiesAvailable(b.getCopiesAvailable() - 1);
        bookRepository.save(b);

        return l;
    }

    public Lending returnBook(Long lendingId) {
        Lending l = lendingRepository.findById(lendingId).orElseThrow(() -> new IllegalArgumentException("invalid lending id"));
        l.setReturnDate(LocalDate.now());
        lendingRepository.save(l);

        Book b = l.getBook();
        b.setCopiesAvailable(b.getCopiesAvailable() + 1);
        bookRepository.save(b);

        return l;
    }
}
