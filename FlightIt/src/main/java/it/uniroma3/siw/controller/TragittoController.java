package it.uniroma3.siw.controller;

import it.uniroma3.siw.repository.TragittoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TragittoController {
    @Autowired
    TragittoRepository tragittoRepository;
}
