package ru.help.wanted.project;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.model.entity.Role;
import ru.help.wanted.project.service.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class HelpWantedApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelpWantedApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunnerBean(UserService userService) {
//        return args -> {
//            userService.saveRole(new Role(null, "ROLE_ADMIN"));
//            userService.saveRole(new Role(null, "ROLE_USER"));
//
//            userService.saveUser(new AppUser
//                    (null, "Svetlana", "svetusa123", "Gunabasic2000", "svetlanabel2000@mail.ru", false, new ArrayList<>()));
//            userService.saveUser(new AppUser
//                    (null, "Ilya", "iluha", "753476Kk", "k1alashilya@yandex.ru", false, new ArrayList<>()));
//
//            userService.addRoleToUser("svetlanabel2000@mail.ru", "ROLE_USER");
//            userService.addRoleToUser("kalashilya@yandex.ru", "ROLE_ADMIN");
//        };
//    }

}
