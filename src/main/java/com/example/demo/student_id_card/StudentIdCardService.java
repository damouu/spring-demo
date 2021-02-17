package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentIdCardService {

    private StudentIdCardSerializable studentIdCardSerializable;

    private StudentRepository studentRepository;

    @Autowired
    public StudentIdCardService(StudentIdCardSerializable studentIdCardSerializable, StudentRepository studentRepository) {
        this.studentIdCardSerializable = studentIdCardSerializable;
        this.studentRepository = studentRepository;
    }

    public List<StudentIdCard> getStudentIdCards() {
        return studentIdCardSerializable.findAll();
    }

    public Student getStudentIdCard(UUID studentCardNumber) {
        Optional<StudentIdCard> studentIdCardByCardNumber = studentIdCardSerializable.findStudentIdCardByUuid(studentCardNumber);
        return studentIdCardByCardNumber.map(StudentIdCard::getStudent).orElse(null);
    }

    public Response deleteStudentIdCard(UUID studentCardNumber) {
        Optional<StudentIdCard> studentIdCard = studentIdCardSerializable.findStudentIdCardByUuid(studentCardNumber);
        if (studentIdCard.isPresent()) {
            studentIdCardSerializable.delete(studentIdCard.get());
            return Response.ok(studentIdCard.get()).status(204).build();
        }
        return Response.noContent().status(404).build();
    }

    public Response createStudentIdCard(UUID studentUuid) {
        Optional<Student> optionalStudent = studentRepository.findStudentByUuid(studentUuid);
        if (optionalStudent.isPresent()) {
            int studentIdCardLast = Math.toIntExact(studentIdCardSerializable.count());
            StudentIdCard studentIdCard = new StudentIdCard(studentIdCardLast + 1, UUID.randomUUID());
            studentIdCard.setStudent(optionalStudent.get());
            studentIdCardSerializable.save(studentIdCard);
            return Response.ok(studentIdCard).status(202).contentLocation(URI.create("http://localhost/api/studentCard/" + studentIdCard.getuuid())).build();
        }
        return Response.notModified().build();
    }
}
