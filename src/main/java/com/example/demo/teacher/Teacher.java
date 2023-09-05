package com.example.demo.teacher;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)    //  ignore all null fields
@Entity(name = "teacher")
@Table(name = "teacher", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "email"}, name = "teacher_email_unique"), @UniqueConstraint(columnNames = {"uuid", "uuid"}, name = "teacher_uuid_unique")})
@NoArgsConstructor
public class Teacher {
    @Id
    @SequenceGenerator(name = "teacher_sequence", allocationSize = 1, sequenceName = "teacher_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_sequence")
    @Column(updatable = false)
    @Getter(onMethod = @__(@JsonIgnore))
    @Setter
    private Integer id;

    @Column(nullable = false, columnDefinition = "UUID", name = "uuid")
    @Getter
    @Setter
    //the getter and setter annotations, exclude us to write manually the actual, getters and setters for this property.
    private UUID uuid;

    @Column(nullable = false, name = "name")
    @NotNull
    @Getter
    @Setter
    //the getter and setter annotations, exclude us to write manually the actual, getters and setters for this property.
    private String name;

    @Column(nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Getter
    @Setter
    private LocalDate dob;
    @Column(nullable = false, name = "gender", columnDefinition = "TEXT")
    @NotNull
    @Getter
    @Setter
    private String gender;

    @Column(nullable = false, columnDefinition = "TEXT")
    @NotNull
    @Email
    @Getter
    @Setter
    private String email;

    @Column(nullable = true)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @Getter
    @Setter
    private LocalDate deleted_at;

    @JsonCreator
    public Teacher(UUID uuid, String name, LocalDate dob, String gender, String email) {
        this.uuid = uuid;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.email = email;
    }
}
