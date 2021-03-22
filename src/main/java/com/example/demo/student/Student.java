package com.example.demo.student;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)    //  ignore all null fields
@Entity(name = "student")
@Table(name = "student", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email", "email"}, name = "student_email_unique"),
        @UniqueConstraint(columnNames = {"uuid", "uuid"}, name = "student_uuid_unique")
})
@NoArgsConstructor
public class Student {
    @Id
    @SequenceGenerator(name = "student_sequence", allocationSize = 1, sequenceName = "student_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    @Column(updatable = false)
    @Getter(onMethod = @__(@JsonIgnore)) // generate the getter with the specific annotation.
    @Setter
    private Integer id;

    @Column(nullable = false, columnDefinition = "UUID", name = "uuid")
    @Getter(onMethod = @__(@JsonIgnore)) // generate the getter with the specific annotation.
    @Setter
    private UUID uuid;

    @Column(nullable = false, name = "name")
    @NotNull
    @Getter
    @Setter
    private String name;

    @Column(nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull
    @Getter
    @Setter
    private LocalDate dob;

    @Transient
    @Getter
    @Setter
    private Integer age;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotNull
    @Email
    @Getter
    @Setter
    private String email;

    @OneToOne(mappedBy = "student")
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
    private StudentIdCard studentIdCard;


    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.ALL
            })
    @JoinTable(name = "course_student",
            joinColumns = {@JoinColumn(name = "student_id")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")})
    @Getter(onMethod = @__(@JsonIgnore)) // generate the getter with the specific annotation.
    @Setter
    protected Set<Course> courses = new HashSet<>();


    @JsonCreator
    public Student(@JsonProperty("uuid") UUID uuid, @JsonProperty("name") String name, @JsonProperty("dob") LocalDate dob, @JsonProperty("email") String email) {
        this.uuid = uuid;
        this.name = name;
        this.dob = dob;
        this.email = email;
    }
}
