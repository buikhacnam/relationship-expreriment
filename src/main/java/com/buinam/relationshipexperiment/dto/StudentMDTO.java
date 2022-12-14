package com.buinam.relationshipexperiment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentMDTO {
    private Long id;
    private String name;
    private Boolean gay;
    private List<Long> subjects;
}
