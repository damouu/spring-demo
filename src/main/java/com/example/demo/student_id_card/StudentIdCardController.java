package com.example.demo.student_id_card;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Data
@Validated
@RestController
@RequestMapping("api/studentCard")
public class StudentIdCardController {

    private final StudentIdCardService studentIdCardService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StudentIdCard> getStudentIdCards(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page, @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {
        return studentIdCardService.getStudentIdCards(page, size);
    }

    @GetMapping(path = "{studentCardUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentIdCard getStudentIdCard(@PathVariable("studentCardUuid") UUID studentCardUuid) {
        return studentIdCardService.getStudentIdCard(studentCardUuid);
    }

    @DeleteMapping(value = "/{studentCardUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteStudentIdCard(@PathVariable("studentCardUuid") UUID studentCardUuid) {
        return studentIdCardService.deleteStudentIdCard(studentCardUuid);
    }

    @PostMapping(path = "/student/{studentUuid}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createStudentIdCard(@PathVariable("studentUuid") UUID studentUuid) {
        return studentIdCardService.postStudentIdCard(studentUuid);
    }

}
