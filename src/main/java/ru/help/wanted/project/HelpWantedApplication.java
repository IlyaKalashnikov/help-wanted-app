package ru.help.wanted.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.help.wanted.project.model.AppUser;
import ru.help.wanted.project.model.Role;
import ru.help.wanted.project.service.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class HelpWantedApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelpWantedApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunnerBean(UserService userService) {
        return args -> {
            userService.saveRole(new Role(null, "ROLE_ADMIN"));
            userService.saveRole(new Role(null, "ROLE_USER"));

            userService.saveUser(new AppUser(null, "Svetlana", "svetusa123", "Gunabasic2000", new ArrayList<>()));
            userService.saveUser(new AppUser(null, "Ilya", "iluha", "753476Kk", new ArrayList<>()));

            userService.addRoleToUser("svetusa123", "ROLE_USER");
            userService.addRoleToUser("iluha", "ROLE_ADMIN");
        };
    }

}
