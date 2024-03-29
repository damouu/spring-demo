package com.example.demo.book;

import com.example.demo.student_id_card.StudentIdCard;
import com.example.demo.student_id_card.StudentIdCardRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Data
public class BookService {

    private final BookRepository bookRepository;

    private final StudentIdCardRepository studentIdCardRepository;

    public Collection<Book> getBooks(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> pages = bookRepository.findAll(pageable);
        return pages.toList();
    }

    public ResponseEntity<Book> getBookUuid(UUID bookUuid) throws ResponseStatusException {
        Book book = bookRepository.findByUuid(bookUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found"));
        return ResponseEntity.ok(book);
    }

    public ResponseEntity<?> deleteBook(UUID bookUuid) throws ResponseStatusException {
        Book book = bookRepository.findByUuid(bookUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found"));
        book.setDeleted_at(LocalDate.now());
        bookRepository.save(book);
        return ResponseEntity.accepted().build();
    }

    public ResponseEntity<Book> postBook(Book book) {
        book.setUuid(UUID.randomUUID());
        bookRepository.save(book);
        return ResponseEntity.status(201).location(URI.create("http://localhost:8083/api/book/" + book.getUuid())).body(book);
    }

    public ResponseEntity<Book> updateBook(UUID bookUuid, HashMap<String, String> bookUpdates) {
        Book book = bookRepository.findByUuid(bookUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found"));
        ObjectMapper oMapper = new ObjectMapper();
        Map map = oMapper.convertValue(book, Map.class);
        for (Object s : map.keySet()) {
            for (String s1 : bookUpdates.keySet()) {
                if (s.equals(s1)) {
                    map.put(s, bookUpdates.get(s1));
                }
            }
        }
        Book book1 = oMapper.convertValue(map, Book.class);
        book1.setId(book.getId());
        bookRepository.save(book1);
        Book book11 = bookRepository.findByUuid(book1.getUuid()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book not found"));
        return ResponseEntity.status(200).location(URI.create("http://localhost:8083/api/book/" + book11.getUuid())).body(book11);
    }

    public ResponseEntity<?> insertStudentBorrowBooks(UUID bookUuid, UUID studentUuidCard) {
        Book book = bookRepository.findByUuid(bookUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book does not exist"));
        StudentIdCard studentIdCard = studentIdCardRepository.findStudentIdCardByUuid(studentUuidCard).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "studentIdCard does not exist" + studentUuidCard));
        if (book.getStudentIdCard() == null) {
            book.setStudentIdCard(studentIdCard);
            bookRepository.save(book);
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<?> returnStudentBorrowBooks(UUID bookUuid, UUID studentUuidCard) {
        Book book = bookRepository.findByUuid(bookUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "book does not exist"));
        StudentIdCard studentIdCard = studentIdCardRepository.findStudentIdCardByUuid(studentUuidCard).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "studentIdCard does not exist" + studentUuidCard));
        if (book.getStudentIdCard() != null && book.getStudentIdCard().getUuid().equals(studentIdCard.getUuid())) {
            book.setStudentIdCard(null);
            bookRepository.save(book);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<?> getBooksStudentCard(UUID studentUuidCard) {
        StudentIdCard studentIdCard = studentIdCardRepository.findStudentIdCardByUuid(studentUuidCard).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "studentIdCard does not exist" + studentUuidCard));
        if (!studentIdCard.getBooks().isEmpty()) {
            return ResponseEntity.status(200).body(studentIdCard.getBooks());
        }
        return ResponseEntity.noContent().build();
    }
}
