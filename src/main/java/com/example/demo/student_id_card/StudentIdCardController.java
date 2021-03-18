package com.example.demo.student_id_card;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Validated
@Controller
@Path("api/studentCard")
public class StudentIdCardController {

    private final StudentIdCardService studentIdCardService;

    @Autowired
    public StudentIdCardController(StudentIdCardService studentIdCardService) {
        this.studentIdCardService = studentIdCardService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response getStudentIdCards(@DefaultValue("0") @QueryParam("page") int page, @DefaultValue("5") @QueryParam("size") int size) {
        return studentIdCardService.getStudentIdCards(page, size);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{studentCardNumber}")
    public Response getStudentIdCard(@PathParam("studentCardNumber") UUID studentCardNumber) {
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
    @Path("/student/{studentUuid}")
    public Response createStudentIdCard(@PathParam("studentUuid") UUID studentUuid) {
        return studentIdCardService.postStudentIdCard(studentUuid);
    }

}
