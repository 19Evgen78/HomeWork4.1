package pro.sky.java.cource4.ru.hogwarts.school1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.java.cource4.ru.hogwarts.school1.services.InfoService;

@RestController
@RequestMapping("/port")
public class InfoController {
    private final InfoService infoService;
    @Autowired
    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping
    public Integer getPort() {
        return infoService.getPort();
    }
}
