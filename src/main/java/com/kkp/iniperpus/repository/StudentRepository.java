package com.kkp.iniperpus.repository;

import com.kkp.iniperpus.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStudentId(String studentId);
}
