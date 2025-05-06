package com.hotel.hotel.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/")
    public String redireccionarAlLogin() {
        return "redirect:/login"; // Solo redireccionar
    }

    @GetMapping("/login")
    public String mostrarLogin() {
        return "views/login"; // Mostrar login
    }
}
