package pro.sky.java.cource4.ru.hogwarts.school1.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.java.cource4.ru.hogwarts.school1.model.Avatar;
import pro.sky.java.cource4.ru.hogwarts.school1.repositories.AvatarRepository;
import pro.sky.java.cource4.ru.hogwarts.school1.services.AvatarService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
@RestController
@RequestMapping(path = "/avatar")
public class AvatarController {
    private final AvatarService avatarService;
    private AvatarRepository avatarRepository;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar
            (@PathVariable long studentId, @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/avatar-preview/{studentId}")
    public ResponseEntity<byte[]> downloadPreview(@PathVariable Long studentId) {
        Avatar preview = avatarService.findAvatar(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(preview.getData().length);
        headers.setContentType(MediaType.parseMediaType(preview.getMediaType()));
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(preview.getData());
    }

    @GetMapping("/avatar-from-db/{studentId}")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long studentId) {
        Avatar avatar = avatarService.findAvatar(studentId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("avatar-from-file/{studentId}")
    public void downloadAvatar(@PathVariable Long studentId, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(studentId);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream();) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int)avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @DeleteMapping("/deleteAvatar/{studentId}")
    public ResponseEntity<Void> deleteAvatar(@PathVariable long studentId) {
        if (avatarService.findAvatar(studentId).getData()!=null) {
            avatarService.deleteAvatar(studentId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();}
    @GetMapping("/avatars")
    public Page<Avatar> getAllAvatars(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return avatarRepository.findAll(pageable);
    }
}
