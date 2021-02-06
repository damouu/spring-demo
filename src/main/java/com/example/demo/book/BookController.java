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
import java.util.UUID;

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
    public List<Book> getBooks(@DefaultValue("0") @QueryParam("page") int page, @DefaultValue("5") @QueryParam("size") int size) {
        return bookService.getBooks(page, size);
    }

    @GET
    @Path("/{bookUuid}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Book getBookById(@PathParam("bookUuid") UUID bookUuid) {
        return bookService.getBookById(bookUuid);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response inertBook(@Valid Book book) {
        return bookService.insertBook(book);
    }

    @DELETE
    @Path("/{bookUuid}")
    public Response deleteBook(@PathParam("bookUuid") UUID bookUuid) {
        return bookService.deleteBook(bookUuid);
    }

    @PUT
    @Path("/{bookUuid}")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response updateBook(@PathParam("bookUuid") UUID bookUuid, @Valid Book book) {
        return bookService.updateBook(bookUuid, book);
    }
}
