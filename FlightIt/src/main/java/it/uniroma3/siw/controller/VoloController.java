package it.uniroma3.siw.controller;

import it.uniroma3.siw.repository.VoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class VoloController {
    @Autowired
    VoloRepository voloRepository;
}
