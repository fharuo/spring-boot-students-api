package com.example.students.dtos;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class StudentDTO {
    private Integer id;

    private String name;

    private LocalDate birthday;

    private String email;

    private Set<String> classesEnum;

}
