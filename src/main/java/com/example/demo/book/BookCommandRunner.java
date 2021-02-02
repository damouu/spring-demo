package com.example.demo.book;

import com.example.demo.student.StudentRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;

@Configuration
public class BookCommandRunner {

    @Bean
    CommandLineRunner dedede(BookSerializable bookSerializable, StudentRepository studentRepository) {
        return args -> {
            Faker faker = new Faker();
            for (int i = 1; i < 100; i++) {
                Book book = new Book(
                        i,
                        faker.book().title(),
                        faker.book().genre(),
                        faker.number().numberBetween(1, 900),
                        faker.book().publisher(),
                        faker.book().author(),
                        LocalDate.of(faker.number().numberBetween(1900, 2021), Month.NOVEMBER, faker.number().numberBetween(1, 31)));
                bookSerializable.save(book);
            }
        };
    }
}