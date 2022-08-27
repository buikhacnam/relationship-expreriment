package com.buinam.relationshipexperiment.controller;

import com.buinam.relationshipexperiment.dto.TeacherMDTO;
import com.buinam.relationshipexperiment.model.*;
import com.buinam.relationshipexperiment.repository.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/api/teacher-m")
public class TeacherMController {
    @Autowired
    TeacherMRepository teacherMRepository;

    @Autowired
    MapOneTeacherMManySubjectMRepository mapOneTeacherMManySubjectMRepository;

    @Autowired
    SubjectMRepository subjectMRepository;

    @Autowired
    MapStudentMSubjectMRepository mapStudentMSubjectMRepository;

    @Autowired
    StudentMRepository studentMRepository;

    @GetMapping("/all")
    public List<TeacherMFullDTO> findAll() {
        // find all teachersM
        // loop through them and find all teachers in MapTeacherMSubjectM
        // loop through them and find all subjects by subject id
        // return TeacherMFullDTO
        try {
            List<TeacherM> teachersM = teacherMRepository.findAll();
            List<TeacherMFullDTO> teacherMFullDTOList = new ArrayList<>();
            teachersM.forEach(teacherM -> {
                TeacherMFullDTO result = new TeacherMFullDTO();
                BeanUtils.copyProperties(teacherM, result);
                List<MapOneTeacherMManySubjectM> mapOneTeacherMManySubjectMList = mapOneTeacherMManySubjectMRepository.findByTeacherMId(teacherM.getId());
                List<SubjectM> subjectMList = new ArrayList<>();
                mapOneTeacherMManySubjectMList.forEach(mapOneTeacherMManySubjectM -> {
                    Optional<SubjectM> subjectMOptional = subjectMRepository.findById(mapOneTeacherMManySubjectM.getSubjectMId());
                    if(subjectMOptional.isPresent()) {
                        subjectMList.add(subjectMOptional.get());
                    }

                } );
                result.setSubjects(subjectMList);
                teacherMFullDTOList.add(result);
            } );
            return teacherMFullDTOList;
        } catch (Exception e) {
            return null;
        }
    }


    @PostMapping("/save")
    public TeacherMDTO save(@RequestBody TeacherMDTO teacherMDTO) {
        // save teacher in TeacherM
        // save subjects id in MapTeacherMSubjectM
        try {
            Long TeacherId = teacherMDTO.getId() == null ? 0L : teacherMDTO.getId();
            Optional<TeacherM> teacherMOptional = teacherMRepository.findById(TeacherId);
            TeacherM teacherM = teacherMOptional.orElse(new TeacherM());
            BeanUtils.copyProperties(teacherMDTO, teacherM);
            teacherMRepository.save(teacherM);

            if (teacherMDTO.getSubjectsId() != null) {
                // if TeacherId != null -> delete all subjects of teacher before save new subjects
                if(TeacherId != 0L) {
                    mapOneTeacherMManySubjectMRepository.deleteAllByTeacherMId(TeacherId);
                }
                teacherMDTO.getSubjectsId().forEach(subjectMId -> {
                    MapOneTeacherMManySubjectM mapOneTeacherMManySubjectM = new MapOneTeacherMManySubjectM();
                    mapOneTeacherMManySubjectM.setTeacherMId(teacherM.getId());
                    mapOneTeacherMManySubjectM.setSubjectMId(subjectMId);
                    mapOneTeacherMManySubjectMRepository.save(mapOneTeacherMManySubjectM);
                });
            }
            return teacherMDTO;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    /**
     * /everything
     * find all TeachersM -> convert to TeacherEveryThingDTO
     * Loop through List<TeacherM> to find and find all subjects of each teacher
     * Loop through List<SubjectM> and find all students of each subject
     */
    @GetMapping("/everything")
    public List<TeacherEveryThingDTO> findEveryThing() {

        try {
            List<TeacherM> teachersM = teacherMRepository.findAll();
            List<TeacherEveryThingDTO> teacherMFullDTOList = new ArrayList<>();
            teachersM.forEach(teacherM -> {
                TeacherEveryThingDTO result = new TeacherEveryThingDTO();
                BeanUtils.copyProperties(teacherM, result);
                List<MapOneTeacherMManySubjectM> mapOneTeacherMManySubjectMList = mapOneTeacherMManySubjectMRepository.findByTeacherMId(teacherM.getId());
                List<SubjectMDTOFull> subjectMDTOFullList = new ArrayList<>();
                mapOneTeacherMManySubjectMList.forEach(mapOneTeacherMManySubjectM -> {
                    Optional<SubjectM> subjectMOptional = subjectMRepository.findById(mapOneTeacherMManySubjectM.getSubjectMId());
                    if(subjectMOptional.isPresent()) {
                        // find all students in mapStudentMSubjectM
                        SubjectMDTOFull subjectMDTOFull = new SubjectMDTOFull();
                        BeanUtils.copyProperties(subjectMOptional.get(), subjectMDTOFull);
                        List<MapStudentMSubjectM> mapStudentMSubjectM = mapStudentMSubjectMRepository.findBySubjectMId(subjectMOptional.get().getId());
                        if (mapStudentMSubjectM != null) {
                            List<StudentM> studentMDTOList = new ArrayList<>();
                            mapStudentMSubjectM.forEach(mapItem -> {
                                Optional<StudentM> studentMOptional = studentMRepository.findById(mapItem.getStudentMId());
                                if (studentMOptional.isPresent()) {
                                    studentMDTOList.add(studentMOptional.get());
                                }
                            });
                            subjectMDTOFull.setStudents(studentMDTOList);
                            subjectMDTOFull.setTeacher(teacherM);
                        }
                        subjectMDTOFullList.add(subjectMDTOFull);
                    }

                } );
                result.setSubjects(subjectMDTOFullList);
                teacherMFullDTOList.add(result);
            } );
            return teacherMFullDTOList;
        } catch (Exception e) {
            return null;
        }
    }
}


@NoArgsConstructor
@AllArgsConstructor
@Data
class TeacherMFullDTO {
    private Long id;
    private String name;
    private List<SubjectM> subjects;
}

@NoArgsConstructor
@AllArgsConstructor
@Data
class TeacherEveryThingDTO {
    private Long id;
    private String name;
    private List<SubjectMDTOFull> subjects;
}