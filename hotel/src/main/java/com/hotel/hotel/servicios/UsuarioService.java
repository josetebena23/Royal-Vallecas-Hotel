package com.hotel.hotel.servicios;

import com.hotel.hotel.entidades.Usuario;
import com.hotel.hotel.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder; // 👈 Añadir import

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // 👈 Añadir esto

    /**
     * Guarda un nuevo usuario en la base de datos.
     * Antes de guardar, valida que el correo no esté ya registrado y encripta la
     * contraseña.
     */
    public Usuario guardarUsuario(Usuario usuario) {
        // Comprobar si el email ya existe
        Optional<Usuario> existente = usuarioRepository.findByEmail(usuario.getEmail());
        if (existente.isPresent()) {
            throw new RuntimeException("El email ya está registrado. Por favor, use otro.");
        }

        // Encriptar la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Guardar el usuario
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
