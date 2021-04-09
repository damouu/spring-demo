package com.example.demo.book;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void getBooks() {
    }

    @Test
    void getBookUuid() throws Exception {
        Book book = new Book(UUID.randomUUID(), "tittle", "genre", 200, "publisher", "author", LocalDate.now());
        Mockito.when(bookService.getBookUuid(book.getUuid())).thenReturn(ResponseEntity.ok(book));
        mockMvc.perform(get("/api/book/" + book.getUuid()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"uuid\":\"" + book.getUuid() + "\"," +
                        "\"title\":\"" + book.getTitle() + "\"," +
                        "\"genre\":\"" + book.getGenre() + "\"," +
                        "\"totalPages\":" + book.getTotalPages() + "," +
                        "\"publisher\":\"" + book.getPublisher() + "\"," +
                        "\"author\":\"" + book.getAuthor() + "\"," +
                        "\"created_at\":\"" + LocalDate.now() + "\"}"));
    }

    @Test
    void inertBook() {
    }

    @Test
    void deleteBook() {
    }

    @Test
    void updateBook() {
    }

    @Test
    void studentBorrowBooks() {
    }

    @Test
    void returnStudentBorrowBooks() {
    }

    @Test
    void bookStudentBorrow() {
    }
}