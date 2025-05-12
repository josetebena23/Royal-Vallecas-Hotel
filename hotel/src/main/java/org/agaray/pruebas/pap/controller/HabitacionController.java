package org.agaray.pruebas.pap.controller;

import org.agaray.pruebas.pap.entities.Habitacion;
import org.agaray.pruebas.pap.entities.Habitacion.Tipo;
import org.agaray.pruebas.pap.exception.DangerException;
import org.agaray.pruebas.pap.helper.PRG;
import org.agaray.pruebas.pap.services.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/habitacion")
public class HabitacionController {

    @Autowired
    private HabitacionService habitacionService;

    @GetMapping("r")
    public String r(ModelMap m) {
        m.put("habitaciones", habitacionService.listarHabitaciones());
        m.put("view", "habitacion/r");
        return "_t/frame";
    }

    @GetMapping("c")
    public String c(ModelMap m) {
        m.put("tipos", Habitacion.Tipo.values());
        m.put("view", "habitacion/c");
        return "_t/frame";
    }

    @PostMapping("c")
    public String cPost(
            @RequestParam("numero") Integer numero,
            @RequestParam("tipo") Tipo tipo,
            @RequestParam("precio") Double precio,
            @RequestParam("capacidad") Integer capacidad,
            @RequestParam(name = "descripcion", required = false) String descripcion) throws DangerException {
        try {
            Habitacion h = new Habitacion();
            h.setNumero(numero);
            h.setTipo(tipo);
            h.setPrecio(precio);
            h.setCapacidad(capacidad);
            h.setDescripcion(descripcion);
            habitacionService.guardarHabitacion(h);
        } catch (Exception e) {
            PRG.error("Error al crear la habitación", "/habitacion/c");
        }
        return "redirect:/habitacion/r";
    }

    @GetMapping("u")
    public String u(@RequestParam("id") Long id, ModelMap m) {
        Habitacion h = habitacionService.buscarPorId(id).orElse(null);
        m.put("habitacion", h);
        m.put("tipos", Habitacion.Tipo.values());
        m.put("view", "habitacion/u");
        return "_t/frame";
    }

    @PostMapping("u")
    public String uPost(
            @RequestParam("id") Long id,
            @RequestParam("numero") Integer numero,
            @RequestParam("tipo") Tipo tipo,
            @RequestParam("precio") Double precio,
            @RequestParam("capacidad") Integer capacidad,
            @RequestParam(name = "descripcion", required = false) String descripcion) throws DangerException {
        try {
            Habitacion h = habitacionService.buscarPorId(id).orElse(null);
            if (h != null) {
                h.setNumero(numero);
                h.setTipo(tipo);
                h.setPrecio(precio);
                h.setCapacidad(capacidad);
                h.setDescripcion(descripcion);
                habitacionService.guardarHabitacion(h);
            }
        } catch (Exception e) {
            PRG.error("Error al actualizar habitación", "/habitacion/r");
        }
        return "redirect:/habitacion/r";
    }

    @PostMapping("d")
    public String d(@RequestParam("id") Long id) throws DangerException {
        try {
            habitacionService.eliminarHabitacion(id);
        } catch (Exception e) {
            PRG.error("No se pudo eliminar la habitación", "/habitacion/r");
        }
        return "redirect:/habitacion/r";
    }
}
