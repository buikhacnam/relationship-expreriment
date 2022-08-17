package com.buinam.relationshipexperiment.repository;

import com.buinam.relationshipexperiment.model.MapStudentMSubjectM;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MapStudentMSubjectMRepository extends JpaRepository<MapStudentMSubjectM, Long> {


    List<MapStudentMSubjectM> findByStudentMId(Long id);

    List<MapStudentMSubjectM> findBySubjectMId(Long id);
}
