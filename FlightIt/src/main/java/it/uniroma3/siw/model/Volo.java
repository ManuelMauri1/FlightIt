package it.uniroma3.siw.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Volo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
