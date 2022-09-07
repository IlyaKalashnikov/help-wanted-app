package ru.help.wanted.project.service;

import ru.help.wanted.project.model.token.PasswordResetToken;

public interface UserSecurityService {
    String validatePasswordResetToken(String token);
    boolean isTokenExpired(PasswordResetToken passToken);
}
