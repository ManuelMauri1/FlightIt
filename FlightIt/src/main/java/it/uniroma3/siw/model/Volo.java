package it.uniroma3.siw.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;

import java.security.SecureRandom;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Data
@Entity
@AllArgsConstructor
public class Volo {
    private static final int CODICE = 10;
    private static final String CARATTERI = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String codiceVolo;
    private LocalDate dataPartenza;
    private Time oraPartenza;
    private Time oraArrivo;

    @ManyToMany
    private List<Utente> preferitiUtente;

    @ManyToOne
    private Aereoporto aereoportoPartenza;

    @ManyToOne
    private Aereoporto aereoportoArrivo;

    public Volo(){
        this.codiceVolo = generaCodiceVolo();
        preferitiUtente = new ArrayList<>();
    }

    public static String generaCodiceVolo() {
        StringBuilder sb = new StringBuilder(CODICE);
        Random random = new SecureRandom();

        for (int i = 0; i < CODICE; i++) {
            int randomIndex = random.nextInt(CARATTERI.length());
            char randomChar = CARATTERI.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Volo volo)) return false;
        return Objects.equals(getCodiceVolo(), volo.getCodiceVolo()) && Objects.equals(getDataPartenza(), volo.getDataPartenza());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodiceVolo(), getDataPartenza());
    }
    @Override
    public String toString() {
        return "Volo{" +
                "id=" + id +
                ", codiceVolo='" + codiceVolo + '\'' +
                ", dataPartenza=" + dataPartenza +
                ", oraPartenza=" + oraPartenza +
                ", oraArrivo=" + oraArrivo +
                ", preferitiUtente=" + preferitiUtente +
                ", aereoportoPartenza=" + aereoportoPartenza +
                ", aereoportoArrivo=" + aereoportoArrivo +
                '}';
    }
}
