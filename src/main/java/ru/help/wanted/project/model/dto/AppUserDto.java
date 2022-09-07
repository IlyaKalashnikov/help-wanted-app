package ru.help.wanted.project.model.dto;

import lombok.Getter;
import lombok.Setter;
import ru.help.wanted.project.validation.PasswordMatches;
import ru.help.wanted.project.validation.ValidEmail;
import ru.help.wanted.project.validation.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches
@Getter
@Setter
public class AppUserDto {
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;
    @ValidPassword
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    private String matchingPassword;

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [firstName=")
                .append(firstName)
                .append(", lastName=")
                .append(lastName)
                .append(", email=")
                .append(email)
                .append(", role=")
                .append("ROLE_USER").append("]");
        return builder.toString();
    }
}
