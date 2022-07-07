package com.example.students.services.implementations;

import com.example.students.dtos.StudentDTO;
import com.example.students.exceptions.ResourceNotFoundException;
import com.example.students.models.ClassEnum;
import com.example.students.models.Student;
import com.example.students.repositories.StudentRepository;
import com.example.students.services.StudentService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import static java.time.LocalDateTime.*;

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

    @Override
    public StudentDTO create(StudentDTO studentDTO) {
        Student student = toEntity(studentDTO);
        student.setCreatedAt(now());
        return toDto(studentRepository.save(student));
    }

    @Override
    public void deleteById(Integer id) {
        studentRepository.findById(id).ifPresentOrElse(student -> studentRepository.deleteById(id), () -> new ResourceNotFoundException("id #" + id + " not found"));
    }

    private StudentDTO toDto(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .birthday(student.getBirthday())
                .classesEnum(student.getClassesEnum().stream().map(ClassEnum::name).collect(Collectors.toSet()))
                .build();
    }

    private Student toEntity(StudentDTO studentDTO) {
        return Student.builder()
                .id(studentDTO.getId())
                .name(studentDTO.getName())
                .birthday(studentDTO.getBirthday())
                .email(studentDTO.getEmail())
                .classesEnum(studentDTO.getClassesEnum().stream().map(ClassEnum::valueOf).collect(Collectors.toSet()))
                .build();
    }
}
