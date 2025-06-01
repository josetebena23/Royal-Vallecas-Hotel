package org.tfc.pruebas.pap.controller;

import java.util.Optional;

import org.tfc.pruebas.pap.entities.Administrador;
import org.tfc.pruebas.pap.entities.Cliente;
import org.tfc.pruebas.pap.entities.Editor;
import org.tfc.pruebas.pap.entities.Reserva;
import org.tfc.pruebas.pap.entities.Usuario;
import org.tfc.pruebas.pap.services.ReservaService;
import org.tfc.pruebas.pap.services.UsuarioService;
import org.tfc.pruebas.pap.helper.PRG;
import org.tfc.pruebas.pap.exception.DangerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ReservaService reservaService;

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

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuario/login";
    }

    @GetMapping("/login")
    public String mostrarLogin(ModelMap m) {
        m.put("view", "usuario/login");
        return "_t/frame";
    }

    @PostMapping("/login")
    public String loginPost(
            @RequestParam("email") String email,
            @RequestParam("contrasena") String contrasena,
            HttpSession session,
            ModelMap m) throws DangerException {
        Optional<Usuario> usuarioOpt = usuarioService.buscarPorEmail(email);

        if (usuarioOpt.isEmpty() || !usuarioService.comprobarContrasena(contrasena, usuarioOpt.get().getContrasena())) {
            m.put("error", "Email o contraseña incorrectos");
            m.put("view", "usuario/login");
            return "_t/frame";
        }

        session.setAttribute("usuario", usuarioOpt.get());
        return "redirect:/"; // o a donde quieras enviarlo tras el login
    }

    @GetMapping("/registro")
    public String mostrarRegistro(ModelMap m) {
        m.put("view", "usuario/registro");
        return "_t/frame";
    }

    @PostMapping("/registro")
    public String registrarCliente(
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String contrasena,
            @RequestParam String contrasena2,
            ModelMap m) throws DangerException {
        // Validación de coincidencia
        if (!contrasena.equals(contrasena2)) {
            m.put("error", "Las contraseñas no coinciden.");
            m.put("view", "usuario/registro");
            return "_t/frame";
        }

        // Validar la complejidad
        if (!contrasenaValida(contrasena)) {
            m.put("error", "La contraseña debe tener al menos 6 caracteres, una mayúscula, una minúscula y un número.");
            m.put("view", "usuario/registro");
            return "_t/frame";
        }

        try {
            Usuario cliente = new Cliente();
            cliente.setNombre(nombre);
            cliente.setEmail(email);
            cliente.setContrasena(contrasena);
            cliente.setRol(Usuario.Rol.CLIENTE);

            usuarioService.guardarUsuario(cliente);
        } catch (Exception e) {
            m.put("error", "Ese correo ya está registrado.");
            m.put("nombre", nombre);
            m.put("email", email);
            m.put("view", "usuario/registro");
            return "_t/frame";
        }

        return "redirect:/usuario/login";
    }

    private boolean contrasenaValida(String contrasena) {
        return contrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$");
    }

    @PostMapping("u")
    public String uPost(
            @RequestParam("id") Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("email") String email,
            @RequestParam("contrasena") String contrasena,
            HttpSession session) throws DangerException {

        Usuario sessionUsuario = (Usuario) session.getAttribute("usuario");

        // Solo permite modificar si el que está en sesión es el mismo o un admin
        if (sessionUsuario == null
                || (!sessionUsuario.getId().equals(id) && sessionUsuario.getRol() != Usuario.Rol.ADMINISTRADOR)) {
            return "redirect:/";
        }

        try {
            Usuario u = usuarioService.buscarPorId(id).orElse(null);

            if (u != null) {
                // Validamos que el email no esté en uso por otro usuario
                Optional<Usuario> usuarioConEseEmail = usuarioService.buscarPorEmail(email);
                if (usuarioConEseEmail.isPresent() && !usuarioConEseEmail.get().getId().equals(u.getId())) {
                    PRG.error("Ese email ya está en uso por otro usuario", "/usuario/perfil");
                }

                u.setNombre(nombre);
                u.setEmail(email);

                if (contrasena != null && !contrasena.trim().isEmpty()
                        && !usuarioService.comprobarContrasena(contrasena, u.getContrasena())) {
                    u.setContrasena(contrasena); // ya se encripta dentro del service
                }

                usuarioService.guardarUsuario(u);

                // ✅ Actualizamos la sesión con el nuevo usuario actualizado
                session.setAttribute("usuario", u);
            }

        } catch (Exception e) {
            e.printStackTrace();
            PRG.error("Error al actualizar usuario", "/usuario/perfil");
        }

        return "redirect:/usuario/perfil?exito=true";
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

    @GetMapping("/perfil")
    public String verPerfilCliente(HttpSession session, ModelMap m) {
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || usuario.getRol() != Usuario.Rol.CLIENTE) {
            return "redirect:/usuario/login";
        }

        m.put("usuario", usuario);
        m.put("reservas", reservaService.obtenerReservasPorUsuario(usuario));
        m.put("view", "usuario/perfil");
        return "_t/frame";
    }

    @PostMapping("/cancelar-reserva")
    public String cancelarReserva(
            @RequestParam("idReserva") Long idReserva,
            HttpSession session) throws DangerException {

        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null || usuario.getRol() != Usuario.Rol.CLIENTE) {
            return "redirect:/usuario/login";
        }

        Reserva reserva = reservaService.buscarPorId(idReserva).orElse(null);

        if (reserva != null && reserva.getUsuario().getId().equals(usuario.getId())) {
            reservaService.cancelarReserva(idReserva);
        }

        return "redirect:/usuario/perfil";
    }

}
