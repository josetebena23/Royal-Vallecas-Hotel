package org.agaray.pruebas.pap.controller;

import org.agaray.pruebas.pap.entities.Administrador;
import org.agaray.pruebas.pap.entities.Cliente;
import org.agaray.pruebas.pap.entities.Editor;
import org.agaray.pruebas.pap.entities.Usuario;
import org.agaray.pruebas.pap.services.UsuarioService;
import org.agaray.pruebas.pap.helper.PRG;
import org.agaray.pruebas.pap.exception.DangerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("r")
    public String r(ModelMap m) {
        m.put("usuarios", usuarioService.listarUsuarios());
        m.put("view", "usuario/r");
        return "_t/frame";
    }

    @GetMapping("c")
    public String c(ModelMap m) {
        m.put("view", "usuario/c");
        return "_t/frame";
    }

    @PostMapping("c")
    public String cPost(
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("contrasena") String contrasena,
            @RequestParam("tipo") String tipo // CLIENTE, EDITOR, ADMIN
    ) throws DangerException {
        try {
            Usuario u;

            switch (tipo) {
                case "EDITOR":
                    u = new Editor();
                    break;
                case "ADMIN":
                    u = new Administrador();
                    break;
                case "CLIENTE":
                default:
                    u = new Cliente();
                    break;
            }

            u.setNombre(nombre);
            u.setEmail(email);
            u.setContrasena(contrasena);
            u.setRol(Usuario.Rol.valueOf(tipo));

            usuarioService.guardarUsuario(u);

        } catch (Exception e) {
            PRG.error("Error al crear usuario", "/usuario/c");
        }
        return "redirect:/usuario/r";
    }

    @GetMapping("u")
    public String u(@RequestParam("id") Long id, ModelMap m) {
        Usuario u = usuarioService.buscarPorId(id).orElse(null);
        m.put("usuario", u);
        m.put("view", "usuario/u");
        return "_t/frame";
    }

    @PostMapping("u")
    public String uPost(
            @RequestParam("id") Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("contrasena") String contrasena) throws DangerException {
        try {
            Usuario u = usuarioService.buscarPorId(id).orElse(null);
            if (u != null) {
                u.setNombre(nombre);
                u.setEmail(email);
                u.setContrasena(contrasena); // ✅ usar el campo correcto
                usuarioService.guardarUsuario(u);
            }
        } catch (Exception e) {
            PRG.error("Error al actualizar usuario", "/usuario/r");
        }
        return "redirect:/usuario/r";
    }

    @PostMapping("d")
    public String d(@RequestParam("id") Long id) throws DangerException {
        try {
            usuarioService.eliminarUsuario(id);
        } catch (Exception e) {
            PRG.error("No se pudo eliminar el usuario", "/usuario/r");
        }
        return "redirect:/usuario/r";
    }
}
