package com.example.demo.course;

import com.example.demo.student.Student;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

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
    @Getter(onMethod = @__(@JsonIgnore)) // generate the getter with the specific annotation.
    @Setter
    private Integer id;

    @Column(name = "uuid", nullable = false, columnDefinition = "UUID")
    @Getter
    @Setter
    private UUID uuid;

    @Column(nullable = false, name = "name", columnDefinition = "TEXT")
    @NotNull
    @Getter
    @Setter
    private String name;

    @Column(nullable = false, name = "campus", columnDefinition = "TEXT")
    @NotNull
    @Getter
    @Setter
    private String campus;

    @Column(nullable = false, name = "university", columnDefinition = "TEXT")
    @NotNull
    @Getter
    @Setter
    private String university;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.ALL
            },
            mappedBy = "courses")
    @Getter(onMethod = @__(@JsonIgnore)) // generate the getter with the specific annotation.
    @Setter
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
}
