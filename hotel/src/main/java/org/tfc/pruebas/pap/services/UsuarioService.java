package org.tfc.pruebas.pap.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tfc.pruebas.pap.entities.Usuario;
import org.tfc.pruebas.pap.repositories.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public Usuario guardarUsuario(Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findByEmail(usuario.getEmail());

        // Si existe otro usuario con ese email Y no es el mismo que se está
        // actualizando
        if (existente.isPresent() && !existente.get().getId().equals(usuario.getId())) {
            throw new RuntimeException("El email ya está registrado. Por favor, use otro.");
        }

        // Solo encriptamos si se ha cambiado la contraseña (ya viene encriptada si no
        // se modifica)
        if (!usuario.getContrasena().startsWith("$2a$")) { // Detecta si ya está encriptada
            usuario.setContrasena(encoder.encode(usuario.getContrasena()));
        }

        return usuarioRepository.save(usuario);
    }

    public boolean comprobarContrasena(String rawPassword, String hash) {
        return encoder.matches(rawPassword, hash);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
