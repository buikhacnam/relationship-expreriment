package com.buinam.relationshipexperiment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table
// a subject is going to have many students and
// a student is going to have many subjects
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Boolean gay;
    @JsonIgnore
    @ManyToMany(mappedBy = "enrolledStudents")
    private Set<Subject> subjects = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

    public Boolean getGay() {
        return gay;
    }

    public void setGay(Boolean gay) {
        this.gay = gay;
    }

    public void setSubjects(Set<Subject> subjects) {
        this.subjects = subjects;
    }
}
