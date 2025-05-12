package org.agaray.pruebas.pap.controller;

import java.time.LocalDate;

import org.agaray.pruebas.pap.entities.Reserva;
import org.agaray.pruebas.pap.services.ReservaService;
import org.agaray.pruebas.pap.helper.PRG;
import org.agaray.pruebas.pap.repositories.HabitacionRepository;
import org.agaray.pruebas.pap.repositories.UsuarioRepository;
import org.agaray.pruebas.pap.exception.DangerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HabitacionRepository habitacionRepository;

    @GetMapping("r")
    public String r(ModelMap m) {
        m.put("reservas", reservaService.listarReservas());
        m.put("view", "reserva/r");
        return "_t/frame";
    }

    @GetMapping("c")
    public String c(ModelMap m) {
        m.put("usuarios", usuarioRepository.findAll()); // 🔹 Añadir esta línea
        m.put("habitaciones", habitacionRepository.findAll()); // 🔹 Añadir esta línea
        m.put("view", "reserva/c");
        return "_t/frame";
    }

    @PostMapping("c")
    public String cPost(
            @RequestParam("usuario") Long usuarioId,
            @RequestParam("habitacion") Long habitacionId,
            @RequestParam("fechaEntrada") String fechaEntrada,
            @RequestParam("fechaSalida") String fechaSalida) throws DangerException {
        try {
            Reserva r = new Reserva();
            r.setUsuario(usuarioRepository.findById(usuarioId).orElseThrow());
            r.setHabitacion(habitacionRepository.findById(habitacionId).orElseThrow());
            r.setFechaEntrada(LocalDate.parse(fechaEntrada));
            r.setFechaSalida(LocalDate.parse(fechaSalida));
            reservaService.guardarReserva(r);
        } catch (Exception e) {
            PRG.error("Error al crear la reserva", "/reserva/c");
        }
        return "redirect:/reserva/r";
    }

    @GetMapping("u")
    public String u(@RequestParam("id") Long id, ModelMap m) {
        Reserva r = reservaService.buscarPorId(id).orElse(null);
        m.put("reserva", r);
        m.put("usuarios", usuarioRepository.findAll()); // 🔹 Añadir esta línea
        m.put("habitaciones", habitacionRepository.findAll()); // 🔹 Añadir esta línea
        m.put("view", "reserva/u");
        return "_t/frame";
    }

    @PostMapping("u")
    public String uPost(
            @RequestParam("id") Long id,
            @RequestParam("usuario") Long usuarioId,
            @RequestParam("habitacion") Long habitacionId,
            @RequestParam("fechaEntrada") String fechaEntrada,
            @RequestParam("fechaSalida") String fechaSalida) throws DangerException {
        try {
            Reserva r = reservaService.buscarPorId(id).orElse(null);
            if (r != null) {
                r.setUsuario(usuarioRepository.findById(usuarioId).orElseThrow());
                r.setHabitacion(habitacionRepository.findById(habitacionId).orElseThrow());
                r.setFechaEntrada(LocalDate.parse(fechaEntrada));
                r.setFechaSalida(LocalDate.parse(fechaSalida));
                reservaService.guardarReserva(r);
            }
        } catch (Exception e) {
            PRG.error("Error al actualizar la reserva", "/reserva/r");
        }
        return "redirect:/reserva/r";
    }

    @PostMapping("d")
    public String d(@RequestParam("id") Long id) throws DangerException {
        try {
            reservaService.eliminarReserva(id);
        } catch (Exception e) {
            PRG.error("No se pudo eliminar la reserva", "/reserva/r");
        }
        return "redirect:/reserva/r";
    }
}
