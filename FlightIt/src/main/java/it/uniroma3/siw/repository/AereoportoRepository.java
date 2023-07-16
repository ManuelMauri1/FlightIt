package it.uniroma3.siw.repository;

import it.uniroma3.siw.model.Aereoporto;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AereoportoRepository extends CrudRepository<Aereoporto, Long> {
    public List<Aereoporto> findAll();

    public Aereoporto findByNome(String nome);
}
