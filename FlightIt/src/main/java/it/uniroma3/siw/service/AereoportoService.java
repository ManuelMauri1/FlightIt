package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Aereoporto;
import it.uniroma3.siw.model.Volo;
import it.uniroma3.siw.repository.AereoportoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AereoportoService {
    @Autowired
    private AereoportoRepository aereoportoRepository;

    public Aereoporto getAereoportoByNome(String nomeAereoporto){
        return aereoportoRepository.findByNome(nomeAereoporto);
    }

    @Transactional
    public List<Aereoporto> getAereoporti(){
        return aereoportoRepository.findAll();
    }

    @Transactional
    public void aggiungiVoloInEntrata(Volo volo, String aereoportoA) {
        Aereoporto aereoporto = getAereoportoByNome(aereoportoA);
        aereoporto.getVoliInEntrata().add(volo);
    }

    @Transactional
    public void aggiungiVoloInUscita(Volo volo, String aereoportoP) {
        getAereoportoByNome(aereoportoP).getVoliInUscita().add(volo);
    }
}
