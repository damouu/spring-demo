package com.example.demo.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookSerializable extends JpaRepository<Book, Integer> {
    Optional<Book> findByUuid(UUID uuid);

    Optional<List<Book>> findAllByTotalPages(Optional<Integer> integer);
}
