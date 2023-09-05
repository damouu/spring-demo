package com.example.demo.book;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

@Data
@Validated
@CrossOrigin
@RestController
@RequestMapping("api/book")
public class BookController {

    private final BookService bookService;
    private UUID bookUuid;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Book> getBooks(@RequestParam(name = "page", required = false, defaultValue = "0") int page, @RequestParam(name = "size", required = false, defaultValue = "5") int size) {
        return bookService.getBooks(page, size);
    }

    @GetMapping(path = "/{bookUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBookUuid(@PathVariable("bookUuid") UUID bookUuid) {
        return bookService.getBookUuid(bookUuid);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> postBook(@Valid @RequestBody Book book) {
        return bookService.postBook(book);
    }

    @DeleteMapping(path = "/{bookUuid}")
    public ResponseEntity<?> deleteBook(@PathVariable("bookUuid") UUID bookUuid) {
        return bookService.deleteBook(bookUuid);
    }

    @PutMapping(path = "/{bookUuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> updateBook(@PathVariable("bookUuid") UUID bookUuid, @RequestBody HashMap<String, String> bookUpdates) {
        return bookService.updateBook(bookUuid, bookUpdates);
    }

    @PostMapping(path = "/{book}/studentCard/{studentCard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> studentBorrowBooks(@PathVariable("book") UUID bookUuid, @PathVariable("studentCard") UUID studentUuidCard) {
        return bookService.insertStudentBorrowBooks(bookUuid, studentUuidCard);
    }

    @DeleteMapping(path = "/{book}/studentCard/{studentCard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> returnStudentBorrowBooks(@PathVariable("book") UUID bookUuid, @PathVariable("studentCard") UUID studentUuidCard) {
        return bookService.returnStudentBorrowBooks(bookUuid, studentUuidCard);
    }

    @GetMapping(path = "/studentCard/{studentCard}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> bookStudentBorrow(@PathVariable("studentCard") UUID studentCard) {
        return bookService.getBooksStudentCard(studentCard);
    }
}
