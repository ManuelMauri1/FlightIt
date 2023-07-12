package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Credentials;
//import it.uniroma3.siw.model.UtenteOAuth2User;
import it.uniroma3.siw.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {
    @Autowired
    private CredentialsService credentialsService;

    private Object getAuthenticatedUser() {
        Object user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = authentication.getPrincipal();
        }
        return user;
    }

    /*@ModelAttribute("authUser")
    public Credentials getCredentials() {
        try {
            UtenteOAuth2User principal = (UtenteOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Credentials credentials = credentialsService.getCredentialsByUsername(principal.getLoginName());
            System.out.println("OAUTH CREDENZIALS: " + credentials);
            System.out.println("OAUTH PRINCIPAL: " + principal.getAuthorities());
            return credentials;
        }catch (ClassCastException e){
            System.out.println("OAUTH ERRORE: " + e);
        }
        return null;
    }


    @ModelAttribute("userDetail")
    public UserDetails getUtente() {
        try {
            UserDetails userDetails = (UserDetails) getAuthenticatedUser();
            System.out.println("LOCAL PRINCIPAL: " + userDetails);
            return userDetails;
        } catch (ClassCastException e) {
            System.out.println("LOCAL ERRORE: " + e);
        }
        return null;
    }*/
}