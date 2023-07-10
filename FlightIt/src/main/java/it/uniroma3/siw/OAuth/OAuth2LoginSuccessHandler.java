package it.uniroma3.siw.OAuth;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.UtenteOAuth2User;
import it.uniroma3.siw.service.CredentialsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private CredentialsService credentialsService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UtenteOAuth2User auth2User = (UtenteOAuth2User) authentication.getPrincipal();
        String loginName = auth2User.getLoginName();
        String displayName = auth2User.getName();
        System.out.println("ON AUTHENTICATION SUCCESS: UTENTE " + auth2User.getLoginName() + ' ' + auth2User.getName());
        Credentials credentials = credentialsService.getCredentialsByUsername(loginName);
        if(credentials == null) {
            System.out.println("ON AUTHENTICATION SUCCESS: NUOVO UTENTE");
            credentialsService.saveCredentialsOAuthLogin(loginName, displayName, AuthProvider.GITHUB);
        }
        else
            System.out.println("ON AUTHENTICATION SUCCESS: UTENTE ESISTENTE");

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
