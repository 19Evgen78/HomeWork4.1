package pro.sky.java.cource4.ru.hogwarts.school1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findByColor(String color);
    Faculty findFacultyByNameIgnoreCase(String name);
    Faculty findFacultyByColorIgnoreCase(String color);
    Faculty findFacultyById(long id);
}
