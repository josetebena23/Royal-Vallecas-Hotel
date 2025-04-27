package main.java.com.hotel.hotel.controladores;

import com.hotel.hotel.entidades.Reserva;
import com.hotel.hotel.servicios.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Crear una nueva reserva
    @PostMapping
    public Reserva registrarReserva(@RequestBody Reserva reserva) {
        return reservaService.guardarReserva(reserva);
    }

    // Listar todas las reservas
    @GetMapping
    public List<Reserva> listarReservas() {
        return reservaService.listarReservas();
    }

    // Buscar una reserva por ID
    @GetMapping("/{id}")
    public Optional<Reserva> buscarReservaPorId(@PathVariable Integer id) {
        return reservaService.buscarPorId(id);
    }

    // Eliminar una reserva por ID
    @DeleteMapping("/{id}")
    public void eliminarReserva(@PathVariable Integer id) {
        reservaService.eliminarReserva(id);
    }
}
