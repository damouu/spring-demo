package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Validated
@Controller
@Path("api/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<Student> allStudents() {
        return studentService.allStudents();
    }

    @GET
    @Path("/{studentUuid}")
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Student getStudent(@PathParam("studentUuid") UUID studentUuid) {
        return studentService.getStudent(studentUuid);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Student postStudent(@Valid Student student) {
        return studentService.postStudent(student);
    }

    @DELETE
    @Path("/{studentUuid}")
    public Response deleteStudent(@PathParam("studentUuid") UUID studentUuid) {
        return studentService.deleteStudent(studentUuid);
    }

    @PUT
    @Path("/{studentUuid}")
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response updateStudent(@PathParam("studentUuid") UUID studentUuid, Student student) {
        return studentService.updateStudent(studentUuid, student);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{studentUuid}/course")
    public Response getCourseStudent(@PathParam("studentUuid") UUID studentUuid) {
        return studentService.getCourseStudent(studentUuid);
    }
}
