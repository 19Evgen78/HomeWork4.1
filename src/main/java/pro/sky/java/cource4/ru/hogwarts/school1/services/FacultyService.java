package pro.sky.java.cource4.ru.hogwarts.school1.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Creating new faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(Long id) {
        logger.info("Finding faculty by id: " + id);
        return facultyRepository.findFacultyById(id);
    }

    public Faculty updateFaculty(long id, Faculty faculty) {
        logger.info("Updating faculty with id: " + id);
        Faculty updatedFaculty = facultyRepository.findFacultyById(id);
        if (updatedFaculty == null) {
            return null;
        }
        updatedFaculty.setName(faculty.getName());
        updatedFaculty.setColor(faculty.getColor());
        return facultyRepository.save(updatedFaculty);
    }

    public void deleteFaculty(Long id) {
        logger.info("Deleting faculty with id: " + id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Retrieving all faculties");
        return facultyRepository.findAll();
    }

    public Faculty findByName(String name) {
        logger.info("Finding faculty by name: " + name);
        return facultyRepository.findFacultyByNameIgnoreCase(name);
    }

    public Faculty findByColor(String color) {
        logger.info("Finding faculty by color: " + color);
        return facultyRepository.findFacultyByColorIgnoreCase(color);
    }

    public Collection<Student> getStudentsOfFaculty(long id) {
        logger.info("Retrieving students of faculty with id: " + id);
        Faculty faculty = facultyRepository.findFacultyById(id);
        return (faculty != null) ? faculty.getStudent() : Collections.emptyList();
    }

    public Collection<Faculty> filterColor(String color) {
        logger.info("Filtering faculties by color: " + color);
        return facultyRepository.findAll()
                .stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toList());
    }
}
