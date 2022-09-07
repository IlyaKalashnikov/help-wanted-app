package ru.help.wanted.project.model.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.help.wanted.project.model.entity.AppUser;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class PasswordResetToken {
    private static final int EXPIRATION = 60;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String token;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "app_user_id")
    private AppUser appUser;
    private Date expiryDate;

    public PasswordResetToken(final String token, final AppUser user) {
        this.token = token;
        this.appUser = user;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(final int expiration){
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiration);
        return new Date(cal.getTime().getTime());
    }
}
