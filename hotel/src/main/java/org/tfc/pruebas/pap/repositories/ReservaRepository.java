package org.tfc.pruebas.pap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tfc.pruebas.pap.entities.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
}
