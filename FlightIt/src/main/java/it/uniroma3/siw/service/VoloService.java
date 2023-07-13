package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Volo;
import it.uniroma3.siw.repository.VoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
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
                               LocalDate dataP, String oraP, String oraA) {
        volo.setAereoportoArrivo(aereoportoA);
        volo.setAereoportoPartenza(aereoportoP);
        volo.setDataPartenza(dataP);
        volo.setOraPartenza(ParseOra(volo, oraP));
        volo.setOraArrivo(ParseOra(volo, oraA));
        salvaVolo(volo);
    }

    private Time ParseOra(Volo volo, String ora) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            Date parsedDate = dateFormat.parse(ora);
            return new Time(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
