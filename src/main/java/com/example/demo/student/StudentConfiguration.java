package com.example.demo.student;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

@Configuration
public class StudentConfiguration {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Faker faker = new Faker();
            for (int i = 1; i < 50; i++) {
                Student student = new Student(
                        UUID.randomUUID(),
                        faker.name().name(),
                        LocalDate.of(faker.number().numberBetween(1900, 2021), Month.NOVEMBER, faker.number().numberBetween(1, 31)),
                        faker.internet().emailAddress()
                );
                studentRepository.save(student);
            }
        };
    }
}
