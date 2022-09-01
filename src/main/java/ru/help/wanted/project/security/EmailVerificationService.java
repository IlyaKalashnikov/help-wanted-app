package ru.help.wanted.project.security;

import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.model.token.EmailVerificationToken;

public interface EmailVerificationService {

    void createVerificationToken(AppUser user, String token);

    EmailVerificationToken getVerificationToken(String VerificationToken);
}
