package com.buinam.relationshipexperiment.dto;

import com.buinam.relationshipexperiment.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TeacherDTO {
    private Long id;
    private String name;
    private Set<Subject> subjects = new HashSet<>();
}
