package com.example.demo.teacher;

import nonapi.io.github.classgraph.utils.VersionFinder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
public class TeacherRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void findByEmail() {
        Teacher teacher = new Teacher(UUID.randomUUID(), "teacher_name", new Date(), "male", "bilalsensei@gmail.com");
        entityManager.persist(teacher);
        entityManager.flush();
        Optional<Teacher> optional = teacherRepository.findTeacherByEmail(teacher.getEmail());
        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(teacher.getEmail(), optional.get().getEmail());
    }
}
