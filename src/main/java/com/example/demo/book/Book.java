package com.example.demo.book;

import com.example.demo.student_id_card.StudentIdCard;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "book")
@Table(name = "book", uniqueConstraints = {@UniqueConstraint(name = "book_uuid", columnNames = "uuid")})
@NoArgsConstructor
public class Book {
    @Id
    @SequenceGenerator(name = "book_sequence", allocationSize = 1, sequenceName = "book_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @Column(updatable = false, nullable = false)
    @Getter(onMethod = @__(@JsonIgnore)) // generate the getter with the specific annotation.
    @Setter
    private Integer id;

    @Column(nullable = false, columnDefinition = "UUID", name = "uuid")
    @Getter
    @Setter
    private UUID uuid;

    @Column(name = "title", nullable = false)
    @Getter
    @Setter
    private String title;

    @Column(name = "genre", nullable = false)
    @Getter
    @Setter
    private String genre;

    @Column(name = "totalPages", nullable = false)
    @Getter
    @Setter
    private Integer totalPages;

    @Column(name = "publisher", nullable = false)
    @Getter
    @Setter
    private String publisher;

    @Column(name = "author", nullable = false)
    @Getter
    @Setter
    private String author;

    @Column(name = "deleted_at", columnDefinition = "DATE")
    @Getter
    @Setter
    private LocalDate deleted_at;

    @Column(name = "created_at", nullable = false, columnDefinition = "Date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull
    @Getter(onMethod = @__(@JsonIgnore)) // generate the getter with the specific annotation.
    @Setter
    private LocalDate created_at;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "studentUuidCard", referencedColumnName = "uuid")
    @Getter(onMethod = @__(@JsonIgnore)) // generate the getter with the specific annotation.
    @Setter
    private StudentIdCard studentIdCard;

    @JsonCreator
    public Book(@JsonProperty("uuid") UUID uuid, @JsonProperty("title") String title, @JsonProperty("genre") String genre, @JsonProperty("totalPages") Integer totalPages, @JsonProperty("publisher") String publisher, @JsonProperty("author") String author, @JsonProperty("created_at") LocalDate created_at) {
        this.uuid = uuid;
        this.title = title;
        this.genre = genre;
        this.totalPages = totalPages;
        this.publisher = publisher;
        this.author = author;
        this.created_at = created_at;
    }
}
