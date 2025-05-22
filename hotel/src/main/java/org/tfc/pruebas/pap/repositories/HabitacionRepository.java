package org.tfc.pruebas.pap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tfc.pruebas.pap.entities.Habitacion;

public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
}
