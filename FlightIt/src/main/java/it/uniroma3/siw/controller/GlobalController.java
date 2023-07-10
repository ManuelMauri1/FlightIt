package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.UtenteOAuth2User;
import it.uniroma3.siw.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {
    @Autowired
    private CredentialsService credentialsService;

    @ModelAttribute("credentials")
    private Object getAuthenticatedUser() {
        Object user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            user = authentication.getPrincipal();
        }
        return user;
    }

    @ModelAttribute("credentials")
    public UtenteOAuth2User getUser() {
        UtenteOAuth2User auth2User = null;
        try {
            auth2User = (UtenteOAuth2User) getAuthenticatedUser();
        } catch (ClassCastException e) {
            return auth2User;
        }
        System.out.println("OAUTH CREDENTIALS: " + (UtenteOAuth2User) getAuthenticatedUser());
        return auth2User;
    }

    @ModelAttribute("userDetail")
    public UserDetails getUtente() {
        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) getAuthenticatedUser();
        } catch (ClassCastException e) {
            return userDetails;
        }
        System.out.println("LOCAL CREDENTIALS: " + (UserDetails) getAuthenticatedUser());
        return userDetails;
    }
}