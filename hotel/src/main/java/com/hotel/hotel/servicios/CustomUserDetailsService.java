package com.hotel.hotel.servicios;

import com.hotel.hotel.entidades.Usuario;
import com.hotel.hotel.repositorios.UsuarioRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // 1. Buscar el usuario en la BD por email
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));

        // 2. Retornar UserDetails (sin roles por ahora)
        return new User(
            usuario.getEmail(),
            usuario.getPassword(),
            new ArrayList<>() // Lista de autoridades/roles vacía (lo añadirás luego)
        );
    }
}