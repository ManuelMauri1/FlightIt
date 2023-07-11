package it.uniroma3.siw.model;

import it.uniroma3.siw.OAuth.AuthProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    public static final String RUOLO_AUTORIZZATO = "AUTORIZZATO";
    public static final String RUOLO_ADMIN = "ADMIN";
    public static final String OAUTH2_USER = "OAUTH2_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    private String username;
    private String ruolo;

    @OneToOne
    private Utente utente;

    @Enumerated(EnumType.STRING)
    @Column(name="auth_provider")
    private AuthProvider provider;
}
