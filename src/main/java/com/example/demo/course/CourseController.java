package com.example.demo.course;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
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
@Path("api/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{courseUuid}")
    public Course getCourse(@PathParam("courseUuid") UUID courseUuid) {
        return courseService.getCourse(courseUuid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response createCourse(@Valid Course course) {
        return courseService.createCourse(course);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{courseUuid}")
    public Response deleteCourse(@PathParam("courseUuid") UUID courseUuid) {
        return courseService.removeCourse(courseUuid);
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
    public Response postStudentCourse(@QueryParam("courseUuid") UUID courseUuid, @QueryParam("studentUuid") UUID studentUuid) {
        return courseService.postStudentCourse(courseUuid, studentUuid);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    public Response deleteStudentCourse(@QueryParam("courseUuid") UUID courseUuid, @QueryParam("studentUuid") UUID studentUuid) {
        return courseService.deleteStudentCourse(courseUuid, studentUuid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/student")
    public Response getStudentsCourse(@QueryParam("courseUuid") UUID courseUuid) {
        return courseService.getStudentsCourse(courseUuid);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/search")
    public Response getCourseSearchQueryParam(@QueryParam("university") String university, @QueryParam("campus") String campus, @QueryParam("name") String name) {
        return courseService.getCourseSearchQueryParam(university, campus, name);
    }
}
