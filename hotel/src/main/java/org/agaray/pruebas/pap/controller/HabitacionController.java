package org.agaray.pruebas.pap.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.agaray.pruebas.pap.entities.Habitacion;
import org.agaray.pruebas.pap.entities.Habitacion.Tipo;
import org.agaray.pruebas.pap.entities.ImagenHabitacion;
import org.agaray.pruebas.pap.exception.DangerException;
import org.agaray.pruebas.pap.helper.PRG;
import org.agaray.pruebas.pap.services.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam(name = "descripcion", required = false) String descripcion,
            @RequestParam("imagenes") MultipartFile[] imagenes // 👈 AQUI va esto
    ) throws DangerException {
        try {
            Habitacion h = Habitacion.builder()
                    .numero(numero)
                    .tipo(tipo)
                    .precio(precio)
                    .capacidad(capacidad)
                    .descripcion(descripcion)
                    .build();

            // 🧠 Creamos la carpeta con el número de la habitación
            String carpetaNombre = "src/main/resources/static/img/" + numero;
            Path carpetaRuta = Paths.get(carpetaNombre);
            if (!Files.exists(carpetaRuta)) {
                Files.createDirectories(carpetaRuta); // crea carpeta si no existe
            }

            // 🖼 Guardar cada imagen en la carpeta
            List<ImagenHabitacion> listaImagenes = new ArrayList<>();
            for (MultipartFile imagen : imagenes) { // 👈 Aquí va el bucle
                if (!imagen.isEmpty()) {
                    String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
                    Path ruta = carpetaRuta.resolve(nombreArchivo);
                    Files.write(ruta, imagen.getBytes());

                    listaImagenes.add(ImagenHabitacion.builder()
                            .url("/img/" + numero + "/" + nombreArchivo) // ruta accesible
                            .habitacion(h)
                            .build());
                }
            }

            h.setImagenes(listaImagenes);
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
            @RequestParam(name = "descripcion", required = false) String descripcion,
            @RequestParam(name = "imagenes", required = false) MultipartFile[] nuevasImagenes) throws DangerException {
        try {
            Habitacion h = habitacionService.buscarPorId(id).orElse(null);
            if (h != null) {
                h.setNumero(numero);
                h.setTipo(tipo);
                h.setPrecio(precio);
                h.setCapacidad(capacidad);
                h.setDescripcion(descripcion);

                // Subir nuevas imágenes si se seleccionaron
                if (nuevasImagenes != null) {
                    String carpetaNombre = "src/main/resources/static/img/" + numero;
                    Path carpetaRuta = Paths.get(carpetaNombre);
                    if (!Files.exists(carpetaRuta)) {
                        Files.createDirectories(carpetaRuta);
                    }

                    for (MultipartFile imagen : nuevasImagenes) {
                        if (!imagen.isEmpty()) {
                            String nombreArchivo = UUID.randomUUID() + "_" + imagen.getOriginalFilename();
                            Path ruta = carpetaRuta.resolve(nombreArchivo);
                            Files.write(ruta, imagen.getBytes());

                            h.getImagenes().add(ImagenHabitacion.builder()
                                    .url("/img/" + numero + "/" + nombreArchivo)
                                    .habitacion(h)
                                    .build());
                        }
                    }
                }

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

    @PostMapping("/imagen/d")
    public String eliminarImagen(
            @RequestParam("imagenId") Long imagenId,
            @RequestParam("habitacionId") Long habitacionId) {
        try {
            habitacionService.eliminarImagenPorId(imagenId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/habitacion/u?id=" + habitacionId;
    }

}
