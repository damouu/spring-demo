package com.example.demo.student_id_card;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StudentIdCardSerializable extends JpaRepository<StudentIdCard, Integer> {

    Optional<StudentIdCard> findStudentIdCardByUuid(UUID studentCardNumber);
}
