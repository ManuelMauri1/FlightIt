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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoloService {
    @Autowired
    private VoloRepository voloRepository;
    @Autowired
    private AereoportoService aereoportoService;

    @Transactional
    public List<Volo> getVoli() {
        return voloRepository.findAll();
    }

    @Transactional
    public void salvaVolo(Volo volo) {
        voloRepository.save(volo);
    }

    @Transactional
    public Volo nuovoVolo() {
        return new Volo();
    }

    @Transactional
    public void salvaNuovoVolo(Volo volo, String aereoportoP, String aereoportoA,
                               LocalDate dataP, String oraP, String oraA) {
        if (voloEsistente(volo.getCodiceVolo(), dataP)) {
            volo.setCodiceVolo(Volo.generaCodiceVolo());
        }

        volo.setAereoportoArrivo(aereoportoService.getAereoportoByNome(aereoportoA));
        volo.setAereoportoPartenza(aereoportoService.getAereoportoByNome(aereoportoP));
        volo.setDataPartenza(dataP);
        volo.setOraPartenza(parseOra(volo, oraP));
        volo.setOraArrivo(parseOra(volo, oraA));
        salvaVolo(volo);

    }

    private Time parseOra(Volo volo, String ora) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            Date parsedDate = dateFormat.parse(ora);
            return new Time(parsedDate.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean voloEsistente(String codiceVolo, LocalDate dataP) {
        return voloRepository.existsByCodiceVoloAndDataPartenza(codiceVolo, dataP);
    }

    public void cancellaVoli(List<Long> voliId) {
        for (Long voloId : voliId) {
            voloRepository.deleteById(voloId);
        }
    }

    public Volo getVolo(Long id) {
        return voloRepository.findById(id).get();
    }

    public List<Volo> getVoliDaId(List<Long> voliId) {
        List<Volo> voli = new ArrayList<>();
        for (Long voloId : voliId) {
            voli.add(getVolo(voloId));
        }
        return voli;
    }

    public List<Volo> getVoliNonPreferiti(List<Volo> preferiti) {
        List<Long> preferitiId = new ArrayList<>();
        for (Volo preferito: preferiti) {
            preferitiId.add(preferito.getId());
        }
        if(preferitiId.isEmpty())
            return getVoli();
        else
            return voloRepository.findAllByIdNotIn(preferitiId);
    }

    public Float calcoloTempoVolo(Volo volo) {
    	Float tempoVolo = (float) 0;
    	Float oraPartenza = (float) volo.getOraPartenza().getTime();
    	Float oraArrivo = (float) volo.getOraArrivo().getTime();
    	tempoVolo = oraArrivo - oraPartenza;
    	return tempoVolo;
    }


    public Float calcoloTempoDallaPartenzaPercentuale(Volo volo) {
    	Float tempoVolo = calcoloTempoVolo(volo);
    	Float oraPartenza = (float) volo.getOraPartenza().getTime();
    	Float oraAttuale = (float) new Date().getTime();
    	Float tempoAttuale = oraAttuale - oraPartenza;
    	return (tempoAttuale/tempoVolo)*100;
    }

    public Boolean checkPartenzaVolo(Volo volo) {
    	Float oraPartenza = (float) volo.getOraPartenza().getTime();
    	Float oraAttuale = (float) new Date().getTime();
    	Float tempoAttuale = oraAttuale - oraPartenza;
        return tempoAttuale < 0;
    }
}
