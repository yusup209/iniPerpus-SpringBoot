package com.kkp.iniperpus.controller;

import com.kkp.iniperpus.model.Student;
import com.kkp.iniperpus.service.PresenceService;
import com.kkp.iniperpus.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class ApiStudentController {

    private final StudentService studentService;
    private final PresenceService presenceService;

    public ApiStudentController(StudentService studentService, PresenceService presenceService) {
        this.studentService = studentService;
        this.presenceService = presenceService;
    }

    @GetMapping
    public List<Student> list() { return studentService.findAll(); }

    @PostMapping
    public Student create(@RequestBody Student s) { return studentService.save(s); }

    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student updates) {
        Student existing = studentService.findById(id);
        if (existing == null) throw new RuntimeException("Student not found");
        if (updates.getName() != null) existing.setName(updates.getName());
        if (updates.getStudentId() != null) existing.setStudentId(updates.getStudentId());
        if (updates.getClassName() != null) existing.setClassName(updates.getClassName());
        return studentService.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            studentService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Cannot delete student: " + e.getMessage());
        }
    }

    @PostMapping(path = "/{id}/photo", consumes = {"multipart/form-data"})
    public ResponseEntity<?> uploadPhoto(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        try {
            // store locally
            var saved = studentService.enrollPhoto(id, image, Path.of(System.getProperty("user.dir"), "student-photos"));
            // enroll with face service
            presenceService.enroll(saved.getStudentId(), image);
            return ResponseEntity.ok(saved);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
