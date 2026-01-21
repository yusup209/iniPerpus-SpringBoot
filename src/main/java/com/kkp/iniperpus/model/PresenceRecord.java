package com.kkp.iniperpus.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class PresenceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Borrower borrower;

    private LocalDateTime timestamp;
    private Boolean matched = true;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Borrower getBorrower() { return borrower; }
    public void setBorrower(Borrower borrower) { this.borrower = borrower; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public Boolean getMatched() { return matched; }
    public void setMatched(Boolean matched) { this.matched = matched; }
}
