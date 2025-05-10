package org.agaray.pruebas.pap.services;

import org.agaray.pruebas.pap.entities.Reserva;
import org.agaray.pruebas.pap.repositories.ReservaRepository;
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
     * Valida que no haya conflictos de fechas para la misma habitación.
     */
    public Reserva guardarReserva(Reserva reserva) {
        List<Reserva> reservasExistentes = reservaRepository.findAll();

        for (Reserva existente : reservasExistentes) {
            if (existente.getHabitacion().getId().equals(reserva.getHabitacion().getId())) {
                boolean fechasSolapadas = reserva.getFechaEntrada().isBefore(existente.getFechaSalida())
                        && reserva.getFechaSalida().isAfter(existente.getFechaEntrada());

                if (fechasSolapadas) {
                    throw new RuntimeException(
                            "La habitación ya está reservada en esas fechas. Elija otra fecha o habitación.");
                }
            }
        }

        return reservaRepository.save(reserva);
    }

    /**
     * Busca una reserva por su ID.
     */
    public Optional<Reserva> buscarPorId(Long id) {
        return reservaRepository.findById(id);
    }

    /**
     * Lista todas las reservas.
     */
    public List<Reserva> listarReservas() {
        return reservaRepository.findAll();
    }

    /**
     * Elimina una reserva por su ID.
     */
    public void eliminarReserva(Long id) {
        reservaRepository.deleteById(id);
    }
}
