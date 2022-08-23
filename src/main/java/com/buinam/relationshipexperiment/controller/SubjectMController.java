package com.buinam.relationshipexperiment.controller;


import com.buinam.relationshipexperiment.dto.SubjectMDTO;
import com.buinam.relationshipexperiment.model.MapStudentMSubjectM;
import com.buinam.relationshipexperiment.model.StudentM;
import com.buinam.relationshipexperiment.model.SubjectM;
import com.buinam.relationshipexperiment.repository.MapStudentMSubjectMRepository;
import com.buinam.relationshipexperiment.repository.StudentMRepository;
import com.buinam.relationshipexperiment.repository.SubjectMRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/subject-m")
@Transactional
public class SubjectMController {

    @Autowired
    private SubjectMRepository subjectMRepository;
    @Autowired
    private StudentMRepository studentMRepository;
    @Autowired
    private MapStudentMSubjectMRepository mapStudentMSubjectMRepository;

    @PostMapping("/save")
    public SubjectMDTO save(@RequestBody SubjectMDTO subjectMDTO) {
        try {
            // check if the subject has existed in the database
            // Is there a better way to fallback a value of null to 0L?
            Long id = subjectMDTO.getId();
            if (id == null) {
                id = 0L;
            }
            // optional is more suitable when findById only
            Optional<SubjectM>  subjectMOptional = subjectMRepository.findById(id);
            SubjectM subjectM = subjectMOptional.orElse(new SubjectM());
            // this will copy all properties from subjectMDTO to subjectM except id.(actually it's ok to copy id)
            // but if the properties in subjectMDTO dont exist in subjectM, it will not copy them ????
            BeanUtils.copyProperties(subjectMDTO, subjectM, "id" );
            subjectMRepository.save(subjectM);

            //save many-to-many relationship in MapStudentMSubjectM table

            // what if the students already in the map -> it will be double right?
            if(subjectMDTO.getStudents() != null) {

                // check if this is the update action -> delete all rows that has subjectMid in mapStudentMSubjectM table:

                if (subjectMDTO.getId() != null) {
                    System.out.println("id removed: "+ subjectMDTO.getId());
                    mapStudentMSubjectMRepository.deleteAllBySubjectMId(subjectMDTO.getId());
                }

                subjectMDTO.getStudents().forEach(studentId -> {
                    MapStudentMSubjectM mapStudentMSubjectM = new MapStudentMSubjectM();
                    mapStudentMSubjectM.setSubjectMId(subjectM.getId());
                    mapStudentMSubjectM.setStudentMId(studentId);
                    mapStudentMSubjectMRepository.save(mapStudentMSubjectM);
                });
            }

            return subjectMDTO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //TODO: get all subjects with students enrolled
    @GetMapping("/all")
    public List<SubjectMDTOFull> getAllSubject() {
        try {
            List<SubjectM> subjects = subjectMRepository.findAll();
            List<SubjectMDTOFull> result = subjects.stream().map(s -> {
                SubjectMDTOFull subjectMDTOFull = new SubjectMDTOFull();
                BeanUtils.copyProperties(s, subjectMDTOFull);
                System.out.println("subjectMDTO: "+ subjectMDTOFull);
                Long id = s.getId();
                System.out.println("id "+ id);
                // get all items that has subjectmid = id
                List<MapStudentMSubjectM> mapStudentMSubjectList = mapStudentMSubjectMRepository.findBySubjectMId(id);

                if(mapStudentMSubjectList != null) {
                    System.out.println("mapStudentMSubjectList: " + mapStudentMSubjectList);
                    List<StudentM> studentList = new ArrayList<>();
                    mapStudentMSubjectList.forEach(mapItem -> {
                       Long studentId = mapItem.getStudentMId();
                       Optional<StudentM> studentMOptional = studentMRepository.findById(studentId);
                       if(studentMOptional.isPresent()) {
                           studentList.add(studentMOptional.get());
                       }
                    });

                    System.out.println("Students found: " + studentList);
                    subjectMDTOFull.setStudents(studentList);
                }

                return subjectMDTOFull;

            }).collect(Collectors.toList());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @GetMapping("/test")
    public void test() {
        try {

                List<MapStudentMSubjectM> mapStudentMSubjectList = mapStudentMSubjectMRepository.findByStudentMId(7L);

                if(mapStudentMSubjectList != null) {
                    System.out.println("mapStudentMSubjectList: " + mapStudentMSubjectList);
                }

        } catch (Exception e) {
            e.printStackTrace();
//            return null;
        }
    }
}


@Data
@NoArgsConstructor
@AllArgsConstructor
class SubjectMDTOFull {
    private Long id;
    private String name;
    private List<StudentM> students;
}