package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class StudentIdCardConfiguration {

    @Bean
    CommandLineRunner runner(StudentIdCardRepository serializable, StudentRepository studentRepository) {
        return args -> {
            for (int i = 1; i < 20; i++) {
                StudentIdCard student_idCard = new StudentIdCard(
                        i,
                        UUID.randomUUID()
                );
                Student student = studentRepository.findById(i).get();
                student_idCard.setStudent(student);
                serializable.save(student_idCard);
            }
        };
    }
}
