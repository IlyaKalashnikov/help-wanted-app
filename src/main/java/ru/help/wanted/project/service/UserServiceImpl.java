package ru.help.wanted.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.help.wanted.project.model.AppUser;
import ru.help.wanted.project.model.Role;
import ru.help.wanted.project.repo.RoleRepository;
import ru.help.wanted.project.repo.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username);
        if (appUser == null){
            log.info("User {} not found in db", username);
            throw new UsernameNotFoundException("User not found in db");
        } else {
            log.info("Found user {} in db", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(
                role -> authorities.add(new SimpleGrantedAuthority(role.getName()))
        );
        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving user '{}' to db", appUser.getUsername());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }

    @Override
    public AppUser findUserByUsername(String username) {
        log.info("Searching for user with username '{}' in db", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving role '{}' to db", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        AppUser appUser = userRepository.findByUsername(username);
        Role role = roleRepository.findByRoleName(roleName);
        appUser.getRoles().add(role);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Getting all users");
        return userRepository.findAll();
    }
}
