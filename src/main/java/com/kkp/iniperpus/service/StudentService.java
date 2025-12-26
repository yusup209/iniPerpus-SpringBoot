package com.kkp.iniperpus.service;

import com.kkp.iniperpus.model.Student;
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

    public Student findById(Long id) { return studentRepository.findById(id).orElse(null); }

    public java.util.List<Student> findAll() { return studentRepository.findAll(); }

    public Student save(Student s) { return studentRepository.save(s); }

    public Student enrollPhoto(Long studentId, MultipartFile image, Path storageDir) throws IOException {
        Student s = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("student not found"));
        if (!Files.exists(storageDir)) Files.createDirectories(storageDir);
        String filename = studentId + "_photo" + (image.getOriginalFilename() != null ? ("_" + image.getOriginalFilename()) : "") + ".jpg";
        Path target = storageDir.resolve(filename);
        Files.write(target, image.getBytes());
        s.setPhotoFilename(filename);
        return studentRepository.save(s);
    }
}
