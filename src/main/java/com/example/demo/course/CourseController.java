package com.example.demo.course;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Validated
@Component
@Path("api/course")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{courseId}")
    public Course getCourse(@PathParam("courseId") Integer courseId) {
        return courseService.getCourse(courseId);
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
    @Path("/{courseId}")
    public Response deleteCourse(@PathParam("courseId") Integer courseId) {
        return courseService.removeCourse(courseId);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON_VALUE)
    @Consumes(MediaType.APPLICATION_JSON_VALUE)
    @Path("/{courseId}")
    public Response updateCourse(@PathParam("courseId") Integer courseId, @Valid Course course) {
        return courseService.updateCourse(courseId, course);
    }
}
