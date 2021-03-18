package com.example.demo.course;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class CourseConfiguration {

    @Bean
    CommandLineRunner lineRunner(CourseRepository courseRepository) {
        return args -> {
            Faker faker = new Faker();
            for (int i = 1; i < 50; i++) {
                Course course = new Course(
                        UUID.randomUUID(),
                        faker.educator().course(),
                        faker.educator().campus(),
                        faker.educator().university()
                );
                courseRepository.save(course);
            }
        };
    }
}
