package com.example.demo.teacher;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Data
@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public Teacher getTeacherUuid(UUID uuid) {
        return teacherRepository.findTeacherByUuid(uuid).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "teacher not found"));
    }
}
