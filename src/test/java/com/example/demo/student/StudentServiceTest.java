///*
//
//package com.example.demo.student;
//
//import com.example.demo.course.CourseRepository;
//import com.github.javafaker.Faker;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.time.LocalDate;
//import java.time.Month;
//import java.util.ArrayList;
//import java.util.Optional;
//
//import static org.mockito.Mockito.when;
//
//class StudentServiceTest {
//
//    @Mock
//    private StudentRepository studentRepository;
//
//    private StudentService studentService;
//
//    private CourseRepository courseRepository;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        studentService = new StudentService(studentRepository);
//        CourseRepository = new CourseRepository(CourseRepository);
//    }
//
//    @Test
//    void allStudents() {
//        Faker faker = new Faker();
//        ArrayList<Student> studentArrayList2 = new ArrayList<>();
//        for (int i = 1; i < 3; i++) {
//            Student student = new Student(
//                    i,
//                    faker.name().name(),
//                    LocalDate.of(faker.number().numberBetween(1900, 2021), Month.NOVEMBER, faker.number().numberBetween(1, 31)),
//                    faker.internet().emailAddress()
//            );
//            studentArrayList2.add(student);
//        }
//        when(studentRepository.findAll()).thenReturn(studentArrayList2);
//        ArrayList<Student> allStudents = (ArrayList<Student>) studentRepository.findAll();
//        Assertions.assertFalse(allStudents.isEmpty());
//    }
//
//    @Test
//    void addNewStudent() {
//    }
//
//    @Test
//    void deleteStudent() {
//    }
//
//    @Test
//    void updateStudent() {
//        Faker faker = new Faker();
//        Student student = new Student(
//                1,
//                faker.name().name(),
//                LocalDate.of(faker.number().numberBetween(1900, 2021), Month.NOVEMBER, faker.number().numberBetween(1, 31)),
//                faker.internet().emailAddress()
//        );
//    }
//
//    @Test
//    void findById() {
//        Faker faker = new Faker();
//        ArrayList<Student> studentArrayList2 = new ArrayList<>();
//        for (int i = 1; i < 4; i++) {
//            Student student = new Student(
//                    i,
//                    faker.name().name(),
//                    LocalDate.of(faker.number().numberBetween(1900, 2021), Month.NOVEMBER, faker.number().numberBetween(1, 31)),
//                    faker.internet().emailAddress()
//            );
//            studentArrayList2.add(student);
//        }
//        when(studentRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(studentArrayList2.get(1)));
//        Optional<Student> student = studentRepository.findById(1);
//        Assertions.assertNotNull(student);
//        Assertions.assertTrue(student.isPresent());
//        Assertions.assertNotNull(student.get().getId());
//        Assertions.assertNotNull(student.get().getName());
//        Assertions.assertNotNull(student.get().getDob());
//        Assertions.assertNotNull(student.get().getEmail());
//        Assertions.assertEquals(student.get().getName(), studentArrayList2.get(1).getName());
//    }
//}
//*/
