package com.example.demo.course;

import com.example.demo.student.Student;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)    //  ignore all null fields
@Entity(name = "course")
@Table(name = "course", uniqueConstraints = {@UniqueConstraint(columnNames = {"uuid", "uuid"}, name = "uuid")})
public class Course {
    @Id
    @SequenceGenerator(name = "course_sequence", allocationSize = 1, sequenceName = "course_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    @Column(updatable = false)
    private Integer id;

    @Column(name = "uuid", nullable = false, columnDefinition = "UUID")
    @NotNull
    private UUID uuid;

    @Column(nullable = false, name = "name", columnDefinition = "TEXT")
    @NotNull
    private String name;


    @Column(nullable = false, name = "campus", columnDefinition = "TEXT")
    @NotNull
    private String campus;

    @Column(nullable = false, name = "university", columnDefinition = "TEXT")
    @NotNull
    private String university;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.ALL
            },
            mappedBy = "courses")
    protected Set<Student> students = new HashSet<>();

    @JsonCreator
    public Course(@JsonProperty("id") Integer id, @JsonProperty("uuid") UUID uuid, @JsonProperty("name") String name, @JsonProperty("campus") String campus, @JsonProperty("university") String university) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.campus = campus;
        this.university = university;
    }

    public Course() {
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getcampus() {
        return campus;
    }

    public void setcampus(String campus) {
        this.campus = campus;
    }

    public String getuniversity() { return university; }

    public void setuniversity(String university) { this.university = university; }

    @JsonIgnore
    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
