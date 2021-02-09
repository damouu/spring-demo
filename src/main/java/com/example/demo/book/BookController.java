package com.example.demo.book;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;
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
    public Collection<Book> getBooks(@DefaultValue("0") @QueryParam("page") int page, @DefaultValue("5") @QueryParam("size") int size,
                                     @QueryParam("totalPages") Optional<Integer> totalPages, @QueryParam("genre") Optional<String> genre) {
        return bookService.getBooks(page, size, totalPages, genre);
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

    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response studentBorrowBooks(@QueryParam("book") UUID bookUuid, @QueryParam("student") UUID studentUuid) {
        return bookService.insertStudentBorrowBooks(bookUuid, studentUuid);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response returnStudentBorrowBooks(@QueryParam("book") UUID bookUuid, @QueryParam("student") UUID studentUuid) {
        return bookService.returnStudentBorrowBooks(bookUuid, studentUuid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/student/{studentUuid}")
    public Response bookStudentBorrow(@PathParam("studentUuid") UUID studentUuid) {
        return bookService.getStudentBook(studentUuid);
    }
}
