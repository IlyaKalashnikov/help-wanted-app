package ru.help.wanted.project.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.help.wanted.project.util.SecureRandomBytesGenerator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    private final SecureRandomBytesGenerator randomBytesGenerator;

    /*
    OncePerRequestFilter отрабатывает при каждом запросе в приложение
    Здесь я настраиваю OncePerRequestFilter принимать пользовательский JWT
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /*
        Если пользователь хочет авторизоваться, фильтр не проверяет JWT,
        а просто передает запрос дальше по цепочке фильтров
        */
        if (request.getServletPath().equals("/api/login") || request.getServletPath().equals("/api/token/refresh/**")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            /*
            Проверка, есть ли у человека JWT, присваивая пользователю JWT,
            мы будем добавлять 'Bearer ', соответственно, если проверка проходит,
            то все последующие запросы производим уже через токен
            */
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                try {
                    String token = authorizationHeader.substring("Bearer ".length());
                    Algorithm algorithm = Algorithm.HMAC256(randomBytesGenerator.getGeneratedBytes());
                    JWTVerifier verifier = JWT.require(algorithm).build();
                    DecodedJWT decodedToken = verifier.verify(token);
                    String username = decodedToken.getSubject();
                    String[] roles = decodedToken.getClaim("roles").asArray(String.class);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    Stream.of(roles).forEach(role -> {
                        authorities.add(new SimpleGrantedAuthority(role));
                    });
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);
                    /*
                    Добавляем полученный токен в SecurityContext, так, залогиненный пользователь
                    сможет спокойно пользоваться приложением в рамках своей роли
                    */
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    //Передаем запрос дальше по цепи филтров
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    log.info("Error logging in: {}", exception.getMessage());
                    response.setHeader("error", exception.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", exception.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
