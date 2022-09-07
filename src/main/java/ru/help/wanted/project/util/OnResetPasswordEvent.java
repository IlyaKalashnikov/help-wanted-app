package ru.help.wanted.project.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import ru.help.wanted.project.model.entity.AppUser;

import java.util.Locale;

@Getter
@Setter
public class OnResetPasswordEvent extends ApplicationEvent {
    private AppUser appUser;
    private String appUrl;
    private Locale locale;

    public OnResetPasswordEvent(AppUser appUser, String appUrl, Locale locale) {
        super(appUser);

        this.appUser = appUser;
        this.appUrl = appUrl;
        this.locale = locale;
    }
}
