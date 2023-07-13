package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.UtenteOAuth2User;
import it.uniroma3.siw.repository.VoloRepository;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.VoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VoloController {
    @Autowired
    private VoloService voloService;
    @Autowired
    private CredentialsService credentialsService;

    /*NON LOGGATI*/
    @GetMapping("/voli")
    public String voli(Model model){
        model.addAttribute("voli", voloService.getVoli());
        return "voli.html";
    }

    /*ADMIN*/
    @GetMapping("/admin/modificaVolo")
    public String modificaVolo(Model model){
        UtenteOAuth2User principal = (UtenteOAuth2User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("CREDENZIALI UTENTE: " + credentialsService.getCredentialsByUsername(principal.getLoginName()));
        return "admin/modificaVolo.html";
    }

    @GetMapping("/admin/formAggiungiVolo")
    public String formNuovoVolo(Model model){
        model.addAttribute("volo", voloService.nuovoVolo());
        return "admin/formAggiungiVolo.html";
    }

    /*LOGGATI*/
    @GetMapping("/autenticato/volo")
    public String volo(Model model){
        return "/autenticato/volo";
    }

}
