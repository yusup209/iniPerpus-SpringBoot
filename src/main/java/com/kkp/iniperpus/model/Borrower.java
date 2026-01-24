package com.kkp.iniperpus.model;

import jakarta.persistence.*;

@Entity
@Table(name = "borrower")
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String borrowerId;

    private String name;
    @Column(name = "division")
    private String division;
    private String photoFilename;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBorrowerId() { return borrowerId; }
    public void setBorrowerId(String borrowerId) { this.borrowerId = borrowerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }
    public String getPhotoFilename() { return photoFilename; }
    public void setPhotoFilename(String photoFilename) { this.photoFilename = photoFilename; }
}
