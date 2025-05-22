package org.tfc.pruebas.pap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tfc.pruebas.pap.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
