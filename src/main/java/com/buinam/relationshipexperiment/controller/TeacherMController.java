package com.buinam.relationshipexperiment.controller;

import com.buinam.relationshipexperiment.dto.TeacherMDTO;
import com.buinam.relationshipexperiment.model.MapTeacherMSubjectM;
import com.buinam.relationshipexperiment.model.SubjectM;
import com.buinam.relationshipexperiment.model.TeacherM;
import com.buinam.relationshipexperiment.repository.MapTeacherMSubjectMRepository;
import com.buinam.relationshipexperiment.repository.SubjectMRepository;
import com.buinam.relationshipexperiment.repository.TeacherMRepository;
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
    MapTeacherMSubjectMRepository mapTeacherMSubjectMRepository;

    @Autowired
    SubjectMRepository subjectMRepository;

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
                List<MapTeacherMSubjectM> mapTeacherMSubjectMList = mapTeacherMSubjectMRepository.findByTeacherMId(teacherM.getId());
                List<SubjectM> subjectMList = new ArrayList<>();
                mapTeacherMSubjectMList.forEach(mapTeacherMSubjectM -> {
                    Optional<SubjectM> subjectMOptional = subjectMRepository.findById(mapTeacherMSubjectM.getSubjectMId());
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
                    mapTeacherMSubjectMRepository.deleteAllByTeacherMId(TeacherId);
                }
                teacherMDTO.getSubjectsId().forEach(subjectMId -> {
                    MapTeacherMSubjectM mapTeacherMSubjectM = new MapTeacherMSubjectM();
                    mapTeacherMSubjectM.setTeacherMId(teacherM.getId());
                    mapTeacherMSubjectM.setSubjectMId(subjectMId);
                    mapTeacherMSubjectMRepository.save(mapTeacherMSubjectM);
                });
            }
            return teacherMDTO;

        } catch (Exception e) {
            System.out.println(e);
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