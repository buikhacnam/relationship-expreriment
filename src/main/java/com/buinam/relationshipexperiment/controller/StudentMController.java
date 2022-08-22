package com.buinam.relationshipexperiment.controller;

import com.buinam.relationshipexperiment.dto.StudentMDTO;
import com.buinam.relationshipexperiment.model.StudentM;
import com.buinam.relationshipexperiment.repository.MapStudentMSubjectMRepository;
import com.buinam.relationshipexperiment.repository.StudentMRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/api/student-m")
@Transactional
public class StudentMController {
    @Autowired
    private StudentMRepository studentMRepository;

    @Autowired
    private MapStudentMSubjectMRepository mapStudentMSubjectMRepository;

    @PostMapping("/save")
    public StudentMDTO save(@RequestBody StudentMDTO studentMDTO) {
        try {
            // check if the student has existed in the database
            Long id = studentMDTO.getId();
            if (id == null) {
                id = 0L;
            }
            StudentM studentM = studentMRepository.findById(id).orElse(new StudentM());
            BeanUtils.copyProperties(studentMDTO, studentM, "id" );
            studentMRepository.save(studentM);

            //save many-to-many relationship in MapStudentMSubjectM table
//            if(studentMDTO.getSubjects() != null) {
//                studentMDTO.getSubjects().forEach(subjectM -> {
//                    MapStudentMSubjectM mapStudentMSubjectM = new MapStudentMSubjectM();
//                    mapStudentMSubjectM.setStudentMId(studentM.getId());
//                    mapStudentMSubjectM.setSubjectMId(subjectM.getId());
//                    mapStudentMSubjectMRepository.save(mapStudentMSubjectM);
//
//                });
//            }

            return studentMDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
