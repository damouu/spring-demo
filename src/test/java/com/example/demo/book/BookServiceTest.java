package com.example.demo.book;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDate;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    @Test
    void getBooks() {
    }

    @Test
    void getBookUuid() {
        Book book = new Book(UUID.randomUUID(), "tittle", "genre", 200, "publisher", "author", LocalDate.now());
        Mockito.when(bookRepository.findByUuid(book.getUuid())).thenReturn(java.util.Optional.of(book));
        ResponseEntity<Book> responseEntity = bookService.getBookUuid(book.getUuid());
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(responseEntity.getBody(), book);
    }

    @Test
    void deleteBook() {
        Book book = new Book(UUID.randomUUID(), "tittle", "genre", 200, "publisher", "author", LocalDate.now());
        Mockito.when(bookRepository.findByUuid(book.getUuid())).thenReturn(java.util.Optional.of(book));
        ResponseEntity<?> responseEntity = bookService.deleteBook(book.getUuid());
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Mockito.verify(bookRepository, Mockito.times(1)).delete(book);
    }

    @Test
    void postBook() {
        Book book = new Book(null, "tittle", "genre", 200, "publisher", "author", LocalDate.now());
        ResponseEntity<Book> responseEntity = bookService.postBook(book);
        Assertions.assertEquals(201, responseEntity.getStatusCodeValue());
        Assertions.assertEquals(URI.create("http://localhost:8083/api/book/" + book.getUuid()), responseEntity.getHeaders().getLocation());
        Assertions.assertEquals(responseEntity.getBody(), book);
        Mockito.verify(bookRepository, Mockito.times(1)).save(book);
    }

    @Test
    void updateBook() {
    }

    @Test
    void insertStudentBorrowBooks() {
    }

    @Test
    void returnStudentBorrowBooks() {
    }

    @Test
    void getBooksStudentCard() {
    }
}