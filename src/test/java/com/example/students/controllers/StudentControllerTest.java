package com.example.students.controllers;

import com.example.students.dtos.StudentDTO;
import com.example.students.services.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static java.time.LocalDate.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    private StudentController controller;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StudentService service;

    final int EXISTING_ID = 1;

    private final ObjectMapper mapper = new ObjectMapper();

    List<StudentDTO> students = new ArrayList<>();

    StudentDTO student;

    @BeforeEach
    private void setup() {
        Set<String> classes = new HashSet<>(Arrays.asList("HISTORY", "ART"));

        student = StudentDTO.builder().id(1).name("foo").email("bar@gmail.com").birthday(now()).classesEnum(classes).build();

        students = Arrays.asList(student);
    }

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(controller);
    }

    @Test
    public void shouldReturnListOfStudents() throws Exception {
        when(service.getAll()).thenReturn(students);

        mvc.perform(get("/students"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("foo"))
                .andExpect(jsonPath("$[0].email").value("bar@gmail.com"))
                .andExpect(jsonPath("$[0].classesEnum").isArray());
    }

    @Test
    public void shouldCreateAndReturnAStudent() throws Exception {
        when(service.create(student)).thenReturn(student);
        mapper.findAndRegisterModules();
        mvc.perform(post("/students").content(mapper.writeValueAsString(student)).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("foo"))
                .andExpect(jsonPath("$.email").value("bar@gmail.com"))
                .andExpect(jsonPath("$.classesEnum").isArray());
    }

    @Test
    public void shouldDeleteByIdAndReturnNull() throws Exception {
        willDoNothing().given(service).deleteById(EXISTING_ID);

        mvc.perform(delete("/students/" + EXISTING_ID))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
