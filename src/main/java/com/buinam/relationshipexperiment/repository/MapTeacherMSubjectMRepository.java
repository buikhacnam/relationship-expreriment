package com.buinam.relationshipexperiment.repository;

import com.buinam.relationshipexperiment.model.MapTeacherMSubjectM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MapTeacherMSubjectMRepository extends JpaRepository<MapTeacherMSubjectM, Long> {


    void deleteAllByTeacherMId(Long teacherId);

    List<MapTeacherMSubjectM> findByTeacherMId(Long id);

    void deleteAllBySubjectMId(Long id);

    MapTeacherMSubjectM findBySubjectMId(Long id);
}
