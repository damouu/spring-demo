package com.example.demo.student;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity(name = "student")
@Table(name = "student", uniqueConstraints = {@UniqueConstraint(name = "student_email_unique", columnNames = "email")})
public class Student {
    @Id
    @SequenceGenerator(name = "student_sequence", allocationSize = 1, sequenceName = "student_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @Column(updatable = false)
    private Integer id;
    @Column(nullable = false, name = "name")
    private String name;
    @Column(nullable = false)
    private LocalDate dob;
    @Transient
    private Integer age;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String email;

    public Student(Integer id, String name, LocalDate dob, String email) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
    }

    public Student() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = Math.toIntExact(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Integer getAge() {
        return Period.between(this.dob, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
