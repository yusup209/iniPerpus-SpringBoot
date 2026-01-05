package com.kkp.iniperpus.service;

import com.kkp.iniperpus.model.Borrower;
import com.kkp.iniperpus.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Borrower findById(Long id) { return studentRepository.findById(id).orElse(null); }

    public java.util.List<Borrower> findAll() { return studentRepository.findAll(); }

    public Borrower save(Borrower s) { return studentRepository.save(s); }

    public Borrower enrollPhoto(Long studentId, MultipartFile image, Path storageDir) throws IOException {
        Borrower s = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("student not found"));
        if (!Files.exists(storageDir)) Files.createDirectories(storageDir);
        String filename = studentId + "_photo" + (image.getOriginalFilename() != null ? ("_" + image.getOriginalFilename()) : "") + ".jpg";
        Path target = storageDir.resolve(filename);
        Files.write(target, image.getBytes());
        s.setPhotoFilename(filename);
        return studentRepository.save(s);
    }

    public void delete(Long id) { studentRepository.deleteById(id); }
}
