package com.example.students.services;

import com.example.students.dtos.StudentDTO;
import com.example.students.models.Student;

import java.net.URI;
import java.util.List;

public interface StudentService {
    List<StudentDTO> getAll();

    StudentDTO create(StudentDTO studentDTO);
}
