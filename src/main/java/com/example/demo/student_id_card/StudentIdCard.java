package com.example.demo.student_id_card;

import com.example.demo.book.Book;
import com.example.demo.student.Student;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Student_id_card")
@Table(name = "Student_id_card", uniqueConstraints = @UniqueConstraint(columnNames = {"uuid", "uuid"}, name = "student_card_uuid_unique"))
public class StudentIdCard implements Serializable {
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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentIdCard", fetch = FetchType.EAGER)
    private Set<Book> books;

    @JsonCreator
    public StudentIdCard(@JsonProperty("id") Integer id, @JsonProperty("uuid") UUID uuid) {
        this.id = id;
        this.uuid = uuid;
    }

    public StudentIdCard() {

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

    @JsonIgnore
    public Student getStudent() {
        return student;
    }

    @JsonIgnore
    public void setStudent(Student student) {
        this.student = student;
    }

    @JsonIgnore
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
