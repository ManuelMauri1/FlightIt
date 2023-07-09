package it.uniroma3.siw.model;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class UtenteOAuth2User implements OAuth2User{
    private OAuth2User auth2User;

    @Override
    public Map<String, Object> getAttributes() {
        return auth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return auth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return auth2User.getAttribute("name");
    }
}
