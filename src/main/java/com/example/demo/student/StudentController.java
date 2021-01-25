package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Validated
@Component
@Path("api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<Student> allStudents(@QueryParam("age") Optional<Integer> queryParam) {
        return studentService.allStudents(queryParam);
    }

    @GET
    @Path("/{studentId}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response findById(@PathParam("studentId") Integer studentId) {
        return studentService.findById(studentId);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response inertNewStudent(@Valid Student student) {
        return studentService.addNewStudent(student);
    }

    @DELETE
    @Path("/{studentId}")
    public Response deleteStudent(@PathParam("studentId") Integer studentId) {
        return studentService.deleteStudent(studentId);
    }

    @PUT
    @Path("/{studentId}")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response updateStudent(@PathParam("studentId") Integer studentId, Student student) {
        return studentService.updateStudent(studentId, student);
    }
}
