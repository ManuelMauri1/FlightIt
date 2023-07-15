package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.UtenteOAuth2User;
import it.uniroma3.siw.model.Volo;
import it.uniroma3.siw.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository userRepository;
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private VoloService voloService;

    @Transactional
    public void setNome(Utente utente, String nome) {
        utente.setNome(nome);
    }

    @Transactional
    public Utente saveUser(Utente user) {
        return this.userRepository.save(user);
    }

    @Transactional
    public void setUtente(Utente utente, String dataN) {
        utente.setDataNascita(LocalDate.parse(dataN));
    }

    public void addVoloPreferiti(Volo volo, String[] usernames) {
        Utente utente = null;
        if (usernames[0] != null)
            utente = credentialsService.getUtenteByUsername(usernames[0]);
        else
            utente = credentialsService.getUtenteByUsername(usernames[1]);

        utente.getPreferiti().add(volo);
        volo.getPreferitiUtente().add(utente);
    }

    public void removeVoloPreferiti(Volo volo, String[] usernames) {
        Utente utente = null;
        if (usernames[0] != null)
            utente = credentialsService.getUtenteByUsername(usernames[0]);
        else
            utente = credentialsService.getUtenteByUsername(usernames[1]);

        utente.getPreferiti().remove(volo);
        volo.getPreferitiUtente().remove(utente);
    }

    @Transactional
    public String[] getUsernames(Credentials authUser, UserDetails userDetails) {
        String[] usernames = new String[2];
        if (authUser != null)
            usernames[0] = authUser.getUsername();

        if (userDetails != null)
            usernames[1] = userDetails.getUsername();
        return usernames;
    }

    public List<Volo> getPreferiti(String[] usernames) {
        List<Volo> preferiti = new ArrayList<>();
        Utente utente = null;
        if (usernames[0] != null)
            utente = credentialsService.getUtenteByUsername(usernames[0]);
        else
            utente = credentialsService.getUtenteByUsername(usernames[1]);

        if (!utente.getPreferiti().isEmpty())
            preferiti.addAll(utente.getPreferiti());

        return preferiti;
    }

}
