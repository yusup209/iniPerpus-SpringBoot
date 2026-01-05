package com.kkp.iniperpus.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import com.kkp.iniperpus.model.Borrower;

@Entity
public class Lending {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Book book;

    @ManyToOne(optional = false)
    private Borrower borrower;

    private LocalDate lendDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public Borrower getBorrower() { return borrower; }
    public void setBorrower(Borrower borrower) { this.borrower = borrower; }
    public LocalDate getLendDate() { return lendDate; }
    public void setLendDate(LocalDate lendDate) { this.lendDate = lendDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
}
