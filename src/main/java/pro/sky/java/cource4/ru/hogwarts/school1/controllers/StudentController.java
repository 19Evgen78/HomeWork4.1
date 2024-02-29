package pro.sky.java.cource4.ru.hogwarts.school1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Faculty;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Student;
import pro.sky.java.cource4.ru.hogwarts.school1.repositories.StudentRepository;
import pro.sky.java.cource4.ru.hogwarts.school1.services.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    private StudentRepository studentRepository;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }
    @GetMapping("findAllStudents")
    public ResponseEntity<Collection<Student>> getAllStudent() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> findStudent(@PathVariable long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Student> editStudent(@PathVariable long id, @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(id, student);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/ageBetween/{min}/{max}")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@PathVariable int min, @PathVariable int max) {
        Collection<Student> students = studentService.findByAgeBetween(min, max);
        if (students.isEmpty()) {
            ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("/studentsFaculty/{id}")
    public ResponseEntity<Faculty> getStudentsFaculty(@PathVariable long id) {
        return ResponseEntity.ok(studentService.getStudentsFaculty(id));
    }


    @GetMapping("/ageFilter/{age}")
    public Collection<Student> ageFilteredStudents(@PathVariable int age) {

        return studentService.filterAge(age);
    }
    @GetMapping("/students/count")
    public int getStudentCount() {
        return studentService.countStudents();
    }
    @GetMapping("/students/average-age")
    public double getAverageStudentAge() {
        return studentService.getAverageAge();
    }
    @GetMapping("/students/last-five")
    public List<Student> getLastFiveStudents() {
        return studentService.findLastFiveStudents();
    }
    @GetMapping("/student-name-A")
    public ResponseEntity findStudentsNameWithA() {
        List<String> students = studentService.findStudentsNameWithA();
        return ResponseEntity.ok(students);
    }
    @GetMapping("/students/average-age-stream")
    public ResponseEntity findStudentAverageAge () {
        Double age = studentService.findStudentAverageAge();
        return ResponseEntity.ok(age);
    }@GetMapping("/print-parallel")
    public void printStudentsInParallel() {
        System.out.println("Printing students in parallel...");

        Thread t1 = new Thread(() -> {
            studentService.printStudentName(0);
            studentService.printStudentName(1);
        });

        Thread t2 = new Thread(() -> {
            studentService.printStudentName(2);
            studentService.printStudentName(3);
        });

        Thread t3 = new Thread(() -> {
            studentService.printStudentName(4);
            studentService.printStudentName(5);
        });

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Printing students in parallel finished.");
    }

    @GetMapping("/print-synchronized")
    public void printStudentsSynchronized() {
        System.out.println("Printing students synchronized...");

        studentService.printStudentNameSync(0);
        studentService.printStudentNameSync(1);

        Thread t1 = new Thread(() -> {
            studentService.printStudentNameSync(2);
            studentService.printStudentNameSync(3);
        });

        Thread t2 = new Thread(() -> {
            studentService.printStudentNameSync(4);
            studentService.printStudentNameSync(5);
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Printing students synchronized finished.");
    }

}
