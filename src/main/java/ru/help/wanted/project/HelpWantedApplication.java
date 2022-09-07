package ru.help.wanted.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.model.entity.Role;
import ru.help.wanted.project.repo.UserRepository;
import ru.help.wanted.project.service.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class HelpWantedApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelpWantedApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunnerBean(UserService UserService, UserRepository userRepository) {
        return args -> {
            UserService.saveRole(new Role(null, "ROLE_ADMIN"));
            UserService.saveRole(new Role(null, "ROLE_USER"));

            UserService.saveUser(new AppUser
                    (null, "Ilya", "iluha", "753476Kk", "kalashilya@yandex.ru", true, new ArrayList<>()));
            UserService.addRoleToUser("kalashilya@yandex.ru", "ROLE_ADMIN");
        };
    }

}
