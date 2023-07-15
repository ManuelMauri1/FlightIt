package it.uniroma3.siw.controller;


import it.uniroma3.siw.model.Volo;
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
}