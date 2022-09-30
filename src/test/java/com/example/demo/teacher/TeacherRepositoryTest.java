package com.example.demo.teacher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
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
        Teacher teacher = new Teacher(UUID.randomUUID(), "teacher_name", LocalDate.parse("2004, 1, 5"), "male", "bilalsensei@gmail.com");
        entityManager.persist(teacher);
        entityManager.flush();
        Optional<Teacher> optional = teacherRepository.findTeacherByEmail(teacher.getEmail());
        Assertions.assertNotNull(optional);
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(teacher.getEmail(), optional.get().getEmail());
    }

    @Test
    void findTeacherByName() {
        Teacher teacher = new Teacher(UUID.randomUUID(), "teacher_name", LocalDate.parse("2004, 1, 5"), "male", "bilalsensei@gmail.com");
        entityManager.persist(teacher);
        entityManager.flush();
        Optional<Teacher> optional = teacherRepository.findTeacherByName(teacher.getName());
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertFalse(optional.isEmpty());
        Assertions.assertEquals(optional.get().getName(), teacher.getName());
    }

    @Test
    void findTeacherByUuid() {
        Teacher teacher = new Teacher(UUID.randomUUID(), "teacher_name", LocalDate.parse("2004, 1, 5"), "male", "bilalsensei@gmail.com");
        entityManager.persist(teacher);
        entityManager.flush();
        Optional<Teacher> optional = teacherRepository.findTeacherByUuid(teacher.getUuid());
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertFalse(optional.isEmpty());
        Assertions.assertEquals(optional.get().getUuid(), teacher.getUuid());
    }

    @Test
    void findTeacherByDob() {
        Teacher teacher = new Teacher(UUID.randomUUID(), "teacher_name", LocalDate.parse("2007, 1, 5"), "male", "bilalsensei@gmail.com");
        entityManager.persist(teacher);
        entityManager.flush();
        Optional<Teacher> optional = teacherRepository.findTeacherByDob(teacher.getDob());
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertFalse(optional.isEmpty());
        Assertions.assertEquals(teacher.getDob(), optional.get().getDob());
    }
}
