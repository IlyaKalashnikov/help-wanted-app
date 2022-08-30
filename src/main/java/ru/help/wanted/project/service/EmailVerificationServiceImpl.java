package ru.help.wanted.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.help.wanted.project.error.UserAlreadyExistException;
import ru.help.wanted.project.model.dto.AppUserDto;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.model.token.EmailVerificationToken;
import ru.help.wanted.project.repo.UserRepository;
import ru.help.wanted.project.repo.VerificationTokenRepo;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class EmailVerificationServiceImpl implements EmailVerificationService{
//    private final UserRepository userRepository;
//    private final UserService userService;
    private final VerificationTokenRepo verificationTokenRepo;

//    @Override
//    public AppUser registerNewUserAccount(AppUserDto userDto) throws UserAlreadyExistException {
//        if (emailExist(userDto.getEmail())) {
//            throw new UserAlreadyExistException(
//                    "There is an account with that email adress: "
//                            + userDto.getEmail());
//        }
//        AppUser appUser = AppUser.builder()
//                .id(null)
//                .name(userDto.getFirstName())
//                .secondName(userDto.getLastName())
//                .password(userDto.getPassword())
//                .email(userDto.getEmail())
//                .roles(new ArrayList<>())
//                .build();
//        userService.saveUser(appUser);
//        userService.addRoleToUser(appUser.getEmail(), "ROLE_USER");
//        return appUser;
//    }
//
//    private boolean emailExist(String email) {
//        return userRepository.findByEmail(email) != null;
//    }
//
//    @Override
//    public AppUser getUser(String verificationToken) {
//        AppUser user = verificationTokenRepo.findByToken(verificationToken).getUser();
//        return user;
//    }
//
//    @Override
//    public void saveRegisteredUser(AppUser user) {
//        userService.saveUser(user);
//    }

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
