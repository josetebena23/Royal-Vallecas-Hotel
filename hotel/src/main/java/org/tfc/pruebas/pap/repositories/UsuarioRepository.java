package org.tfc.pruebas.pap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tfc.pruebas.pap.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}
