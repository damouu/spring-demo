package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Student_id_card")
@Table(name = "Student_id_card", uniqueConstraints = {@UniqueConstraint(name = "uuid", columnNames = "uuid")})
public class StudentIdCard {
    @Id
    @Column(unique = true, updatable = false, nullable = false)
    @SequenceGenerator(name = "student_id_card_sequence", allocationSize = 1, sequenceName = "student_id_card_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_card_sequence")
    private Integer id;

    @Column(name = "uuid", columnDefinition = "UUID", nullable = false)
    @NotNull
    private UUID uuid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    @JsonCreator
    public StudentIdCard(@JsonProperty("id") Integer id, @JsonProperty("uuid") UUID uuid) {
        this.id = id;
        this.uuid = uuid;
    }

    public StudentIdCard() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getuuid() {
        return uuid;
    }

    public void setuuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Student getStudent() {
        return student;
    }

    @JsonIgnore
    public void setStudent(Student student) {
        this.student = student;
    }
}
