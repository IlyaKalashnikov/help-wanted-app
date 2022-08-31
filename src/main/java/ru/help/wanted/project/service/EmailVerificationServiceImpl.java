package ru.help.wanted.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.model.token.EmailVerificationToken;
import ru.help.wanted.project.repo.VerificationTokenRepo;


@Service
@Transactional
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService{
    private final VerificationTokenRepo verificationTokenRepo;

    @Override
    public void createVerificationToken(AppUser user, String token) {
        EmailVerificationToken myToken = new EmailVerificationToken(token, user);
        verificationTokenRepo.save(myToken);
    }

    @Override
    public EmailVerificationToken getVerificationToken(String VerificationToken) {
        return verificationTokenRepo.findByToken(VerificationToken);
    }
}
