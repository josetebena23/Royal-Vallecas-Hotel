package org.agaray.pruebas.pap.controller;

import org.agaray.pruebas.pap.exception.DangerException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {

    @GetMapping("/")
    public String home(
        ModelMap m
    ) {
        m.put("view","/home/home");
        return "_t/frame";
    }

    @GetMapping("/test")
    public void test(
    ) throws Exception {
        throw new DangerException("¡¡¡¡ PUM !!!!!");
    }

    @GetMapping("/contador")
    public String contador(
        HttpSession s,
        ModelMap m
    ) {
        if ( s.getAttribute("nVisitas") == null  )   {
            s.setAttribute("nVisitas", 1);
        }
        m.put("visitas",s.getAttribute("nVisitas"));
        
        s.setAttribute("nVisitas", ((Integer)s.getAttribute("nVisitas"))+1);
        return "home/contador";
    }

}
