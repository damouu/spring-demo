package com.example.demo.book;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

@Validated
@Controller
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
    @Path("/{book}/studentCard/{studentCard}")
    public Response studentBorrowBooks(@PathParam("book") UUID bookUuid, @PathParam("studentCard") UUID studentUuidCard) {
        return bookService.insertStudentBorrowBooks(bookUuid, studentUuidCard);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{book}/studentCard/{studentCard}")
    public Response returnStudentBorrowBooks(@PathParam("book") UUID bookUuid, @PathParam("studentCard") UUID studentUuidCard) {
        return bookService.returnStudentBorrowBooks(bookUuid, studentUuidCard);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/studentCard/{studentCard}")
    public Response bookStudentBorrow(@PathParam("studentCard") UUID studentCard) {
        return bookService.getBooksStudentCard(studentCard);
    }
}
