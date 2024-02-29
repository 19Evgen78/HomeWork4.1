package pro.sky.java.cource4.ru.hogwarts.school1.controllers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Faculty;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Student;
import pro.sky.java.cource4.ru.hogwarts.school1.repositories.StudentRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private TestRestTemplate testTemplate;

    @AfterEach
    public void cleanUp() {
        studentRepository.deleteAll();
    }

    @Test
    void createStudentTest() throws Exception {
        Student expect = new Student();
        expect.setName("Joni");
        expect.setAge(45);
        studentRepository.save(expect);
        Student student = this.testTemplate.postForObject(
                "http://localhost:" + port + "/student", expect, Student.class);
        Assertions.assertThat(student).isNotNull();

        ResponseEntity<Student> responseEntity = this.testTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + student.getId(), Student.class);

        Assertions.assertThat(HttpStatus.OK).isEqualTo(responseEntity.getStatusCode());
        Assertions.assertThat(expect).isEqualTo(responseEntity.getBody());
    }

    @Test
    void findStudentTest() throws Exception {
        Student expect = new Student();
        expect.setName("Joni");
        expect.setAge(45);
        studentRepository.save(expect);
        Student student = this.testTemplate.getForObject(
                "http://localhost:" + port + "/student/" + expect.getId(), Student.class);

        Assertions.assertThat(student).isNotNull();
        Assertions.assertThat(student).isEqualTo(expect);

        ResponseEntity<Student> responseEntity = this.testTemplate.getForEntity(
                "http://localhost:" + port + "/student/" + student.getId(), Student.class);
        Assertions.assertThat(HttpStatus.OK).isEqualTo(responseEntity.getStatusCode());
        Assertions.assertThat(expect).isEqualTo(responseEntity.getBody());
    }

    @Test
    void editStudentTest() throws Exception {
        Student expect = new Student();
        expect.setName("Joni");
        expect.setAge(45);
        studentRepository.save(expect);
        Student changedStudent = new Student(expect.getId(), "Ford", 72);

        this.testTemplate.put("http://localhost:" + port + "/student", changedStudent);

        ResponseEntity<Void> responseEntity = this.testTemplate.exchange(
                "http://localhost:" + port + "/faculty",
                HttpMethod.PUT, new HttpEntity<>(changedStudent), Void.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        Student student = this.testTemplate.getForObject(
                "http://localhost:" + port + "/student/" + changedStudent.getId(), Student.class);
        assertThat(changedStudent).isEqualTo(student);
    }
    @Test
    void deleteStudentTest() throws Exception {
        Student studentToDelete = new Student(123L, "Ford", 72);
        studentRepository.save(studentToDelete);
        ResponseEntity<Void> responseEntity = this.testTemplate.exchange(
                "http://localhost:" + port + "/faculty/" + studentToDelete.getId(),
                HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        Faculty deletedStudent = this.testTemplate.getForObject(
                "http://localhost:" + port + "/faculty/" + studentToDelete.getId(), Faculty.class);
        assertThat(deletedStudent.getId()).isNull();
        assertThat(deletedStudent.getName()).isNull();
        assertThat(deletedStudent.getColor()).isNull();
        ResponseEntity<Faculty> deletedResponseEntity = this.testTemplate.getForEntity(
                "http://localhost:" + port + "/faculty/" + deletedStudent.getId(), Faculty.class);
        assertEquals(HttpStatus.BAD_REQUEST, deletedResponseEntity.getStatusCode());
    }

}