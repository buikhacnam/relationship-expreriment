package com.buinam.relationshipexperiment.repository;

import com.buinam.relationshipexperiment.model.MapOneTeacherMManySubjectM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapOneTeacherMManySubjectMRepository extends JpaRepository<MapOneTeacherMManySubjectM, Long> {


    void deleteAllByTeacherMId(Long teacherId);

    List<MapOneTeacherMManySubjectM> findByTeacherMId(Long id);

    void deleteAllBySubjectMId(Long id);

    MapOneTeacherMManySubjectM findBySubjectMId(Long id);
}
