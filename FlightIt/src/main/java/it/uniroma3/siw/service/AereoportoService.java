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

    public Aereoporto getAereoportoByNome(String nomeAereoporto) {
        return aereoportoRepository.findByNome(nomeAereoporto);
    }

    public List<Aereoporto> getAereoporti() {
        return aereoportoRepository.findAll();
    }

    @Transactional
    public void aggiungiVolo(Volo volo, String aereoportoA, String aereoportoP) {
        aggiungiVoloInEntrata(volo, aereoportoA);
        aggiungiVoloInUscita(volo, aereoportoP);
    }

    public void aggiungiVoloInEntrata(Volo volo, String aereoportoA) {
        getAereoportoByNome(aereoportoA).getVoliInEntrata().add(volo);
    }


    public void aggiungiVoloInUscita(Volo volo, String aereoportoP) {
        getAereoportoByNome(aereoportoP).getVoliInUscita().add(volo);
    }

    @Transactional
    public void cancellaVoli(List<Volo> voliDaId) {
        for (Volo volo : voliDaId) {
            eliminaVoloInUscita(volo, volo.getAereoportoPartenza());
            eliminaVoloInEntrata(volo, volo.getAereoportoArrivo());
        }
    }


    public void eliminaVoloInEntrata(Volo volo, Aereoporto aereoportoA) {
        aereoportoA.getVoliInEntrata().remove(volo);
    }

    public void eliminaVoloInUscita(Volo volo, Aereoporto aereoportoP) {
        aereoportoP.getVoliInUscita().remove(volo);
    }

}
