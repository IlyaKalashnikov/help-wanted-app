package ru.help.wanted.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.help.wanted.project.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    @Query("SELECT u FROM AppUser u WHERE u.username = :username")
    AppUser findByUsername(@Param("username") String username);
}
