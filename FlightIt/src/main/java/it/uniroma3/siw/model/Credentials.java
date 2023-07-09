package it.uniroma3.siw.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Credentials {
    public static final String RUOLO_AUTORIZZATO = "AUTORIZZATO";
    public static final String RUOLO_ADMIN = "ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String password;
    private String username;
    private String ruolo;
}
