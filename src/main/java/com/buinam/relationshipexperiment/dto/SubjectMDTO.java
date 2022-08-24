package com.buinam.relationshipexperiment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectMDTO {
    private Long id;
    private String name;
    private List<Long> students;
    private Long teacherId;
}
