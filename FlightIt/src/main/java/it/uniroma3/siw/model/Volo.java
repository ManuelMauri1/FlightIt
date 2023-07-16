package it.uniroma3.siw.model;

import it.uniroma3.siw.service.VoloService;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.SecureRandom;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
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

    public boolean checkTempo() {
        return this.oraPartenza.before(this.oraArrivo);
    }

    public Float calcoloTempoVolo() {
        float tempoVolo;
        Float oraPartenza = (float) this.getOraPartenza().getTime();
        Float oraArrivo = (float) this.getOraArrivo().getTime();
        tempoVolo = oraArrivo - oraPartenza;
        return tempoVolo;
    }


    public Float calcoloTempoPartenza() {
        Float tempoVolo = calcoloTempoVolo();
        Float oraPartenza = (float) this.getOraPartenza().getTime();
        Float oraAttuale = (float) new Date().getTime();
        Float tempoAttuale = oraAttuale - oraPartenza;
        return (tempoAttuale / tempoVolo) * 100;
    }
}

