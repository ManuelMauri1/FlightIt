package it.uniroma3.siw.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Volo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer codiceVolo;
    private LocalDate dataPartenza;
    private Time oraPartenza;
    private Time oraArrivo;

    private String aereoportoPartenza;

    private String aereoportoArrivo;
}
