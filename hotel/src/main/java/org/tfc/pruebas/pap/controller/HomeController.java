package org.tfc.pruebas.pap.controller;

import org.tfc.pruebas.pap.exception.DangerException;
import org.tfc.pruebas.pap.services.HabitacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @Autowired
    private HabitacionService habitacionService;

    @GetMapping("/")
    public String home(ModelMap m) {
        m.put("habitaciones", habitacionService.listarHabitaciones());
        m.put("view", "home/home");
        return "_t/frame";
    }

    @GetMapping("/test")
    public void test() throws Exception {
        throw new DangerException("¡¡¡¡ PUM !!!!!");
    }

    @GetMapping("/contador")
    public String contador(
            HttpSession s,
            ModelMap m) {
        if (s.getAttribute("nVisitas") == null) {
            s.setAttribute("nVisitas", 1);
        }
        m.put("visitas", s.getAttribute("nVisitas"));

        s.setAttribute("nVisitas", ((Integer) s.getAttribute("nVisitas")) + 1);
        return "home/contador";
    }

}
