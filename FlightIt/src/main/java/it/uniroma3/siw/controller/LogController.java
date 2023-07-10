package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.controller.validator.UtenteValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.UtenteOAuth2User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
public class LogController {
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private UtenteService userService;
    @Autowired
    private UtenteValidator userValidator;
    @Autowired
    private CredentialsValidator credentialsValidator;

    @GetMapping("/")
    public String index(Model model) {
        System.out.println("INDEX");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        System.out.println("LOGIN");
        return "formLogin";
    }

    @GetMapping("autenticato/indexAutenticato")
    public String autenticato(Model model) {
        System.out.println("INDEX AUTENTICATO");
        return "autenticato/indexAutenticato";
    }

    @GetMapping("admin/indexAdmin")
    public String admin(Model model) {
        System.out.println("INDEX ADMIN");
        return "admin/indexAdmin";
    }

    @GetMapping("/success")
    public String defaultAfterLogin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("DEFAULT AFTER LOGIN: " + authentication);
        if (authentication instanceof AnonymousAuthenticationToken)
            return login(model);
        else {
            UserDetails userDetails = null;
            Credentials credentials = null;
            UtenteOAuth2User auth2User = null;
            String loginName = null;

            //Come ha loggato?
            if (authentication instanceof OAuth2AuthenticationToken) {
                auth2User = (UtenteOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                loginName = auth2User.getLoginName();
                credentials = credentialsService.getCredentials(loginName);
                System.out.println("DEFAULT AFTER LOGIN: " + loginName + '\n' + "CREDENTIALS: " + credentials);
            }
            else{
                userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                loginName = userDetails.getUsername();
                credentials = credentialsService.getCredentials(loginName);
                System.out.println("DEFAULT AFTER LOGIN: " + loginName + '\n' + "CREDENTIALS: " + credentials);
            }

            //Rimando al relativo index
            if (credentials.getRuolo().equals(Credentials.RUOLO_ADMIN))
                return admin(model);
            else
                return autenticato(model);
        }
    }

    //Quando implemento /register mettere a provider LOCAL, se non ricordi vedi OAuth2LoginSuccessHandler/ CredentialsService saveCredentialsOAuthLogin
    @GetMapping("/register")
    public String registerForm(Model model){
        System.out.println("FORM REGISTER");
        model.addAttribute("user", new Utente());
        model.addAttribute("credentials", new Credentials());
        return "formRegistrazione";
    }

    @PostMapping( "/register" )
    public String registerUser(@Valid @ModelAttribute("user") Utente user,
                               BindingResult userBindingResult,
                               @Valid @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               @RequestParam("dataN")String dataN,
                               Model model) {
        System.out.println("REGISTER");
        Utente utenteDaVerificare = userService.setNewUser(user, dataN);
        userValidator.validate(utenteDaVerificare, userBindingResult);
        credentialsValidator.validate(credentials, credentialsBindingResult);
        // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        boolean userErrors = userBindingResult.hasErrors();
        boolean credentialsErrors = credentialsBindingResult.hasErrors();
        if(!userErrors && !credentialsErrors) {
            userService.saveUser(utenteDaVerificare);
            credentialsService.setUser(credentials, user);
            credentialsService.saveCredentials(credentials);
            model.addAttribute("user", user);
            return "registrationSuccessful";
        }
        else
            return "formRegistrazione";
    }

}