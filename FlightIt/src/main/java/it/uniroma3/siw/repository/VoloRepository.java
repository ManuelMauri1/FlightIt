package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Volo;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface VoloRepository extends CrudRepository<Volo, Long> {
    public List<Volo> findAll();
    public boolean existsByCodiceVoloAndDataPartenza(String codiceVolo, LocalDate dataPartenza);

}
