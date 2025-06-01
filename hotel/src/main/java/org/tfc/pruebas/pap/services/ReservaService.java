package org.tfc.pruebas.pap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tfc.pruebas.pap.entities.Reserva;
import org.tfc.pruebas.pap.entities.Usuario;
import org.tfc.pruebas.pap.repositories.ReservaRepository;

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

    public List<Reserva> obtenerReservasPorUsuario(Usuario usuario) {
        return reservaRepository.findByUsuario(usuario);
    }

    public void cancelarReserva(Long id) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva != null) {
            reserva.setEstado(Reserva.Estado.CANCELADA);
            reservaRepository.save(reserva);
        }
    }

}
