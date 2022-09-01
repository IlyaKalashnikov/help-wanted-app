package ru.help.wanted.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.help.wanted.project.error.UserAlreadyExistException;
import ru.help.wanted.project.model.dto.AppUserDto;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.model.token.EmailVerificationToken;
import ru.help.wanted.project.repo.UserRepository;
import ru.help.wanted.project.security.EmailVerificationService;
import ru.help.wanted.project.service.UserService;
import ru.help.wanted.project.util.OnRegistrationCompleteEvent;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {
    private final EmailVerificationService emailVerificationService;

    private final UserService userService;
    private final MessageSource messages;
    private final ApplicationEventPublisher eventPublisher;
    private final UserRepository userRepository;

    @GetMapping("/user/registration")
    public String showRegistrationForm(final HttpServletRequest request, final Model model) {
        log.info("Started GET-requested method of registration");
        final AppUserDto accountDto = new AppUserDto();
        model.addAttribute("user", accountDto);
        return "registration";
    }

    @GetMapping("/registrationConfirm")
    @Transactional
    public String confirmRegistration(final HttpServletRequest request, final Model model, @RequestParam("token") final String token) {
        final Locale locale = request.getLocale();

        final EmailVerificationToken verificationToken = emailVerificationService.getVerificationToken(token);
        if (verificationToken == null) {
            final String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }
        final AppUser user = userRepository.findByEmail(verificationToken.getUser().getEmail());
        user.setEnabled(true);
        model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
        return "redirect:/login.html?lang=" + locale.getLanguage();
    }

    @PostMapping("/user/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid final AppUserDto userDto, final HttpServletRequest request, final Errors errors) {
        log.info("Started POST-requested method of registration \n user account information: {}", userDto.getEmail());
        try {
            AppUser registered = userService.registerNewUser(userDto);
            userService.addRoleToUser(userDto.getEmail(), "ROLE_USER");

            final String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
        } catch (final UserAlreadyExistException uaeEx) {
            ModelAndView mav = new ModelAndView("registration", "user", userDto);
            String errMessage = messages.getMessage("message.regError", null, request.getLocale());
            mav.addObject("message", errMessage);
            return mav;
        } catch (final RuntimeException ex) {
            return new ModelAndView("emailError", "user", userDto);
        }
        return new ModelAndView("successRegister", "user", userDto);
    }

}
