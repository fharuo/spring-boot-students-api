package com.example.students.services;

import com.example.students.dtos.StudentDTO;

import java.util.List;

public interface StudentService {
    List<StudentDTO> getAll();
}
