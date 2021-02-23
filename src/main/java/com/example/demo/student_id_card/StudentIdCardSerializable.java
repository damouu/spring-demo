package com.example.demo.student_id_card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentIdCardSerializable extends JpaRepository<StudentIdCard, Integer> {

    Optional<StudentIdCard> findStudentIdCardByUuid(UUID studentCardNumber);
}
