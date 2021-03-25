package com.example.demo.student_id_card;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
// Testing Jpa queries with this annotation
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
// tells springboot to not use an embedded database instead to use the current default database source.
public class StudentIdCardRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StudentIdCardRepository studentIdCardRepository;

    @Test
    public void findStudentIdCardByUuid() {
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        entityManager.persist(studentIdCard);
        entityManager.flush();
        Optional<StudentIdCard> found = studentIdCardRepository.findStudentIdCardByUuid(studentIdCard.getUuid());
        Assertions.assertEquals(studentIdCard, found.get());
    }
}
