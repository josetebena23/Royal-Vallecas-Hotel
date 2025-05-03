package com.hotel.hotel.servicios;

import com.hotel.hotel.entidades.Usuario;
import com.hotel.hotel.repositorios.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Guarda un nuevo usuario en la base de datos.
     * Antes de guardar, valida que el correo no esté ya registrado.
     */
    public Usuario guardarUsuario(Usuario usuario) {
        // Comprobar si el email ya existe
        Optional<Usuario> existente = usuarioRepository.findByEmail(usuario.getEmail());
        if (existente.isPresent()) {
            throw new RuntimeException("El email ya está registrado. Por favor, use otro.");
        }
        // Si el email no existe, guardar el usuario
        return usuarioRepository.save(usuario);
    }

    /**
     * Busca un usuario por su ID.
     */
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Busca un usuario por su email.
     */
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    /**
     * Devuelve una lista de todos los usuarios.
     */
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Elimina un usuario por su ID.
     */
    public void eliminarUsuario(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
