package com.buinam.relationshipexperiment.controller;


import com.buinam.relationshipexperiment.dto.TeacherDTO;
import com.buinam.relationshipexperiment.model.Teacher;
import com.buinam.relationshipexperiment.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherRepository teacherRepository;

    @GetMapping("/all")
    public List<TeacherDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        TeacherDTO teacherDTO = new TeacherDTO();
        List<TeacherDTO> teacherDTOList = new ArrayList<>();
        teachers.forEach(teacher -> {
            BeanUtils.copyProperties(teacher, teacherDTO);
            teacherDTOList.add(teacherDTO);
        });
        return teacherDTOList;
    }

    @GetMapping("/all1")
    public List<Teacher> getAllTeachers1() {
        List<Teacher> teachers = teacherRepository.findAll();

        return teachers;
    }

    @PostMapping("/save")
    public Teacher createTeacher(@RequestBody  Teacher teacher) {
        System.out.println(teacher.getId());
        System.out.println(teacher.getName());
        return teacherRepository.save(teacher);
    }
}
