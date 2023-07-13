package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Volo;
import it.uniroma3.siw.repository.VoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VoloService {
    @Autowired
    private VoloRepository voloRepository;

    @Transactional
    public List<Volo> getVoli(){
        return voloRepository.findAll();
    }
}
