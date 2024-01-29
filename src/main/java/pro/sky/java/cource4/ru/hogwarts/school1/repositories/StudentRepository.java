package pro.sky.java.cource4.ru.hogwarts.school1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int min, int max);
    Student findStudentById(long id);
    @Query("SELECT COUNT(*) FROM Student")
    int countStudents();
    @Query("SELECT AVG(age) FROM Student")
    double getAverageAge();
    @Query("SELECT * FROM student ORDER BY id DESC LIMIT 5")
    List<Student> findLastFiveStudents();
}