package com.buinam.relationshipexperiment.controller;

import com.buinam.relationshipexperiment.dto.SubjectDTO;
import com.buinam.relationshipexperiment.model.Student;
import com.buinam.relationshipexperiment.model.Subject;
import com.buinam.relationshipexperiment.model.Teacher;
import com.buinam.relationshipexperiment.repository.StudentRepository;
import com.buinam.relationshipexperiment.repository.SubjectRepository;
import com.buinam.relationshipexperiment.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/subject")
@RequiredArgsConstructor
@Transactional
public class SubjectController {

    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    private final TeacherRepository teacherRepository;

    @GetMapping("/all")
    List<Subject> getSubjects() {
        //possible error when delete a teacher from db: Unable to find com.buinam.relationshipexperiment.model.Teacher with id 42; nested exception is javax.persistence.
        return subjectRepository.findAll();
    }

    @PostMapping("/save")
    Subject createSubject(@RequestBody SubjectDTO subjectDTO) {
        // TODO: update subject method
        try {
            Long subjectId = subjectDTO.getId() == null ? 0L : subjectDTO.getId();
            Optional<Subject> subjectOptional = subjectRepository.findById(subjectId);
            Subject subject = subjectOptional.orElse(new Subject());
            BeanUtils.copyProperties(subjectDTO, subject);

            //@manytomany with students
            if(subjectDTO.getStudents() != null) {
                if(subjectId != 0L) {
                    // clear all elements in a hashset
                    subject.getEnrolledStudents().clear();
                }
                subjectDTO.getStudents().forEach(studentId -> {
                    Optional<Student> studentOptional = studentRepository.findById(studentId);
                    if (studentOptional.isPresent()) {
                        subject.enrollStudent(studentOptional.get());
                    }

                });
            }

            //@manytoone with teacher
            if(subjectDTO.getTeacherId() != null) {
                Optional<Teacher> teacherOptional = teacherRepository.findById(subjectDTO.getTeacherId());
                if(teacherOptional.isPresent()) {
                    subject.setCoach(teacherOptional.get());
                }
            }

            return subjectRepository.save(subject);
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PutMapping("/{subjectId}/students/{studentId}")
    Subject enrollStudentToSubject(@PathVariable Long subjectId, @PathVariable Long studentId) {
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student not found"));
        System.out.println(subject.getName());
        System.out.println(student.getName());
        subject.enrollStudent(student);

        return subjectRepository.save(subject);
    }

//    @PutMapping("/{subjectId}/teacher/{teacherId}")
//    Subject assignTeacherToSubject(@PathVariable Long subjectId, @PathVariable Long teacherId) {
//        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new IllegalArgumentException("Subject not found"));
//        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(() -> new IllegalArgumentException("teacher not found"));
//
//        subject.assignTeacher(teacher);
//
//        return subjectRepository.save(subject);
//    }

    // delete subject
    @DeleteMapping("/{subjectId}")
    void deleteSubject(@PathVariable Long subjectId) {
        subjectRepository.deleteById(subjectId);
    }
}
