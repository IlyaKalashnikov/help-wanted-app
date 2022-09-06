package ru.help.wanted.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.help.wanted.project.error.UserAlreadyExistException;
import ru.help.wanted.project.model.dto.AppUserDto;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.service.UserService;
import ru.help.wanted.project.util.GenericResponse;
import ru.help.wanted.project.util.OnRegistrationCompleteEvent;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NewRegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @PostMapping("/user/registration")
    public GenericResponse registerNewUser(@Valid AppUserDto userDto, HttpServletRequest request){
        AppUser registered = userService.registerNewUser(userDto);
        if (registered == null){
            throw new UserAlreadyExistException();
        }
        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));

        return new GenericResponse("success");
    }
}
