package it.uniroma3.siw.service;

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

    @Transactional
    public UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional
    public void setNome(Utente utente, String nome){
        utente.setNome(nome);
    }

    @Transactional
    public Utente saveUser(Utente user){
        return this.userRepository.save(user);
    }
    @Transactional
    public void setUtente(Utente utente, String dataN){
        utente.setDataNascita(LocalDate.parse(dataN));
    }

    @Transactional
    public void addVoloPreferiti(Volo volo, String[] usernames) {
        System.out.println("ADD VOLO PREFERITI");
        Utente utente = null;
        try{
            utente = credentialsService.getUtenteByUsername(usernames[0]);
        }
        catch (Exception e){
            utente = credentialsService.getUtenteByUsername(usernames[1]);
        }
        /*if(utente.getPreferiti().isEmpty())
            utente.getPreferiti().add(volo);
        utente.getPreferiti().add(volo);
        */
        System.out.println("ADD VOLO PREFERITI: " + utente);
    }

    @Transactional
    public String[] getUsernames(UtenteOAuth2User authUser, UserDetails userDetails){
        String[] usernames = new String[2];
        if(authUser != null)
            usernames[0] = authUser.getLoginName();
        if(userDetails != null)
            usernames[1] = userDetails.getUsername();
        return usernames;
    }

    public List<Volo> getPreferiti() {
        return new ArrayList<Volo>();
    }
}
