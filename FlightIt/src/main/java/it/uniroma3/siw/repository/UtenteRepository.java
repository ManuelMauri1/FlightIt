package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.Volo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UtenteRepository extends CrudRepository<Utente, Long> {
}
