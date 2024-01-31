package pro.sky.java.cource4.ru.hogwarts.school1.services;

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

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.findStudentById(id);
    }

    public Student updateStudent(long id, Student student) {
        Student updatedStudent = studentRepository.findStudentById(id);
        if (updatedStudent == null) {
            return null;
        }
        updatedStudent.setName(student.getName());
        updatedStudent.setAge(student.getAge());
        return studentRepository.save(updatedStudent);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    public int countStudents() {
        return studentRepository.countStudents();
    }
    public double getAverageAge() {
        return studentRepository.getAverageAge();
    }
    public List<Student> findLastFiveStudents() {
        return studentRepository.findLastFiveStudents();
    }

    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Faculty getStudentsFaculty(long id) {
        return studentRepository.findStudentById(id).getFaculty();
    }

    public Collection<Student> filterAge(int age) {
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }
    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
