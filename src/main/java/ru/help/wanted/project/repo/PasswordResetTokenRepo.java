package ru.help.wanted.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.model.token.PasswordResetToken;

@Repository
public interface PasswordResetTokenRepo extends JpaRepository<PasswordResetToken, Long> {

    @Query("SELECT t FROM PasswordResetToken t WHERE t.appUser = :user")
    PasswordResetToken findByUser(@Param("user")AppUser user);

    @Query("SELECT t FROM PasswordResetToken t WHERE t.token = :token")
    PasswordResetToken findByToken(@Param("token")String token);

}
