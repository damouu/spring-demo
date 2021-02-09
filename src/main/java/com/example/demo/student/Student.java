package com.example.demo.student;

import com.example.demo.book.Book;
import com.example.demo.course.Course;
import com.example.demo.student_id_card.StudentIdCard;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)    //  ignore all null fields
@Entity(name = "student")
@Table(name = "student", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "email"}, name = "student_email_unique"),
        @UniqueConstraint(columnNames = {"uuid", "uuid"}, name = "student_uuid_unique")
})
public class Student {
    @Id
    @SequenceGenerator(name = "student_sequence", allocationSize = 1, sequenceName = "student_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @Column(updatable = false)
    private Integer id;

    @Column(nullable = false, columnDefinition = "UUID", name = "uuid")
    @NotNull
    private UUID uuid;

    @Column(nullable = false, name = "name")
    @NotNull
    private String name;

    @Column(nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull
    private LocalDate dob;

    @Transient
    private Integer age;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotNull
    @Email
    private String email;

    @OneToOne(mappedBy = "student")
    private StudentIdCard studentIdCard;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student", fetch = FetchType.EAGER)
    private Set<Book> books;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.ALL
            })
    @JoinTable(name = "course_student",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    protected Set<Course> courses = new HashSet<>();

    @JsonCreator
    public Student(@JsonProperty("id") Integer id, @JsonProperty("uuid") UUID uuid, @JsonProperty("name") String name, @JsonProperty("dob") LocalDate dob, @JsonProperty("email") String email) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
        this.dob = dob;
        this.email = email;
    }

    public Student() {
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = Math.toIntExact(id);
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

    @JsonIgnore
    public StudentIdCard getStudentIdCard() {
        return studentIdCard;
    }

    public void setStudentIdCard(StudentIdCard studentIdCard) {
        this.studentIdCard = studentIdCard;
    }

    @JsonIgnore
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @JsonIgnore
    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }
}
