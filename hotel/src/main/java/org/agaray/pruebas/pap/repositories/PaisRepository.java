package org.agaray.pruebas.pap.repositories;

import org.agaray.pruebas.pap.entities.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais,Long> {    
}
