package pro.sky.java.cource4.ru.hogwarts.school1.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
public class InfoServiceImpl implements InfoService {
    @Value("${server.port}")
    private Integer port;
    @Override
    public Integer getPort() {
        return port;
    }
}
