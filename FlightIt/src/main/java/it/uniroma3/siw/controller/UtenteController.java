package it.uniroma3.siw.controller;


import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Volo;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UtenteService;
import it.uniroma3.siw.service.VoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2Token;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Controller
public class UtenteController {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private VoloController voloController;
    @Autowired
    private CredentialsService credentialsService;

    @GetMapping("/autenticato/areaUtente")
    public String areaUtente(Model model) {
        Credentials credentials = null;
        String[] usernames = voloController.getUsernames(model);
        if(usernames[0] != null)
            credentials = credentialsService.getCredentialsByUsername(usernames[0]);
        else
            credentials = credentialsService.getCredentialsByUsername(usernames[1]);

        model.addAttribute("credenziali", credentials);
        model.addAttribute("utente", credentials.getUtente());
        model.addAttribute("preferiti", credentials.getUtente().getPreferiti());
        return "autenticato/areaUtente";
    }
}