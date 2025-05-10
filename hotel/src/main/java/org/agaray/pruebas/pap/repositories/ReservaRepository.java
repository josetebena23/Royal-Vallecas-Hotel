package org.agaray.pruebas.pap.repositories;

import org.agaray.pruebas.pap.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
