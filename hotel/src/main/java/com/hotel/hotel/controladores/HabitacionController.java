package com.hotel.hotel.controladores;

import com.hotel.hotel.entidades.Habitacion;
import com.hotel.hotel.servicios.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    // Crear una nueva habitación
    @PostMapping
    public Habitacion registrarHabitacion(@RequestBody Habitacion habitacion) {
        return habitacionService.guardarHabitacion(habitacion);
    }

    // Listar todas las habitaciones
    @GetMapping
    public List<Habitacion> listarHabitaciones() {
        return habitacionService.listarHabitaciones();
    }

    // Buscar una habitación por ID
    @GetMapping("/{id}")
    public Optional<Habitacion> buscarHabitacionPorId(@PathVariable Integer id) {
        return habitacionService.buscarPorId(id);
    }

    // Eliminar una habitación por ID
    @DeleteMapping("/{id}")
    public void eliminarHabitacion(@PathVariable Integer id) {
        habitacionService.eliminarHabitacion(id);
    }
}
