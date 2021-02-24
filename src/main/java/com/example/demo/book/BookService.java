package com.example.demo.book;

import com.example.demo.student_id_card.StudentIdCard;
import com.example.demo.student_id_card.StudentIdCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final StudentIdCardRepository studentIdCardRepository;

    @Autowired
    public BookService(BookRepository bookRepository, StudentIdCardRepository studentIdCardRepository) {
        this.bookRepository = bookRepository;
        this.studentIdCardRepository = studentIdCardRepository;
    }

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
        Optional<Book> book = bookRepository.findByUuid(bookUuid);
        return book.orElse(null);
    }

    public Response deleteBook(UUID bookUuid) {
        Optional<Book> book = bookRepository.findByUuid(bookUuid);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
            return Response.accepted().build();
        }
        return Response.noContent().status(404).build();
    }

    public Response insertBook(Book book) {
        bookRepository.save(book);
        return Response.accepted(book).status(201).entity(book).location(URI.create("http://localhost:8080/api/book/" + book.getId())).build();
    }

    public Response updateBook(UUID bookUuid, Book book) {
        if (bookRepository.findByUuid(bookUuid).isPresent()) {
            Book optionalBook = bookRepository.findByUuid(bookUuid).get();
            optionalBook.setAuthor(book.getAuthor());
            optionalBook.setCreated_at(book.getCreated_at());
            optionalBook.setGenre(book.getGenre());
            optionalBook.setPublisher(book.getPublisher());
            optionalBook.setTitle(book.getTitle());
            optionalBook.setTotalPages(book.getTotalPages());
            bookRepository.save(optionalBook);
            return Response.ok().status(204).contentLocation(URI.create("http://localhost:8080/api/book/" + optionalBook.getId())).build();
        }
        return Response.noContent().status(404).build();
    }

    public Response insertStudentBorrowBooks(UUID bookUuid, UUID studentUuidCard) {
        Optional<Book> book = bookRepository.findByUuid(bookUuid);
        Optional<StudentIdCard> studentIdCard = studentIdCardRepository.findStudentIdCardByUuid(studentUuidCard);
        if (book.isPresent() && studentIdCard.isPresent()) {
            if (book.get().getStudentIdCard() == null) {
                book.get().setStudentIdCard(studentIdCard.get());
                bookRepository.save(book.get());
                return Response.ok(book.get(), MediaType.APPLICATION_JSON_TYPE).status(201).build();
            }
        }
        return Response.status(406).build();
    }

    public Response returnStudentBorrowBooks(UUID bookUuid, UUID studentUuid) {
        Optional<Book> book = bookRepository.findByUuid(bookUuid);
        Optional<StudentIdCard> studentCard = studentIdCardRepository.findStudentIdCardByUuid(studentUuid);
        if (book.isPresent() && studentCard.isPresent()) {
            if (book.get().getStudentIdCard().getUuid().equals(studentCard.get().getUuid())) {
                book.get().setStudentIdCard(null);
                bookRepository.save(book.get());
                return Response.ok(book.get(), MediaType.APPLICATION_JSON_TYPE).status(200).build();
            }
        }
        return Response.status(406).build();
    }

    public Response getBooksStudentCard(UUID studentCard) {
        Optional<StudentIdCard> studentIdCard = studentIdCardRepository.findStudentIdCardByUuid(studentCard);
        if (studentIdCard.isPresent() && !studentIdCard.get().getBooks().isEmpty()) {
            return Response.ok(studentIdCard.get().getBooks()).status(200).build();
        }
        return Response.noContent().build();
    }
}
