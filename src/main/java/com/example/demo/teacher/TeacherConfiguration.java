package com.example.demo.teacher;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.UUID;

@Configuration
// tells to the spring framework that this class has a @Bean definition method..
public class TeacherConfiguration {
    @Bean
        // tells to the spring that the method will return an object and it should be managed like a bean.
    CommandLineRunner teacherRunner(TeacherRepository teacherRepository) {
        return args -> {
            Faker faker = new Faker();
            for (int i = 0; i < 20; i++) {
                var age = faker.date().birthday(18, 100);
                Teacher teacher = new Teacher(UUID.randomUUID(), faker.name().name(), LocalDate.parse((CharSequence) age), faker.demographic().sex(), faker.internet().emailAddress());
                teacherRepository.save(teacher);
            }
        };
    }
}
