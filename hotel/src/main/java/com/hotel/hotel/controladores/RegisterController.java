package com.hotel.hotel.controladores;

import com.hotel.hotel.entidades.Cliente;
import com.hotel.hotel.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Cliente());
        return "views/registro"; // Ruta sin .html
    }

    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Cliente cliente) {
        usuarioService.guardarUsuario(cliente);
        return "redirect:/login"; // primero a login, o también podemos hacer que inicie sesión automático
    }

}
