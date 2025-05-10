package org.agaray.pruebas.pap.repositories;

import org.agaray.pruebas.pap.entities.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    List<Administrador> findByDepartamento(String departamento);
}
