package ru.help.wanted.project.util;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@Getter
public class SecureRandomBytesGenerator {

    private final byte [] generatedBytes;

    //Так как по дефолту scope sping bean'ов синглтон, мы уверены,
    //что generatedBytes будут инициализированы один раз
    //и логика конструктора отработает один раз, значит для всех фильтров
    //ключ будет одним и тем же
    public SecureRandomBytesGenerator() {
        this.generatedBytes = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(generatedBytes);
    }
}
