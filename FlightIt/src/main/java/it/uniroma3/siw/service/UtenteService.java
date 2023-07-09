package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository userRepository;
    @Transactional
    public UserDetails getUserDetails() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Transactional
    public Utente saveUser(Utente user){
        return this.userRepository.save(user);
    }
}
