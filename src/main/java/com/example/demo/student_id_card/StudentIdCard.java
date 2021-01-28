package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Student_id_card")
@Table(name = "Student_id_card", uniqueConstraints = {@UniqueConstraint(name = "card_number", columnNames = "card_number")})
public class StudentIdCard {
    @Id
    @Column(unique = true, updatable = false, nullable = false)
    @SequenceGenerator(name = "student_id_card_sequence", allocationSize = 1, sequenceName = "student_id_card_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_card_sequence")
    private Integer id;

    @Column(name = "card_number", columnDefinition = "TEXT", nullable = false)
    private UUID card_number;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentIdCard(Integer id, UUID card_number) {
        this.id = id;
        this.card_number = card_number;
    }

    public StudentIdCard() {

    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getCard_number() {
        return card_number;
    }

    public void setCard_number(UUID card_number) {
        this.card_number = card_number;
    }
}
