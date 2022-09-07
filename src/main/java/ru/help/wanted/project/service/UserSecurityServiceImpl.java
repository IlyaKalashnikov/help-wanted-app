package ru.help.wanted.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.help.wanted.project.model.token.PasswordResetToken;
import ru.help.wanted.project.repo.PasswordResetTokenRepo;

import java.util.Calendar;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserSecurityServiceImpl implements UserSecurityService{

    private final PasswordResetTokenRepo tokenRepo;

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = tokenRepo.findByToken(token);
        return passwordResetToken == null? "invalidToken"
                : isTokenExpired(passwordResetToken)? "expired"
                : null;
    }

    @Override
    public boolean isTokenExpired(PasswordResetToken passToken) {
        Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
