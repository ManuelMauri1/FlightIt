package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Aereoporto;
import it.uniroma3.siw.model.Volo;
import org.springframework.data.repository.CrudRepository;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public interface VoloRepository extends CrudRepository<Volo, Long> {
    public List<Volo> findAll();
    public boolean existsByCodiceVoloAndDataPartenza(String codiceVolo, LocalDate dataPartenza);
    public List<Volo> findAllByIdNotIn(List<Long> idVoliEsclusi);
    public boolean existsVoloByDataPartenzaAndAereoportoPartenzaAndAereoportoArrivoAndOraPartenza(LocalDate dataPartenza, Aereoporto aereoportoPartenza, Aereoporto aereoportoArrivo, Time oraPartenza);
}
