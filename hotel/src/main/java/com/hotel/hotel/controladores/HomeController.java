package com.hotel.hotel.controladores;

import com.hotel.hotel.entidades.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        // Obtener el usuario autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();

        // Pasarlo al modelo para mostrar el nombre en el home
        model.addAttribute("nombreUsuario", usuario.getNombre());

        return "views/home/home"; // ruta del HTML, como usted me indicó
    }
}
