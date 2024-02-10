package pro.sky.java.cource4.ru.hogwarts.school1.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Faculty;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Student;
import pro.sky.java.cource4.ru.hogwarts.school1.repositories.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Creating student with name: " + student.getName());
        Student createdStudent = studentRepository.save(student);
        logger.info("Student created with ID: " + createdStudent.getId());
        return createdStudent;
    }

    public Student findStudent(Long id) {
        logger.info("Finding student with ID: " + id);
        Student foundStudent = studentRepository.findStudentById(id);
        if (foundStudent == null) {
            logger.warn("Student not found with ID: " + id);
        } else {
            logger.info("Student found: " + foundStudent.getName());
        }
        return foundStudent;
    }

    public Student updateStudent(long id, Student student) {
        logger.info("Updating student with ID: " + id);
        Student updatedStudent = studentRepository.findStudentById(id);
        if (updatedStudent == null) {
            logger.warn("Student not found with ID: " + id);
            return null;
        }
        updatedStudent.setName(student.getName());
        updatedStudent.setAge(student.getAge());
        Student savedStudent = studentRepository.save(updatedStudent);
        logger.info("Student updated: " + savedStudent.getName());
        return savedStudent;
    }

    public void deleteStudent(Long id) {
        logger.info("Deleting student with ID: " + id);
        studentRepository.deleteById(id);
        logger.info("Student deleted with ID: " + id);
    }

    public int countStudents() {
        logger.info("Counting students");
        int count = studentRepository.countStudents();
        logger.info("Total number of students: " + count);
        return count;
    }

    public double getAverageAge() {
        logger.info("Getting average age of students");
        double averageAge = studentRepository.getAverageAge();
        logger.info("Average age of students: " + averageAge);
        return averageAge;
    }

    public List<Student> findLastFiveStudents() {
        logger.info("Finding last five students");
        List<Student> lastFiveStudents = studentRepository.findLastFiveStudents();
        logger.info("Last five students: " + lastFiveStudents.toString());
        return lastFiveStudents;
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Finding students with age between " + min + " and " + max);
        Collection<Student> students = studentRepository.findByAgeBetween(min, max);
        logger.info("Students found: " + students.toString());
        return students;
    }

    public Faculty getStudentsFaculty(long id) {
        logger.info("Getting faculty of student with ID: " + id);
        Student student = studentRepository.findStudentById(id);
        if (student == null) {
            logger.warn("Student not found with ID: " + id);
            return null;
        }
        Faculty faculty = student.getFaculty();
        logger.info("Faculty of student: " + faculty.toString());
        return faculty;
    }

    public Collection<Student> filterAge(int age) {
        logger.info("Filtering students by age: " + age);
        Collection<Student> filteredStudents = studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
        logger.info("Filtered students: " + filteredStudents.toString());
        return filteredStudents;
    }

    public Collection<Student> getAllStudents() {
        logger.debug("Getting all students");
        Collection<Student> allStudents = studentRepository.findAll();
        logger.debug("All students: " + allStudents.toString());
        return allStudents;
    }
}
