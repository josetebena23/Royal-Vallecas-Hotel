package main.java.com.hotel.hotel.repositorios;

import com.hotel.hotel.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Este método permite buscar un usuario por su email.
    // Se devuelve un Optional<Usuario> en lugar de Usuario directamente
    // para evitar errores en caso de que no se encuentre el usuario (por ejemplo,
    // NullPointerException).
    // El uso de Optional obliga a manejar de forma segura si el usuario existe o
    // no.
    // Ejemplo de uso:
    // usuarioRepository.findByEmail(email).ifPresent(usuario -> { /* lógica aquí */
    // });
    Optional<Usuario> findByEmail(String email);
}
