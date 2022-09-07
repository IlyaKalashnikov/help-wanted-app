package ru.help.wanted.project.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.help.wanted.project.filter.CustomAuthenticationFilter;
import ru.help.wanted.project.filter.CustomAuthorizationFilter;
import ru.help.wanted.project.repo.UserRepository;
import ru.help.wanted.project.security.CustomUserDetailsService;
import ru.help.wanted.project.util.SecureRandomBytesGenerator;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUserDetailsService userDetailsService;
    private final SecureRandomBytesGenerator secureRandomBytesGenerator;
    private final UserRepository userRepository;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter authenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean(), secureRandomBytesGenerator, userRepository);
//        authenticationFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/user/registration*","/registrationConfirm*",
                        "/badUser*", "/successRegister*", "/user/resetPassword*", "/forgetPassword*",
                        "/user/changePassword*", "/updatePassword*")
                .permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/getAds", "/api/token/refresh/**")
                .permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/users")
                .hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/createAd")
                .hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user/save")
                .hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().anyRequest().authenticated();
        http.formLogin()
                .loginPage("/login.html")
                .permitAll()
                .and()
                .logout()
                .permitAll();
        http.addFilter(authenticationFilter);
        http.addFilterBefore
                (new CustomAuthorizationFilter(secureRandomBytesGenerator), UsernamePasswordAuthenticationFilter.class);
    }
}
