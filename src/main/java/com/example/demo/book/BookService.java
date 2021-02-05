package com.example.demo.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookService {

    private BookSerializable bookSerializable;

    @Autowired
    public BookService(BookSerializable bookSerializable) {
        this.bookSerializable = bookSerializable;
    }

    public List<Book> getBooks() {
        return bookSerializable.findAll();
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
}
