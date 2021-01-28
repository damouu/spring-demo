package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

@Configuration
public class StudentIdCardCommandRunner {

    @Bean
    CommandLineRunner runner(Serializable serializable, StudentRepository studentRepository) {
        return args -> {
            for (int i = 1; i < 7; i++) {
                StudentIdCard student_idCard = new StudentIdCard(
                        i,
                        UUID.randomUUID()
                );
                serializable.save(student_idCard);
            }
            Faker faker = new Faker();
            StudentIdCard studentIdCard = new StudentIdCard(55, UUID.randomUUID());
            Student student = new Student(55, "BILAL",
                    LocalDate.of(faker.number().numberBetween(1900, 2021), Month.NOVEMBER, faker.number().numberBetween(1, 31)),
                    faker.internet().emailAddress());
            studentIdCard.setStudent(student);
            serializable.save(studentIdCard);
        };
    }
}
