package it.uniroma3.siw.controller;

import it.uniroma3.siw.OAuth.AuthProvider;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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


    @ModelAttribute("credentials")
    public void getCredentials(Model model, Credentials credentials) {
        model.addAttribute("credentials", credentials);
    }

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "formLogin";
    }

    @GetMapping("autenticato/indexAutenticato")
    public String autenticato(Model model) {
        return "autenticato/indexAutenticato";
    }

    @GetMapping("admin/indexAdmin")
    public String admin(Model model) {
        UtenteOAuth2User principal = (UtenteOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Credentials credentials = credentialsService.getCredentialsByUsername(principal.getLoginName());
        System.out.println("CREDENZIALI UTENTE: " + credentials);
        System.out.println("PRINCIPAL: " + principal.getAuthorities());

        getCredentials(model, credentials);
        return "admin/indexAdmin";
    }


    @GetMapping("/success")
    public String defaultAfterLogin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
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
            } else {
                userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                loginName = userDetails.getUsername();
            }
            credentials = credentialsService.getCredentialsByUsername(loginName);

            //Rimando al relativo index
            if (credentials.getRuolo().equals(Credentials.RUOLO_ADMIN))
                return admin(model);
            else
                return autenticato(model);
        }
    }

    //Quando implemento /register mettere a provider LOCAL, se non ricordi vedi OAuth2LoginSuccessHandler/ CredentialsService saveCredentialsOAuthLogin
    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new Utente());
        model.addAttribute("credentials", new Credentials());
        return "formRegistrazione";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") Utente user,
                               BindingResult userBindingResult,
                               @Valid @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult,
                               @RequestParam("dataN") String dataN,
                               Model model) {
        userService.setUtente(user, dataN);
        userValidator.validate(user, userBindingResult);
        credentialsValidator.validate(credentials, credentialsBindingResult);
        // se user e credential hanno entrambi contenuti validi, memorizza User e the Credentials nel DB
        boolean userErrors = userBindingResult.hasErrors();
        boolean credentialsErrors = credentialsBindingResult.hasErrors();
        if (!userErrors && !credentialsErrors) {
            credentialsService.saveCredentialsLocalLogin(user, credentials, AuthProvider.LOCAL);
            model.addAttribute("user", user);
            return "registrationSuccessful";
        } else
            return "formRegistrazione";
    }

}