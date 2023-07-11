package it.uniroma3.siw.service;

import it.uniroma3.siw.model.UtenteOAuth2User;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UtenteOAuth2UserService extends DefaultOAuth2UserService {
   @Override
   public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
       OAuth2User user = super.loadUser(userRequest);
       UtenteOAuth2User auth2User = new UtenteOAuth2User(user);
       System.out.println("UTENTE CARICATO: " + user + "\n" + auth2User);
       return auth2User;
   }
}
