package com.buinam.relationshipexperiment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
public class MapTeacherMSubjectM {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long teacherMId;
    private Long subjectMId;
}
