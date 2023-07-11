package it.uniroma3.siw.OAuth;


import it.uniroma3.siw.service.UtenteOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

import static it.uniroma3.siw.model.Credentials.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * La sorgente dati (che contiene le credenziali) è
     * iniettata automaticamente
     */
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UtenteOAuth2UserService utenteOAuth2UserService;
    @Autowired
    private OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, ruolo from credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }


    @Bean
    protected SecurityFilterChain configureLogin(HttpSecurity http) throws Exception {
        System.out.println("FILTER CHAIN: INIZIO");
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/", "/index", "/register","/css/**", "/images/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/login", "/register").permitAll()
                .requestMatchers(HttpMethod.GET,"/autenticato/**").hasAnyAuthority(RUOLO_AUTORIZZATO, RUOLO_ADMIN, OAUTH2_USER)
                .requestMatchers(HttpMethod.POST,"/autenticato/**").hasAnyAuthority(RUOLO_AUTORIZZATO, RUOLO_ADMIN, OAUTH2_USER)
                .requestMatchers(HttpMethod.GET,"/admin/**").hasAnyAuthority(RUOLO_ADMIN, OAUTH2_USER)
                .requestMatchers(HttpMethod.POST,"/admin/**").hasAnyAuthority(RUOLO_ADMIN, OAUTH2_USER)
                .anyRequest().authenticated()
                .and()
                //Login
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/success", true)
                    .failureUrl("/login?error=true")
                .and()
                //Login con Github
                .oauth2Login()
                    .loginPage("/login")
                    .userInfoEndpoint()
                        .userService(utenteOAuth2UserService)
                    .and()
                    .successHandler(oAuth2LoginSuccessHandler)
                    .defaultSuccessUrl("/success", true)
                //Logout
                .and()
                .logout().logoutSuccessUrl("/").permitAll();
        System.out.println("FILTER CHAIN: FINE");
        return http.build();
    }

    /**
     * Questo metodo definisce il componente "passwordEncoder",
     * usato per criptare e decriptare la password nella sorgente dati.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}