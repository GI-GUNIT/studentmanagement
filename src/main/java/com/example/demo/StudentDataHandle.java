package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentDataHandle {

    @Autowired
    private StudentRepository studentRepository;

    // Insert data (POST)
    @PostMapping
    public ResponseEntity<String> addStudent(@RequestBody Student student) {
        try {
            studentRepository.save(student);
            return ResponseEntity.ok("Student record added successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to add student record: " + e.getMessage());
        }
    }

    // Delete data (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        try {
            if (studentRepository.existsById(id)) {
                studentRepository.deleteById(id);
                return ResponseEntity.ok("Student record deleted successfully!");
            } else {
                return ResponseEntity.status(404).body("Student record not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to delete student record: " + e.getMessage());
        }
    }

    // Update data (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateStudent(@PathVariable int id, @RequestBody Student updatedStudent) {
        try {
            return studentRepository.findById(id).map(existingStudent -> {
                existingStudent.setName(updatedStudent.getName());
                existingStudent.setStudentClass(updatedStudent.getStudentClass());
                existingStudent.setDob(updatedStudent.getDob());
                existingStudent.setMaths(updatedStudent.getMaths());
                existingStudent.setPhysics(updatedStudent.getPhysics());
                existingStudent.setChemistry(updatedStudent.getChemistry());

                studentRepository.save(existingStudent);
                return ResponseEntity.ok("Student record updated successfully!");
            }).orElseGet(() -> ResponseEntity.status(404).body("Student record not found with ID: " + id));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to update student record: " + e.getMessage());
        }
    }

    // Fetch all students (GET)
    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        try {
            return ResponseEntity.ok(studentRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch student records: " + e.getMessage());
        }
    }

    // Fetch a single student by ID (GET)

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable int id) {
        try {
            Optional<Student> optionalStudent = studentRepository.findById(id);
            if (optionalStudent.isPresent()) {
                return ResponseEntity.ok(optionalStudent.get());
            } else {
                return ResponseEntity.status(404).body("Student record not found with ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to fetch student record: " + e.getMessage());
        }
    }



}

