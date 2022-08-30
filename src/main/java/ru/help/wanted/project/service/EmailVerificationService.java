package ru.help.wanted.project.service;

import ru.help.wanted.project.error.UserAlreadyExistException;
import ru.help.wanted.project.model.dto.AppUserDto;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.model.token.EmailVerificationToken;

public interface EmailVerificationService {

//    AppUser registerNewUserAccount(AppUserDto userDto)
//            throws UserAlreadyExistException;
//
//    AppUser getUser(String verificationToken);
//
//    void saveRegisteredUser(AppUser user);

    void createVerificationToken(AppUser user, String token);

    EmailVerificationToken getVerificationToken(String VerificationToken);
}
