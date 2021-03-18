package com.example.demo.student_id_card;

import com.example.demo.book.Book;
import com.example.demo.student.Student;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "Student_id_card")
@Table(name = "Student_id_card", uniqueConstraints = @UniqueConstraint(columnNames = {"uuid", "uuid"}, name = "student_card_uuid_unique"))
@NoArgsConstructor
public class StudentIdCard implements Serializable {
    @Id
    @Column(unique = true, updatable = false, nullable = false)
    @SequenceGenerator(name = "student_id_card_sequence", allocationSize = 1, sequenceName = "student_id_card_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_card_sequence")
    @Getter(onMethod = @__(@JsonIgnore)) // generate the getter with the specific annotation.
    @Setter
    private Integer id;

    @Column(name = "uuid", columnDefinition = "UUID", nullable = false)
    @NotNull
    @Getter
    @Setter
    private UUID uuid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @Getter(onMethod = @__(@JsonIgnore)) // generate the getter with the specific annotation.
    @Setter
    private Student student;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "studentIdCard", fetch = FetchType.EAGER)
    @Getter(onMethod = @__(@JsonIgnore)) // generate the getter with the specific annotation.
    @Setter
    private Set<Book> books;

    @JsonCreator
    public StudentIdCard(@JsonProperty("id") Integer id, @JsonProperty("uuid") UUID uuid) {
        this.id = id;
        this.uuid = uuid;
    }
}
