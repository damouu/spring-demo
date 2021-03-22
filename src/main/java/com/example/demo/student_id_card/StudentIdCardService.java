package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
@Data
public class StudentIdCardService {

    private final StudentIdCardRepository studentIdCardRepository;

    private final StudentRepository studentRepository;

    public List<StudentIdCard> getStudentIdCards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentIdCard> pages = studentIdCardRepository.findAll(pageable);
        return pages.toList();
    }

    public StudentIdCard getStudentIdCard(UUID studentCardNumber) throws ResponseStatusException {
        return this.studentIdCardRepository.findStudentIdCardByUuid(studentCardNumber).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student card does not exist"));
    }

    public ResponseEntity<?> deleteStudentIdCard(UUID studentCardUuid) throws ResponseStatusException {
        StudentIdCard studentIdCard = this.studentIdCardRepository.findStudentIdCardByUuid(studentCardUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student card does not exist"));
        studentIdCardRepository.delete(studentIdCard);
        return ResponseEntity.status(204).body(studentIdCard);
    }

    public ResponseEntity<?> postStudentIdCard(UUID studentUuid) throws ResponseStatusException {
        Student student = this.studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "student Uuid does not exist"));
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        studentIdCard.setStudent(student);
        studentIdCardRepository.save(studentIdCard);
        return ResponseEntity.status(202).location(URI.create("http://localhost:8083/api/studentCard/" + studentIdCard.getUuid())).body(studentIdCard);
    }
}
