package com.hotel.hotel.servicios;

import com.hotel.hotel.entidades.Reserva;
import com.hotel.hotel.repositorios.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    /**
     * Guarda una nueva reserva en la base de datos.
     * Antes de guardar, valida que no haya conflictos de fechas para la misma
     * habitación.
     */
    public Reserva guardarReserva(Reserva reserva) {
        // Validar que no existan reservas en conflicto para la misma habitación
        List<Reserva> reservasExistentes = reservaRepository.findAll();

        for (Reserva existente : reservasExistentes) {
            // Verificamos que sea la misma habitación
            if (existente.getHabitacion().getId().equals(reserva.getHabitacion().getId())) {
                // Comprobar si las fechas se solapan
                boolean fechasSolapadas = reserva.getFechaEntrada().isBefore(existente.getFechaSalida())
                        && reserva.getFechaSalida().isAfter(existente.getFechaEntrada());

                if (fechasSolapadas) {
                    throw new RuntimeException(
                            "La habitación ya está reservada en esas fechas. Elija otra fecha o habitación.");
                }
            }
        }

        // Si no hay conflictos, guardar la reserva
        return reservaRepository.save(reserva);
    }

    /**
     * Busca una reserva por su ID.
     */
    public Optional<Reserva> buscarPorId(Integer id) {
        return reservaRepository.findById(id);
    }

    /**
     * Devuelve una lista de todas las reservas.
     */
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    /**
     * Elimina una reserva por su ID.
     */
    public void eliminarReserva(Integer id) {
        reservaRepository.deleteById(id);
    }
}
