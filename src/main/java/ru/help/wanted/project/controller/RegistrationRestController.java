package ru.help.wanted.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.help.wanted.project.error.UserAlreadyExistException;
import ru.help.wanted.project.error.UserNotFoundException;
import ru.help.wanted.project.model.dto.AppUserDto;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.service.UserService;
import ru.help.wanted.project.util.GenericResponse;
import ru.help.wanted.project.util.OnRegistrationCompleteEvent;
import ru.help.wanted.project.util.OnResetPasswordEvent;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegistrationRestController {
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    private final MessageSource messages;

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

    @PostMapping("/user/resetPassword")
    public GenericResponse resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) {
        AppUser user = userService.findUserByEmail(userEmail);
        if (user == null) {
            throw new UserNotFoundException();
        }
        final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        eventPublisher.publishEvent(new OnResetPasswordEvent(user, appUrl, request.getLocale()));
        return new GenericResponse(
                messages.getMessage("message.resetPasswordEmail", null,
                        request.getLocale()));
    }
}
