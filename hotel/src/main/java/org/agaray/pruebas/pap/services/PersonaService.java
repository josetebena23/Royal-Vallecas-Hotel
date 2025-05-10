package org.agaray.pruebas.pap.services;

import java.util.ArrayList;
import java.util.List;

import org.agaray.pruebas.pap.entities.Aficion;
import org.agaray.pruebas.pap.entities.Pais;
import org.agaray.pruebas.pap.entities.Persona;
import org.agaray.pruebas.pap.repositories.AficionRepository;
import org.agaray.pruebas.pap.repositories.PaisRepository;
import org.agaray.pruebas.pap.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PaisRepository paisRepository;

    @Autowired
    private AficionRepository aficionRepository;

    public void c(String nombre, String apellido, Long idPN, Long idPR, List<Long> idsAficionGusta,
            List<Long> idsAficionOdia) {
        Persona p = new Persona(nombre, apellido);

        Pais pn = paisRepository.findById(idPN).get();
        p.setNace(pn);
        pn.getNacidos().add(p);

        Pais pr = paisRepository.findById(idPR).get();
        p.setVive(pr);
        pr.getResidentes().add(p);

        // Vincular con los gustos
        for (Long idAficionGusta : idsAficionGusta) {
            p.getGustos().add(aficionRepository.findById(idAficionGusta).get());
        }

        // Vincular con los odios
        for (Long idAficionOdia : idsAficionOdia) {
            p.getOdios().add(aficionRepository.findById(idAficionOdia).get());
        }

        personaRepository.save(p);
    }

    public List<Persona> r() {
        return personaRepository.findAll();
    }

    @SuppressWarnings("unused")
    public void d(Long id) throws Exception {
        //Persona personaABorrar = personaRepository.findById(id).get();
        personaRepository.deleteById(id);

    }

    public Persona rById(Long id) {
        return personaRepository.findById(id).get();
    }

    public void u(Long id, String nombre, String apellido, Long idPN, Long idPR,
            List<Long> idsAficionGusta, List<Long> idsAficionOdia) {

                Persona p = personaRepository.findById(id).get();

                p.setNombre(nombre);
                p.setApellido(apellido);

                Pais pn = paisRepository.findById(idPN).get();
                p.setNace(pn);
                pn.getNacidos().add(p);
        
                Pais pr = paisRepository.findById(idPR).get();
                p.setVive(pr);
                pr.getResidentes().add(p);
        
                ArrayList<Aficion> gustosNuevos = new ArrayList<>();
                // Vincular con los gustos
                for (Long idAficionGusta : idsAficionGusta) {
                    gustosNuevos.add(aficionRepository.findById(idAficionGusta).get());
                }
                p.setGustos(gustosNuevos);

                ArrayList<Aficion> odiosNuevos = new ArrayList<>();
                // Vincular con los odios
                for (Long idAficionOdia : idsAficionOdia) {
                    odiosNuevos.add(aficionRepository.findById(idAficionOdia).get());
                }
                p.setOdios(odiosNuevos);
        
                personaRepository.save(p);
    }

}
