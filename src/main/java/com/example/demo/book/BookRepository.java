package com.example.demo.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM book b WHERE b.deleted_at is null and b.uuid = :uuid")
    Optional<Book> findByUuid(UUID uuid);

    Optional<Collection<Book>> findAllByTotalPages(Optional<Integer> integer);

    Optional<Collection<Book>> findAllByGenre(Optional<String> stringOptional);

    Optional<Collection<Book>> findAllByGenreAndTotalPages(Optional<String> genre, Optional<Integer> totalPages);

}
