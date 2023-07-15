package it.uniroma3.siw.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.hibernate.query.sql.internal.ParameterRecognizerImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;
    private String cognome;
    private LocalDate dataNascita;

    @OneToMany
    @JoinColumn(name = "preferiti_id")
    private List<Volo> preferiti;

    public Utente(){
        preferiti = new ArrayList<>();
    }

    public Utente(String nome, String cognome){
        this();
        setNome(nome);
        setCognome(cognome);
    }
}
