package com.example.students.services.implementations;

import com.example.students.dtos.StudentDTO;
import com.example.students.models.ClassEnum;
import com.example.students.models.Student;
import com.example.students.repositories.StudentRepository;
import com.example.students.services.StudentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDTO> getAll() {
        return studentRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    private StudentDTO toDto(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .birthday(student.getBirthday())
                .classesEnum(student.getClassesEnum().stream().map(ClassEnum::name).collect(Collectors.toSet()))
                .build();
    }


}
