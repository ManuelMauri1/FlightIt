package it.uniroma3.siw.service;

import it.uniroma3.siw.repository.VoloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoloService {
    @Autowired
    private VoloRepository voloRepository;
}
