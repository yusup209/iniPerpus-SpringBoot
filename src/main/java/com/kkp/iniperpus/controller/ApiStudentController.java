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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
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
