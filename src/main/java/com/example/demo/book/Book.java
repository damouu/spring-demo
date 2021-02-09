package com.example.demo.book;

import com.example.demo.student.Student;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "book")
@Table(name = "book", uniqueConstraints = {@UniqueConstraint(name = "book_uuid", columnNames = "uuid")})
public class Book {
    @Id
    @SequenceGenerator(name = "book_sequence", allocationSize = 1, sequenceName = "book_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @Column(updatable = false, nullable = false)
    private Integer id;
    @Column(nullable = false, columnDefinition = "UUID", name = "uuid")
    @NotNull
    private UUID uuid;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "genre", nullable = false)
    private String genre;
    @Column(name = "totalPages", nullable = false)
    private Integer totalPages;
    @Column(name = "publisher", nullable = false)
    private String publisher;
    @Column(name = "author", nullable = false)
    private String author;
    @Column(name = "created_at", nullable = false, columnDefinition = "Date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull
    private LocalDate created_at;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @JsonCreator
    public Book(@JsonProperty("id") Integer id,
                @JsonProperty("uuid") UUID uuid,
                @JsonProperty("title") String title,
                @JsonProperty("genre") String genre,
                @JsonProperty("totalPages") Integer totalPages,
                @JsonProperty("publisher") String publisher,
                @JsonProperty("author") String author,
                @JsonProperty("created_at") LocalDate created_at) {
        this.id = id;
        this.uuid = uuid;
        this.title = title;
        this.genre = genre;
        this.totalPages = totalPages;
        this.publisher = publisher;
        this.author = author;
        this.created_at = created_at;
    }

    public Book() {

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }
}
