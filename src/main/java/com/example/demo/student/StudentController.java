package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Collection;
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
    public Collection<Student> allStudents(@DefaultValue("0") @QueryParam("page") int page, @DefaultValue("5") @QueryParam("size") int sort) {
        return studentService.allStudents(page, sort);
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
    public Student updateStudent(@PathParam("studentUuid") UUID studentUuid, @Valid Student student) {
        return studentService.updateStudent(studentUuid, student);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{studentUuid}/course")
    public Response getCourseStudent(@PathParam("studentUuid") UUID studentUuid) {
        return studentService.getCourseStudent(studentUuid);
    }
}
