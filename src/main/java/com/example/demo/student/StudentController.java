package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

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
    public Response inertNewStudent(Student student) {
        return studentService.addNewStudent(student);
    }

    /*@DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Integer studentId) {
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(
            @PathVariable("studentId") Integer studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
    }*/
}
