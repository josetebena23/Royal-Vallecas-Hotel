package com.hotel.hotel.repositorios;

import com.hotel.hotel.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
}
