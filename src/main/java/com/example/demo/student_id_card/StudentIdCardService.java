package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class StudentIdCardService {

    private final StudentIdCardRepository studentIdCardRepository;

    private final StudentRepository studentRepository;

    @Autowired
    public StudentIdCardService(StudentIdCardRepository studentIdCardRepository, StudentRepository studentRepository) {
        this.studentIdCardRepository = studentIdCardRepository;
        this.studentRepository = studentRepository;
    }

    public List<StudentIdCard> getStudentIdCards() {
        return studentIdCardRepository.findAll();
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

    public Response createStudentIdCard(UUID studentUuid) {
        Student student = this.studentRepository.findStudentByUuid(studentUuid).orElseThrow(() -> new IllegalStateException("student does not exist"));
        StudentIdCard studentIdCard = new StudentIdCard(Math.toIntExact(studentIdCardRepository.count()) + 1, UUID.randomUUID());
        studentIdCard.setStudent(student);
        studentIdCardRepository.save(studentIdCard);
        return Response.ok().status(202).contentLocation(URI.create("http://localhost:8083/api/studentCard/" + studentIdCard.getuuid())).build();
    }
}
