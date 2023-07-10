package it.uniroma3.siw.service;

import it.uniroma3.siw.OAuth.AuthProvider;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CredentialsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CredentialsService {
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Autowired
    private CredentialsRepository credentialsRepository;
    @Autowired
    private UtenteService utenteService;

    @Transactional
    public Credentials getCredentials(String username) {
        Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
        return result.orElse(null);
    }

    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRuolo(Credentials.RUOLO_AUTORIZZATO);
        credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }

    @Transactional
    public void setUser(Credentials credentials, Utente user){
        credentials.setUtente(user);
    }

    @Transactional
    public void saveCredentialsOAuthLogin(String loginName, String displayName, AuthProvider authenticationProvider){
        System.out.println("SAVE CREDENTIALS OAUTHLOGIN");
        Credentials credentials = new Credentials();
        Utente utente = new Utente();
        System.out.println("SAVE CREDENTIALS OAUTHLOGIN: " + credentials + ' ' + utente);
        utente.setNome(displayName);
        credentials.setUsername(loginName);
        credentials.setProvider(authenticationProvider);
        setUser(credentials, utente);
        utenteService.saveUser(utente);
        System.out.println("SAVE CREDENTIALS OAUTHLOGIN: " + credentials + ' ' + utente);
        saveCredentials(credentials);
        System.out.println("SAVE CREDENTIALS OAUTHLOGIN: " + credentials + ' ' + utente);
    }

    @Transactional
    public void saveCredentialsLocalLogin(){}
}
