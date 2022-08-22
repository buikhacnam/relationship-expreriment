package com.buinam.relationshipexperiment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDTO {
    private Long id;
    private String name;
    private Set<Long> students = new HashSet<>();
}

