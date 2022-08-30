package ru.help.wanted.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.help.wanted.project.model.entity.AppUser;
import ru.help.wanted.project.model.token.EmailVerificationToken;

public interface VerificationTokenRepo extends JpaRepository<EmailVerificationToken,Long> {

    @Query("SELECT t FROM EmailVerificationToken t WHERE t.token = :token")
    EmailVerificationToken findByToken(@Param("token") String token);

    @Query("SELECT t FROM EmailVerificationToken t WHERE t.user = :user")
    EmailVerificationToken findByUser(@Param("user")AppUser appUser);
}
