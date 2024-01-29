package pro.sky.java.cource4.ru.hogwarts.school1.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
    void deleteByStudentId(long studentId);
    Page<Avatar> findAll(Pageable pageable);
}
