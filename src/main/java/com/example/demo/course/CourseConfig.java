package com.example.demo.course;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class CourseConfig {

    @Bean
    CommandLineRunner lineRunner(CourseRepository courseRepository) {
        return args -> {
            Faker faker = new Faker();
            for (int i = 1; i < 7; i++) {
                Course course = new Course(
                        i,
                        UUID.randomUUID(),
                        faker.educator().course(),
                        faker.educator().campus()
                );
                courseRepository.save(course);
            }
        };
    }
}
