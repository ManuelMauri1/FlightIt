package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Aereoporto;
import it.uniroma3.siw.repository.AereoportoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AereoportoService {
    @Autowired
    private AereoportoRepository aereoportoRepository;

    @Transactional
    public List<Aereoporto> getAereoporti(){
        return aereoportoRepository.findAll();
    }
}
