package ru.help.wanted.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.service.UserService;
import ru.help.wanted.project.util.RefreshTokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService UserService;
    private final RefreshTokenUtil refreshTokenUtil;

    @GetMapping("/users")
    public List<AppUser> getUsers() {
        return UserService.getUsers();
    }

    @PostMapping("/user/save")
    public AppUser saveUser(@RequestBody AppUser user) {
        return UserService.saveUser(user);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        refreshTokenUtil.refreshToken(request, response);
    }
}
