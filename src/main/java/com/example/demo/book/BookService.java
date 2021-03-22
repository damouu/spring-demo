package com.example.demo.book;

import com.example.demo.student_id_card.StudentIdCard;
import com.example.demo.student_id_card.StudentIdCardRepository;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
public class BookService {

    private final BookRepository bookRepository;

    private final StudentIdCardRepository studentIdCardRepository;

    public Collection<Book> getBooks(int page, int size, Optional<Integer> totalPages, @QueryParam("genre") Optional<String> genre) {
        Pageable pageable = PageRequest.of(page, size);
        if (totalPages.isPresent() && genre.isEmpty()) {
            Optional<Collection<Book>> pagedResult = bookRepository.findAllByTotalPages(totalPages);
            return pagedResult.get();
        }
        if (genre.isPresent() && totalPages.isEmpty()) {
            Optional<Collection<Book>> pagedResult = bookRepository.findAllByGenre(genre);
            return pagedResult.orElse(null);
        }
        if (genre.isPresent() && totalPages.isPresent()) {
            Optional<Collection<Book>> books = bookRepository.findAllByGenreAndTotalPages(genre, totalPages);
            return books.orElse(null);
        }
        Page<Book> pagedResult = bookRepository.findAll(pageable);
        return pagedResult.toList();
    }

    public Book getBookById(UUID bookUuid) {
        return bookRepository.findByUuid(bookUuid).orElseThrow(() -> new IllegalStateException("book not found" + " " + bookUuid));
    }

    public Response deleteBook(UUID bookUuid) {
        Book book = bookRepository.findByUuid(bookUuid).orElseThrow(() -> new IllegalStateException("book does not exist"));
        bookRepository.delete(book);
        return Response.accepted().build();
    }

    public Response insertBook(Book book) {
        book.setUuid(UUID.randomUUID());
        bookRepository.save(book);
        return Response.accepted(book).status(201).entity(book).location(URI.create("http://localhost:8083/api/book/" + book.getUuid())).build();
    }

    public Response updateBook(UUID bookUuid, Book book) {
        Book optionalBook = bookRepository.findByUuid(bookUuid).orElseThrow(() -> new IllegalStateException("book does not exist"));
        optionalBook.setAuthor(book.getAuthor());
        optionalBook.setCreated_at(book.getCreated_at());
        optionalBook.setGenre(book.getGenre());
        optionalBook.setPublisher(book.getPublisher());
        optionalBook.setTitle(book.getTitle());
        optionalBook.setTotalPages(book.getTotalPages());
        bookRepository.save(optionalBook);
        return Response.ok().status(204).contentLocation(URI.create("http://localhost:8083/api/book/" + optionalBook.getUuid())).build();
    }

    public Response insertStudentBorrowBooks(UUID bookUuid, UUID studentUuidCard) {
        Book book = bookRepository.findByUuid(bookUuid).orElseThrow(() -> new IllegalStateException("book does not exist"));
        StudentIdCard studentIdCard = studentIdCardRepository.findStudentIdCardByUuid(studentUuidCard).orElseThrow(() -> new IllegalStateException("studentIdCard does not exist" + studentUuidCard));
        if (book.getStudentIdCard() == null) {
            book.setStudentIdCard(studentIdCard);
            bookRepository.save(book);
            return Response.ok(book).status(201).build();
        }
        return Response.notModified().status(406).build();
    }

    public Response returnStudentBorrowBooks(UUID bookUuid, UUID studentUuidCard) {
        Book book = bookRepository.findByUuid(bookUuid).orElseThrow(() -> new IllegalStateException("book does not exist"));
        StudentIdCard studentIdCard = studentIdCardRepository.findStudentIdCardByUuid(studentUuidCard).orElseThrow(() -> new IllegalStateException("studentIdCard does not exist" + studentUuidCard));
        if (book.getStudentIdCard() != null && book.getStudentIdCard().getUuid().equals(studentIdCard.getUuid())) {
            book.setStudentIdCard(null);
            bookRepository.save(book);
            return Response.ok(book).status(200).build();
        }
        return Response.notModified().status(406).build();
    }

    public Response getBooksStudentCard(UUID studentUuidCard) {
        StudentIdCard studentIdCard = studentIdCardRepository.findStudentIdCardByUuid(studentUuidCard).orElseThrow(() -> new IllegalStateException("studentIdCard does not exist" + studentUuidCard));
        if (!studentIdCard.getBooks().isEmpty()) {
            return Response.ok(studentIdCard.getBooks()).status(200).build();
        }
        return Response.noContent().status(204).build();
    }
}
