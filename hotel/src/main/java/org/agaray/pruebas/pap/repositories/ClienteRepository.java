package org.agaray.pruebas.pap.repositories;

import org.agaray.pruebas.pap.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
