package com.example.demo.student_id_card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Serializable extends JpaRepository<StudentIdCard, Integer> {
}
