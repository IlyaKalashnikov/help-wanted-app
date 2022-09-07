package ru.help.wanted.project.service;

import ru.help.wanted.project.model.dto.AppUserDto;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.model.entity.Role;

import java.util.List;

public interface UserService {

    AppUser saveUser(AppUser appUser);
    AppUser findUserByEmail(String email);
    Role saveRole(Role role);
    void addRoleToUser(String username, String roleName);
    List<AppUser> getUsers();
    AppUser registerNewUser(AppUserDto userDto);
    boolean emailExists(String email);
    void createPasswordResetTokenForUser(final AppUser user, final String token);
}
