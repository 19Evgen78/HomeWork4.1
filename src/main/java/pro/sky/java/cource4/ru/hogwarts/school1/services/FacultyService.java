package pro.sky.java.cource4.ru.hogwarts.school1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Faculty;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Student;
import pro.sky.java.cource4.ru.hogwarts.school1.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
@Service
public class FacultyService {
    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        return facultyRepository.findFacultyById(id);
    }

    public Faculty updateFaculty(long id, Faculty faculty) {
        Faculty updatedFaculty = facultyRepository.findFacultyById(id);
        if (updatedFaculty == null) {
            return null;
        }
        updatedFaculty.setName(faculty.getName());
        updatedFaculty.setColor(faculty.getColor());
        return facultyRepository.save(updatedFaculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }
    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty findByName(String name) {
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public Faculty findByColor(String color) {
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }

    public Collection<Student> getStudentsOfFaculty(long id) {
        Faculty faculty = facultyRepository.findFacultyById(id);
        return (faculty != null) ? faculty.getStudent() : Collections.emptyList();
    }

    public Collection<Faculty> filterColor(String color) {
        return facultyRepository.findAll()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
