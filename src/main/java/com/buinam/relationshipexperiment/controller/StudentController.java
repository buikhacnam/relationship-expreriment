package com.buinam.relationshipexperiment.controller;


import com.buinam.relationshipexperiment.dto.StudentDTO;
import com.buinam.relationshipexperiment.model.Student;
import com.buinam.relationshipexperiment.repository.StudentRepository;
import com.buinam.relationshipexperiment.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentRepository studentRepository;

    private final SubjectRepository subjectRepository;

    @GetMapping("/all")
    List<StudentDTO> getStudentsWithSubjects() {

        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentEnrolledDTOList = new ArrayList<>();
        students.forEach(student -> {
            StudentDTO result = new StudentDTO();
           BeanUtils.copyProperties(student, result);
            studentEnrolledDTOList.add(result);
        });

        return studentEnrolledDTOList;


    }



    @PostMapping("/save")
    Student createStudent(@RequestBody Student student) {
        return studentRepository.save(student);
    }
}
