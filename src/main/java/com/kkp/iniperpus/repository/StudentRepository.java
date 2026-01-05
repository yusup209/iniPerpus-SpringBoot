package com.kkp.iniperpus.repository;

import com.kkp.iniperpus.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Borrower, Long> {
    Borrower findByStudentId(String studentId);
}
