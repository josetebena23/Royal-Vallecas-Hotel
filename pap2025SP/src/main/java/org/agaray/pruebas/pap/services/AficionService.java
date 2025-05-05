package org.agaray.pruebas.pap.services;

import java.util.List;

import org.agaray.pruebas.pap.entities.Aficion;
import org.agaray.pruebas.pap.entities.Pais;
import org.agaray.pruebas.pap.repositories.AficionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AficionService {
    
    @Autowired
    private AficionRepository aficionRepository;

    public void c(String nombre)  {
        aficionRepository.save( new Aficion(nombre) );
    }

    public List<Aficion> r() {
        return aficionRepository.findAll();
    }

    @SuppressWarnings("unused")
    public void d(Long id) throws Exception {
        Aficion aficionABorrar = aficionRepository.findById(id).get();
        if (aficionABorrar.getAficionados().size() == 0 && aficionABorrar.getHaters().size() == 0) {
            aficionRepository.deleteById(id);
        }
        else {
            throw new Exception("La afición "+aficionABorrar.getNombre()+" tiene aficionados o haters");
        }
    }

    public Aficion rById(Long id) {
        return aficionRepository.findById(id).get();
    }

    public void u(Long id, String nombre) {
       Aficion aficionAModificar =  aficionRepository.findById(id).get();
       aficionAModificar.setNombre(nombre);
       aficionRepository.save(aficionAModificar);
    }

}
