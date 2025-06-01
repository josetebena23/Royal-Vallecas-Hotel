package org.tfc.pruebas.pap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tfc.pruebas.pap.entities.Habitacion;
import org.tfc.pruebas.pap.entities.ImagenHabitacion;
import org.tfc.pruebas.pap.exception.DangerException;
import org.tfc.pruebas.pap.repositories.HabitacionRepository;
import org.tfc.pruebas.pap.repositories.ImagenHabitacionRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository habitacionRepository;
    @Autowired
    private ImagenHabitacionRepository imagenRepo;

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
    public void eliminarHabitacion(Long id) throws DangerException {
        Optional<Habitacion> opt = habitacionRepository.findById(id);
        if (opt.isPresent()) {
            Habitacion h = opt.get();

            // Verificación básica: si tiene reservas, no se elimina
            if (h.getReservas() != null && !h.getReservas().isEmpty()) {
                throw new DangerException("No se puede eliminar la habitación porque tiene reservas asociadas");
            }

            // Ruta de la carpeta de imágenes
            String carpeta = "src/main/resources/static/img/" + h.getNumero();
            Path carpetaRuta = Paths.get(carpeta);

            try {
                // Eliminar todas las imágenes de la carpeta si existe
                if (Files.exists(carpetaRuta)) {
                    Files.walk(carpetaRuta)
                            .sorted((a, b) -> b.compareTo(a)) // eliminar archivos antes que carpetas
                            .forEach(ruta -> {
                                try {
                                    Files.delete(ruta);
                                } catch (Exception e) {
                                    System.err.println("No se pudo borrar: " + ruta);
                                }
                            });
                }

                // Finalmente elimina la habitación
                habitacionRepository.deleteById(id);

            } catch (Exception e) {
                e.printStackTrace();
                throw new DangerException("Error al eliminar habitación y su carpeta de imágenes");
            }
        } else {
            throw new DangerException("Habitación no encontrada");
        }
    }

    public void eliminarImagenPorId(Long id) {
        Optional<ImagenHabitacion> opt = imagenRepo.findById(id);
        if (opt.isPresent()) {
            ImagenHabitacion img = opt.get();
            try {
                Path ruta = Paths.get("src/main/resources/static" + img.getUrl());
                Files.deleteIfExists(ruta);
            } catch (Exception e) {
                e.printStackTrace(); // error al borrar del disco
            }
            imagenRepo.deleteById(id);
        }
    }

    public List<Habitacion> buscarConFiltros(String tipo, String estado) {
        return habitacionRepository.findAll().stream()
                .filter(h -> (tipo == null || tipo.isEmpty() || h.getTipo().name().equalsIgnoreCase(tipo)))
                .filter(h -> (estado == null || estado.isEmpty() || h.getEstado().name().equalsIgnoreCase(estado)))
                .collect(Collectors.toList());
    }

}
