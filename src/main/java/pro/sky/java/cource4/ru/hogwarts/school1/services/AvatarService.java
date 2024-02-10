package pro.sky.java.cource4.ru.hogwarts.school1.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Avatar;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Student;
import pro.sky.java.cource4.ru.hogwarts.school1.repositories.AvatarRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;
@Service
@Transactional
public class AvatarService {
    @Value("${path.to.avatars.folder}")
    private String avatarDir;
    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    private static final Logger logger = LoggerFactory.getLogger(AvatarService.class);
    @Autowired
    public AvatarService(AvatarRepository avatarRepository, StudentService studentService) {
        this.avatarRepository = avatarRepository;
        this.studentService = studentService;
    }
    public void uploadAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.info("Upload avatar for student with ID {}", studentId);
        Student student = studentService.findStudent(studentId);
        Path filePath = Path.of(avatarDir, student.getName() + "." + getExtension(Objects.requireNonNull(file.getOriginalFilename())));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream io = file.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(io, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024)
        ) {
            bis.transferTo(bos);
        }
        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setData(file.getBytes());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateImagePreview(filePath));
        avatarRepository.save(avatar);
    }
    public void deleteAvatar(long studentId) {
        logger.info("Delete avatar for student with ID {}", studentId);
        avatarRepository.deleteByStudentId(studentId);
    }
    public Avatar findAvatar(long studentId) {
        logger.info("Find avatar for student with ID {}", studentId);
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }
    public List<Avatar> getAllAvatars(Integer number, Integer size) {
        logger.info("Get all avatars. Page number: {}, Page size: {}", number, size);
        PageRequest pageRequest = PageRequest.of(number - 1, size);
        return avatarRepository.findAll(pageRequest).getContent();
    }
    private byte[] generateImagePreview(Path filePath) throws IOException {
        logger.info("Generating image preview for file: {}", filePath);
        try (InputStream is = Files.newInputStream(filePath);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            BufferedImage image = ImageIO.read(bis);
            int high = image.getHeight() / (image.getWidth() / 100);
            BufferedImage preview = new BufferedImage(100, high, image.getType());
            Graphics2D graphics = preview.createGraphics();
            graphics.drawImage(image, 0, 0, 100, high, null);
            graphics.dispose();

            ImageIO.write(preview, getExtension(filePath.getFileName().toString()), baos);
            return baos.toByteArray();
        }
    }
    private String getExtension(String filename) {
        logger.debug("Get extension.Filename:{}",filename);
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
