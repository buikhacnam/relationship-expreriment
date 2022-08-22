package com.buinam.relationshipexperiment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table

// ManyToMany
// a subject is going to have many students and
// a student is going to have many subjects

// a teacher is going to have many subjects
// a subject is going to have one teacher
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;


    @ManyToMany
    @JoinTable(
            name="student_enrolled",
            joinColumns = @JoinColumn(name="subject_id"),
            inverseJoinColumns = @JoinColumn(name="student_id")
    )
//    @JsonIgnore
    private Set<Student> enrolledStudents = new HashSet<>();


    public void enrollStudent(Student student) {
        enrolledStudents.add(student);
    }


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

    public Set<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(Set<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

}
