package com.buinam.relationshipexperiment.repository;

import com.buinam.relationshipexperiment.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
