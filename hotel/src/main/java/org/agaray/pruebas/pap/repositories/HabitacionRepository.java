package org.agaray.pruebas.pap.repositories;

import org.agaray.pruebas.pap.entities.Habitacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
}
