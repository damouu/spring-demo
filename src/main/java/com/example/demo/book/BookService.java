package com.example.demo.book;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
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

    private BookSerializable bookSerializable;

    private StudentRepository studentRepository;

    @Autowired
    public BookService(BookSerializable bookSerializable, StudentRepository studentRepository) {
        this.bookSerializable = bookSerializable;
        this.studentRepository = studentRepository;
    }

    public Collection<Book> getBooks(int page, int size, Optional<Integer> totalPages, @QueryParam("genre") Optional<String> genre) {
        Pageable pageable = PageRequest.of(page, size);
        if (totalPages.isPresent() && genre.isEmpty()) {
            Optional<Collection<Book>> pagedResult = bookSerializable.findAllByTotalPages(totalPages);
            return pagedResult.get();
        }
        if (genre.isPresent() && totalPages.isEmpty()) {
            Optional<Collection<Book>> pagedResult = bookSerializable.findAllByGenre(genre);
            return pagedResult.orElse(null);
        }
        if (genre.isPresent() && totalPages.isPresent()) {
            Optional<Collection<Book>> books = bookSerializable.findAllByGenreAndTotalPages(genre, totalPages);
            return books.orElse(null);
        }
        Page<Book> pagedResult = bookSerializable.findAll(pageable);
        return pagedResult.toList();
    }

    public Book getBookById(UUID bookUuid) {
        Optional<Book> book = bookSerializable.findByUuid(bookUuid);
        return book.orElse(null);
    }

    public Response deleteBook(UUID bookUuid) {
        Optional<Book> book = bookSerializable.findByUuid(bookUuid);
        if (book.isPresent()) {
            bookSerializable.delete(book.get());
            return Response.accepted().build();
        }
        return Response.noContent().status(404).build();
    }

    public Response insertBook(Book book) {
        bookSerializable.save(book);
        return Response.accepted(book).status(201).entity(book).location(URI.create("http://localhost:8080/api/book/" + book.getId())).build();
    }

    public Response updateBook(UUID bookUuid, Book book) {
        if (bookSerializable.findByUuid(bookUuid).isPresent()) {
            Book optionalBook = bookSerializable.findByUuid(bookUuid).get();
            optionalBook.setAuthor(book.getAuthor());
            optionalBook.setCreated_at(book.getCreated_at());
            optionalBook.setGenre(book.getGenre());
            optionalBook.setPublisher(book.getPublisher());
            optionalBook.setTitle(book.getTitle());
            optionalBook.setTotalPages(book.getTotalPages());
            bookSerializable.save(optionalBook);
            return Response.ok().status(204).contentLocation(URI.create("http://localhost:8080/api/book/" + optionalBook.getId())).build();
        }
        return Response.noContent().status(404).build();
    }

    public Response insertStudentBorrowBooks(UUID bookUuid, UUID studentUuid) {
        Optional<Book> book = bookSerializable.findByUuid(bookUuid);
        Optional<Student> student = studentRepository.findStudentByUuid(studentUuid);
        if (book.isPresent() && student.isPresent()) {
            book.get().setStudent(student.get());
            bookSerializable.save(book.get());
            return Response.ok(book.get(), MediaType.APPLICATION_JSON_TYPE).status(201).build();
        }
        return Response.status(406).build();
    }
}
