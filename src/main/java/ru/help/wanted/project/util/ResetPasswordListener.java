package ru.help.wanted.project.util;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.service.UserService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ResetPasswordListener implements ApplicationListener<OnResetPasswordEvent> {
    private final JavaMailSender mailSender;
    private final UserService userService;
    private final MessageSource messages;

    @Override
    public void onApplicationEvent(OnResetPasswordEvent event) {
        this.resetPassword(event);
    }

    private void resetPassword(OnResetPasswordEvent event){
        AppUser appUser = event.getAppUser();
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(appUser, token);

        String recipientAddress = appUser.getEmail();
        String subject = "Reset password";
        String confirmationUrl
                = event.getAppUrl() + "/user/changePassword?token=" + token;
        String message = messages.getMessage("message.resetPasswordSuc", null, event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("i.e.kalashnikov@yandex.ru");
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + "\r\n" + confirmationUrl);
        mailSender.send(email);
    }
}
