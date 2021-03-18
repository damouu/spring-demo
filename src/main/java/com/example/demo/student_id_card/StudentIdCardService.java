package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.UUID;

@Service
@Data
public class StudentIdCardService {

    private final StudentIdCardRepository studentIdCardRepository;

    private final StudentRepository studentRepository;

    public Response getStudentIdCards(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StudentIdCard> pagedResult = studentIdCardRepository.findAll(pageable);
        return Response.ok(pagedResult.toList()).status(200).build();
    }

    public Response getStudentIdCard(UUID studentCardNumber) {
        StudentIdCard studentIdCard = this.studentIdCardRepository.findStudentIdCardByUuid(studentCardNumber).orElseThrow(() -> new IllegalStateException("student card does not exist"));
        return Response.ok(studentIdCard.getStudent()).status(200).build();
    }

    public Response deleteStudentIdCard(UUID studentCardNumber) {
        StudentIdCard studentIdCard = this.studentIdCardRepository.findStudentIdCardByUuid(studentCardNumber).orElseThrow(() -> new IllegalStateException("student card does not exist"));
        studentIdCardRepository.delete(studentIdCard);
        return Response.ok().status(204).build();
    }

    public Response postStudentIdCard(UUID studentUuid) {
        Student student = this.studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new EntityNotFoundException("student does not exist"));
        StudentIdCard studentIdCard = new StudentIdCard(UUID.randomUUID());
        studentIdCard.setStudent(student);
        studentIdCardRepository.save(studentIdCard);
        return Response.ok().status(202).contentLocation(URI.create("http://localhost:8083/api/studentCard/" + studentIdCard.getUuid())).build();
    }
}
