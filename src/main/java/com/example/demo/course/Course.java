package com.example.demo.course;

import com.example.demo.student.Student;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)    //  ignore all null fields
@Entity(name = "course")
@Table(name = "course", uniqueConstraints = {@UniqueConstraint(name = "course_name_unique", columnNames = "name")})
public class Course {
    @Id
    @SequenceGenerator(name = "course_sequence", allocationSize = 1, sequenceName = "course_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    @Column(updatable = false)
    private Integer id;

    @Column(nullable = false, name = "name", columnDefinition = "TEXT")
    @NotNull
    private String name;


    @Column(nullable = false, name = "department", columnDefinition = "TEXT")
    @NotNull
    private String department;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "courses")
    private final Set<Student> students = new HashSet<>();

    public Course(@JsonProperty("id") Integer id, @NotNull @JsonProperty("name") String name, @NotNull @JsonProperty("department") String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public Course() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
