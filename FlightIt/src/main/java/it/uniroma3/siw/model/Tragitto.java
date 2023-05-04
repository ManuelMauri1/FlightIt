package it.uniroma3.siw.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Tragitto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
