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
    public Credentials getCredentialsByUsername(String username) {
        Optional<Credentials> result = this.credentialsRepository.findByUsername(username);
        return result.orElse(null);
    }

    public Utente getUtenteByUsername(String username){
        Credentials credentials = getCredentialsByUsername(username);
        return credentials.getUtente();
    }

    @Transactional
    public Utente getUtenteByUsername(String username){
        Credentials credentials = getCredentialsByUsername(username);
        return credentials.getUtente();
    }

    @Transactional
    public Credentials saveCredentials(Credentials credentials) {
        credentials.setRuolo(Credentials.RUOLO_AUTORIZZATO);
        try {
            credentials.setPassword(this.passwordEncoder.encode(credentials.getPassword()));
        }
        catch (Exception e ){}
        return this.credentialsRepository.save(credentials);
    }

    @Transactional
    public void setProvider(Credentials credentials, AuthProvider provider){
        credentials.setProvider(provider);
    }

    @Transactional
    public void setUsername(Credentials credentials, String username){
        credentials.setUsername(username);
    }

    @Transactional
    public void setUser(Credentials credentials, Utente user){
        credentials.setUtente(user);
    }

    @Transactional
    public void saveCredentialsOAuthLogin(String loginName, String displayName, AuthProvider provider){
        Credentials credentials = new Credentials();
        Utente utente = new Utente();
        utenteService.setNome(utente, displayName);
        setUsername(credentials, loginName);
        setProvider(credentials, provider);
        setUser(credentials, utente);
        utenteService.saveUser(utente);
        saveCredentials(credentials);
    }

    @Transactional
    public void saveCredentialsLocalLogin(Utente utente, Credentials credentials, AuthProvider provider){
        Utente utenteSalvato = utenteService.saveUser(utente);
        setProvider(credentials, provider);
        setUser(credentials, utenteSalvato);
        saveCredentials(credentials);
    }
}
