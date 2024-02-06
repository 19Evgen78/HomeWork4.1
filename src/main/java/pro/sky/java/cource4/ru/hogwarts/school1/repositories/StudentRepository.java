package pro.sky.java.cource4.ru.hogwarts.school1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int min, int max);
    Student findStudentById(long id);
    @Query(value = "SELECT COUNT(*) FROM student",nativeQuery = true)
    int countStudents();
    @Query(value = "SELECT AVG(age) FROM student",nativeQuery = true)
    double getAverageAge();
    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5",nativeQuery = true)
    List<Student> findLastFiveStudents();
}
