package org.agaray.pruebas.pap.services;

import java.util.List;

import org.agaray.pruebas.pap.entities.Pais;
import org.agaray.pruebas.pap.repositories.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisService {
    
    @Autowired
    private PaisRepository paisRepository;

    public void c(String nombre)  {
        paisRepository.save( new Pais(nombre) );
    }

    public List<Pais> r() {
        return paisRepository.findAll();
    }

    @SuppressWarnings("unused")
    public void d(Long id) throws Exception {
        Pais paisABorrar = paisRepository.findById(id).get();
        if (paisABorrar.getNacidos().size() <= 0 && paisABorrar.getResidentes().size() <= 0) {
            paisRepository.deleteById(id);
        }
        else {
            throw new Exception("El país "+paisABorrar.getNombre()+" tiene nacidos o residentes");
        }
    }

    public Pais rById(Long id) {
        return paisRepository.findById(id).get();
    }

    public void u(Long id, String nombre) {
       Pais paisAModificar =  paisRepository.findById(id).get();
       paisAModificar.setNombre(nombre);
       paisRepository.save(paisAModificar);
    }
}
