package com.hotel.hotel.servicios;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hotel.hotel.entidades.Usuario;
import com.hotel.hotel.repositorios.UsuarioRepository;

import lombok.RequiredArgsConstructor;

public class UserDeatilsService {
    @Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con email: " + email));
        
        return User.builder()  // Usando el builder de Spring Security
            .username(usuario.getEmail())
            .password(usuario.getPassword())
            .roles("USER")  // Rol temporal. Cambiará según tu lógica.
            .build();
    }
}
}
