package com.example.demo.book;

import com.example.demo.student.Student;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "Book")
@Table(name = "books")
public class Book {
    @Id
    @SequenceGenerator(name = "book_sequence", allocationSize = 1, sequenceName = "book_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @Column(updatable = false, nullable = false)
    private Integer id;
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
    private LocalDate created_at;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Book(Integer id, String title, String genre, Integer totalPages, String publisher, String author, LocalDate created_at) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.totalPages = totalPages;
        this.publisher = publisher;
        this.author = author;
        this.created_at = created_at;
    }

    public Book() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
