package com.example.demo.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookSerializable extends JpaRepository<Book, Integer> {
}
