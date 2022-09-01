package ru.help.wanted.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.help.wanted.project.model.dto.AppUserDto;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.model.entity.Role;
import ru.help.wanted.project.repo.RoleRepository;
import ru.help.wanted.project.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving user '{}' to db", appUser.getEmail());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        log.info("Searching for user with username '{}' in db", username);
        return userRepository.findByEmail(username);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role '{}' to db", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        AppUser appUser = userRepository.findByEmail(username);
        Role role = roleRepository.findByRoleName(roleName);
        appUser.getRoles().add(role);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }

    @Override
    public AppUser registerNewUser(AppUserDto userDto) {
        log.info("Checking for email {} to be present in db", userDto.getEmail());
        if (emailExists(userDto.getEmail())) {
            throw new UsernameNotFoundException("There is an account with that email address: "
                    + userDto.getEmail());
        }
        AppUser appUser = AppUser.builder()
                .id(null)
                .name(userDto.getFirstName())
                .secondName(userDto.getLastName())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .roles(new ArrayList<>())
                //ИСПРАВИТЬ!!!!!!!!!!!
                .enabled(true)
                .build();
        return userRepository.save(appUser);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
