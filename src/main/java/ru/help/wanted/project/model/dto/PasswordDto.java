package ru.help.wanted.project.model.dto;

import lombok.Data;
import ru.help.wanted.project.validation.ValidPassword;

@Data
public class PasswordDto {

    private String oldPassword;
    private String token;
    @ValidPassword
    private String newPassword;
}
