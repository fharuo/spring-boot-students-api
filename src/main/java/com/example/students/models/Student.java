package com.example.students.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@Table(name = "students")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private LocalDate birthday;

    private String email;

    @ElementCollection(targetClass = ClassEnum.class)
    @Enumerated(EnumType.STRING)
    private Set<ClassEnum> classesEnum;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
