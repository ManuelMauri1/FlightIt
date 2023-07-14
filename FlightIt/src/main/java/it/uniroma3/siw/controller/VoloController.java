package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.model.UtenteOAuth2User;
import it.uniroma3.siw.model.Volo;
import it.uniroma3.siw.repository.VoloRepository;
import it.uniroma3.siw.service.AereoportoService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.UtenteService;
import it.uniroma3.siw.service.VoloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Controller
public class VoloController {
    @Autowired
    private VoloService voloService;
    @Autowired
    private AereoportoService aereoportoService;
    @Autowired
    private UtenteService utenteService;

    /*NON LOGGATI*/
    @GetMapping("/voli")
    public String voli(Model model) {
        model.addAttribute("voli", voloService.getVoli());
        return "voli.html";
    }

    /*ADMIN*/

    @GetMapping("/admin/formAggiungiVolo")
    public String formNuovoVolo(Model model) {
        model.addAttribute("volo", voloService.nuovoVolo());
        model.addAttribute("aereoporti", aereoportoService.getAereoporti());
        return "admin/formAggiungiVolo.html";
    }

    @PostMapping("/admin/nuovoVolo")
    public String nuovoVolo(@ModelAttribute("volo") Volo volo, @RequestParam("aereoportoP") String aereoportoP,
                            @RequestParam("aereoportoA") String aereoportoA, @RequestParam("dataP") LocalDate dataP,
                            @RequestParam("oraP") String oraP, @RequestParam("oraA") String oraA, Model model) {
        voloService.salvaNuovoVolo(volo, aereoportoP, aereoportoA, dataP, oraP, oraA);
        aereoportoService.aggiungiVolo(volo, aereoportoA, aereoportoP);
        return volo(volo.getId(), model);
    }

    @GetMapping("/admin/modificaVolo")
    public String modificaVolo(Model model) {
        model.addAttribute("voli", voloService.getVoli());
        return "admin/modificaVolo.html";
    }

    @PostMapping("/admin/cancellaVoli")
    public String cancellaVoli(@RequestParam("elimina") List<Long> voliId, Model model) {
        aereoportoService.cancellaVoli(voloService.getVoliDaId(voliId));
        voloService.cancellaVoli(voliId);
        return modificaVolo(model);
    }

    /*LOGGATI*/
    @GetMapping("/autenticato/volo/{id}")
    public String volo(@PathVariable("id") Long id, Model model) {
        model.addAttribute("volo", voloService.getVolo(id));
        return "/autenticato/volo";
    }

    @GetMapping("/autenticato/voli")
    public String voliAutenticato(Model model){
        System.out.println("VOLI AUTENTICATO");
        model.addAttribute("voli", voloService.getVoli());
        return "autenticato/voliAutenticato";
    }

    @GetMapping("/autenticato/voli/addPreferiti/{idVolo}")
    public String addPreferiti(@PathVariable("idVolo") Long idVolo, Model model) {
        Volo volo = voloService.getVolo(idVolo);
        utenteService.addVoloPreferiti(volo);
        return voliAutenticato(model);
    }
}
