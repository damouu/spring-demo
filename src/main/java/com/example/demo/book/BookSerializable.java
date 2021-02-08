package com.example.demo.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookSerializable extends JpaRepository<Book, Integer> {
    Optional<Book> findByUuid(UUID uuid);

    Optional<Collection<Book>> findAllByTotalPages(Optional<Integer> integer);

    Optional<Collection<Book>> findAllByGenre(Optional<String> stringOptional);

    Optional<Collection<Book>> findAllByGenreAndTotalPages(Optional<String> genre, Optional<Integer> totalPages);

}
