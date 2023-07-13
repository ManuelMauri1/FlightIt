package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Volo;
import it.uniroma3.siw.repository.VoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Service
public class VoloService {
    @Autowired
    private VoloRepository voloRepository;

    @Transactional
    public List<Volo> getVoli(){
        return voloRepository.findAll();
    }

    @Transactional
    public void salvaVolo(Volo volo){
        voloRepository.save(volo);
    }

    @Transactional
    public Volo nuovoVolo() {
        return new Volo();
    }

    @Transactional
    public void salvaNuovoVolo(Volo volo, String aereoportoP, String aereoportoA,
                               String dataP, String oraP, String oraA) {
        volo.setAereoportoArrivo(aereoportoA);
        volo.setAereoportoPartenza(aereoportoP);
        volo.setDataPartenza(LocalDate.parse(dataP));
        volo.setOraPartenza(Time.valueOf(oraP));
        volo.setOraArrivo(Time.valueOf(oraA));
        salvaVolo(volo);
    }
}
