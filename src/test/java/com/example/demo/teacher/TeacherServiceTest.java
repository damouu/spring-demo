package com.example.demo.teacher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void getTeacher() {
        Teacher teacher = new Teacher(UUID.randomUUID(), "teacher_name", new Date(), "male", "bilalsensei@gmail.com");
        Mockito.when(teacherRepository.findTeacherByUuid(teacher.getUuid())).thenReturn(Optional.of(teacher));
        Teacher teacher1 = teacherService.getTeacherUuid(teacher.getUuid());
        Mockito.verify(teacherRepository, Mockito.times(1)).findTeacherByUuid(UUID.fromString(String.valueOf(teacher.getUuid())));
        Assertions.assertEquals(teacher1.getUuid(), teacher.getUuid());
    }

    @Test
    void getTeacherEmail() {
        Teacher teacher = new Teacher(UUID.randomUUID(), "teacher_name", new Date(), "male", "bilalsensei@gmail.com");
        Mockito.when(teacherRepository.findTeacherByEmail(teacher.getEmail())).thenReturn(Optional.of(teacher));
        Optional<Teacher> teacher1 = teacherService.getTeacherEmail(teacher.getEmail());
        Mockito.verify(teacherRepository, Mockito.times(1)).findTeacherByEmail(teacher.getEmail());
        Assertions.assertTrue(teacher1.isPresent());
        Assertions.assertFalse(teacher1.isEmpty());
        Assertions.assertEquals(teacher1.get().getEmail(), teacher.getEmail());
    }

    @Test
    void deleteTeacher() {
        Teacher teacher = new Teacher(UUID.randomUUID(), "teacher_name", new Date(), "male", "bilalsensei@gmail.com");
        Mockito.when(teacherRepository.findTeacherByUuid(teacher.getUuid())).thenReturn(java.util.Optional.of(teacher));
        ResponseEntity<String> deleteTest = (ResponseEntity<String>) teacherService.deleteTeacher(teacher.getUuid());
        Mockito.verify(teacherRepository, Mockito.times(1)).delete(teacher);
        Assertions.assertEquals(deleteTest.getBody(), "teacher successfully deleted");
        Assertions.assertTrue(deleteTest.getStatusCode().is2xxSuccessful());
        Mockito.verify(teacherRepository, Mockito.times(1)).delete(teacher);
    }

    @Test
    void postTeacher() {
        Teacher teacher = new Teacher(UUID.randomUUID(), "teacher_name", new Date(), "male", "bilalsensei@gmail.com");
        Mockito.when(teacherRepository.findTeacherByUuid(teacher.getUuid())).thenReturn(Optional.empty());
        var responseEntity = teacherService.postTeacher(teacher);
        Assertions.assertNotNull(responseEntity);
        Assertions.assertTrue(responseEntity.getHeaders().containsKey("Location"));
        Assertions.assertEquals(responseEntity.getHeaders().get("Location"), List.of("http://localhost:8083/api/teacher/" + teacher.getUuid()));
        Assertions.assertEquals(teacher, responseEntity.getBody());
        Assertions.assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
        Mockito.verify(teacherRepository, Mockito.times(1)).save(teacher);
    }
}