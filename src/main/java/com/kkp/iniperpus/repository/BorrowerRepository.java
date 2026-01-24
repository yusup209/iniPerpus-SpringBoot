package com.kkp.iniperpus.repository;

import com.kkp.iniperpus.model.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
    Borrower findByBorrowerId(String borrowerId);
}
