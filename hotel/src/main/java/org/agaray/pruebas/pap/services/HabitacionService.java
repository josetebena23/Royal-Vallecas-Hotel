package org.agaray.pruebas.pap.services;

import org.agaray.pruebas.pap.entities.Habitacion;
import org.agaray.pruebas.pap.repositories.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;

    // Guardar una habitación nueva
    public Habitacion guardarHabitacion(Habitacion habitacion) {
        return habitacionRepository.save(habitacion);
    }

    // Buscar habitación por ID
    public Optional<Habitacion> buscarPorId(Long id) {
        return habitacionRepository.findById(id);
    }

    // Listar todas las habitaciones
    public List<Habitacion> listarHabitaciones() {
        return habitacionRepository.findAll();
    }

    // Eliminar habitación por ID
    public void eliminarHabitacion(Long id) {
        habitacionRepository.deleteById(id);
    }
}
