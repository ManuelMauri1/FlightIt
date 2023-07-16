package it.uniroma3.siw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Aereoporto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nome;

    @OneToMany(mappedBy = "aereoportoArrivo")
    private List<Volo> voliInEntrata;

    @OneToMany(mappedBy = "aereoportoPartenza")
    private List<Volo> voliInUscita;

    @Override
    public String toString() {
        return "Aereoporto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", voliInEntrata=" + voliInEntrata +
                ", voliInUscita=" + voliInUscita +
                '}';
    }
}