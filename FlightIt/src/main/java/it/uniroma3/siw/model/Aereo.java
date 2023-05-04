package it.uniroma3.siw.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Aereo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
