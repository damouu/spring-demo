package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student dede = new Student(
                    1L,
                    "dede",
                    LocalDate.of(2000, Month.JULY, 5),
                    "saladeTomateOgnionsPiments"
            );
            studentRepository.saveAndFlush(dede);
        };
    }
}
