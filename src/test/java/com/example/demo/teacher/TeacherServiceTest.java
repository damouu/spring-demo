package com.example.demo.teacher;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
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
}
