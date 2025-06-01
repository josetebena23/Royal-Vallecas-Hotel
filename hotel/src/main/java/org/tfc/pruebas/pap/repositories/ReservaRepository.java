package org.tfc.pruebas.pap.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tfc.pruebas.pap.entities.Reserva;
import org.tfc.pruebas.pap.entities.Usuario;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByUsuario(Usuario usuario); // 🔥 Esto es lo que faltaba
}