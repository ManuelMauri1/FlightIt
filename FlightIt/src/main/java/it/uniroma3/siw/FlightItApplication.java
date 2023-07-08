package it.uniroma3.siw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@SpringBootApplication
@EnableWebSecurity
public class FlightItApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightItApplication.class, args);
    }

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User user) {
        return Collections.singletonMap("name", user.getAttribute("name"));
    }

    @Bean
    protected SecurityFilterChain configureLogin(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(a -> a
                        .requestMatchers("/", "/error", "/webjars/**").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
                )
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .clearAuthentication(true).permitAll()
                )
                .oauth2Login();
        return http.build();
    }
}
