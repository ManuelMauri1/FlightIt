package it.uniroma3.siw.OAuth;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Controller
public class LogController {

    @GetMapping("/")
    public String index(Model model){
        System.out.println("INDEX");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model){
        System.out.println("LOGIN");
        return "formLogin";
    }

    @GetMapping("/indexAutenticato")
    public String autenticato(Model model){
        System.out.println("INDEX AUTENTICATO");
        return "indexAutenticato";
    }

    @GetMapping("/success")
    public String defaultAfterLogin(Model model){
        System.out.println("DEFAULT AFTER LOGIN");
        if(SecurityContextHolder.getContext().getAuthentication() instanceof OAuth2AuthenticationToken)
            return autenticato(model);
        else
            return index(model);
    }
}