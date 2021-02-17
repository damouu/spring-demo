package com.example.demo.student_id_card;

import com.example.demo.student.Student;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Validated
@Controller
@Path("api/studentCard")
public class StudentIdCardController {

    private StudentIdCardService studentIdCardService;

    @Autowired
    public StudentIdCardController(StudentIdCardService studentIdCardService) {
        this.studentIdCardService = studentIdCardService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<StudentIdCard> getStudentIdCards() {
        return studentIdCardService.getStudentIdCards();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{studentCardNumber}")
    public Student getStudentIdCard(@PathParam("studentCardNumber") UUID studentCardNumber) {
        return studentIdCardService.getStudentIdCard(studentCardNumber);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{studentCardNumber}")
    public Response deleteStudentIdCard(@PathParam("studentCardNumber") UUID studentCardNumber) {
        return studentIdCardService.deleteStudentIdCard(studentCardNumber);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response createStudentIdCard(@QueryParam("studentId") UUID studentUuid) {
        return studentIdCardService.createStudentIdCard(studentUuid);
    }

}
