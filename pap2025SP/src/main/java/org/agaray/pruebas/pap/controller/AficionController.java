package org.agaray.pruebas.pap.controller;

import org.agaray.pruebas.pap.exception.DangerException;
import org.agaray.pruebas.pap.helper.PRG;
import org.agaray.pruebas.pap.services.AficionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/aficion")
public class AficionController {

    @Autowired
    private AficionService aficionService;

    
    @GetMapping("r")
    public String r(
        ModelMap m
    ) {
        m.put("aficiones", aficionService.r() );
        m.put("view","aficion/r" );
        return "_t/frame";
    }

    @GetMapping("c")
    public String c(
        ModelMap m
    ) {
        m.put("view","aficion/c");
        return "_t/frame";
    }

    @PostMapping("c")
    public String cPost(
        @RequestParam("nombre")
        String nombre
    ) throws DangerException {
        try {
            this.aficionService.c(nombre);
        }
        catch (Exception e) {
            PRG.error("La afición "+nombre+" ya está registrada","/aficion/c");
        }
        return "redirect:/aficion/r";
    }

    @PostMapping("d")
    public String d(
        @RequestParam("id")
        Long id
    ) throws DangerException {
        try {
            aficionService.d(id);
        }
        catch (Exception e) {
            PRG.error(e.getMessage(), "/aficion/r");
        }
        return "redirect:/aficion/r";
    }

    @GetMapping("u")
    public String u(
            @RequestParam("id") Long id,
            ModelMap m) {
        m.put("aficion",aficionService.rById(id));
        m.put("view", "aficion/u");
        return "_t/frame";
    }

    @PostMapping("u")
    public String uPost(
        @RequestParam("id") Long id,
        @RequestParam("nombre") String nombre
        ) throws DangerException {
        try {
            this.aficionService.u(id,nombre);
        } catch (Exception e) {
            PRG.error("La afición " + nombre + " ya existe", "/aficion/r");
        }
        return "redirect:/aficion/r";
    }
}
