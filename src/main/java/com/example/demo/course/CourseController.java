package com.example.demo.course;

import lombok.Data;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
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
@Data
@Path("api/course")
public class CourseController {

    private final CourseService courseService;

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{courseUuid}")
    public Course getCourse(@PathParam("courseUuid") UUID courseUuid) {
        return courseService.getCourse(courseUuid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Collection<Course> getCourses(@DefaultValue("0") @QueryParam("page") int page, @DefaultValue("5") @QueryParam("size") int size) {
        return courseService.getCourses(page, size);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response postCourse(@Valid Course course) {
        return courseService.postCourse(course);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{courseUuid}")
    public Response deleteCourse(@PathParam("courseUuid") UUID courseUuid) {
        return courseService.deleteCourse(courseUuid);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{courseUuid}")
    public Response updateCourse(@PathParam("courseUuid") UUID courseUuid, @Valid Course course) {
        return courseService.updateCourse(courseUuid, course);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{courseUuid}/student/{studentUuid}")
    public Response postStudentCourse(@PathParam("courseUuid") UUID courseUuid, @PathParam("studentUuid") UUID studentUuid) {
        return courseService.postStudentCourse(courseUuid, studentUuid);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{courseUuid}/student/{studentUuid}")
    public Response deleteStudentCourse(@PathParam("courseUuid") UUID courseUuid, @PathParam("studentUuid") UUID studentUuid) {
        return courseService.deleteStudentCourse(courseUuid, studentUuid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{courseUuid}/student")
    public Response getStudentsCourse(@PathParam("courseUuid") UUID courseUuid) {
        return courseService.getStudentsCourse(courseUuid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/search")
    public Response getCourseSearchQueryParam(@QueryParam("university") String university, @QueryParam("campus") String campus, @QueryParam("name") String name) {
        return courseService.getCourseSearchQueryParam(university, campus, name);
    }
}
