package com.example.demo.book;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Validated
@Component
@Path("api/book")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GET
    @Path("/{bookId}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Book getBookById(@PathParam("bookId") Integer bookId) {
        return bookService.getBookById(bookId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response inertBook(@Valid Book book) {
        return bookService.insertBook(book);
    }

    @DELETE
    @Path("/{bookId}")
    public Response deleteBook(@PathParam("bookId") Integer bookId) {
        return bookService.deleteBook(bookId);
    }

    @PUT
    @Path("/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response updateBook(@PathParam("bookId") Integer bookId, @Valid Book book) {
        return bookService.updateBook(bookId, book);
    }
}
