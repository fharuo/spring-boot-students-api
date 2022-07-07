package com.example.students.controllers;

import com.example.students.dtos.StudentDTO;
import com.example.students.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAll() {
        return ResponseEntity.ok(studentService.getAll());
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@RequestBody StudentDTO studentDTO) {
        StudentDTO student = studentService.create(studentDTO);
        return ResponseEntity.created(buildLocation(student.getId())).body(student);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        studentService.deleteById(id);
    }

    private URI buildLocation(Integer id) {
        return  ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
