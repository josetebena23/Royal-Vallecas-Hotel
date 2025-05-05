package org.agaray.pruebas.pap.controller;

import org.agaray.pruebas.pap.exception.DangerException;
import org.agaray.pruebas.pap.exception.InfoException;
import org.agaray.pruebas.pap.helper.PRG;
import org.agaray.pruebas.pap.services.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pais")
public class PaisController {

    @Autowired
    private PaisService paisService;

    @GetMapping("r")
    public String r(
            ModelMap m) {
        m.put("paises", paisService.r());
        m.put("view", "pais/r");
        return "_t/frame";
    }

    @GetMapping("c")
    public String c(
            ModelMap m) {
        m.put("view", "pais/c");
        return "_t/frame";
    }

    @PostMapping("c")
    public String cPost(
            @RequestParam("nombre") String nombre) throws DangerException, InfoException {
        try {
            this.paisService.c(nombre);
        } catch (Exception e) {
            PRG.error("El país " + nombre + " ya existe", "/pais/c");
        }
        return "redirect:/pais/r";
    }

    @PostMapping("d")
    public String d(
        @RequestParam("id")
        Long id
    ) throws DangerException {
        try {
            paisService.d(id);
        }
        catch (Exception e) {
            PRG.error(e.getMessage(), "/pais/r");
        }
        return "redirect:/pais/r";
    }

    @GetMapping("u")
    public String u(
            @RequestParam("id") Long id,
            ModelMap m) {
        m.put("pais",paisService.rById(id));
        m.put("view", "pais/u");
        return "_t/frame";
    }

    @PostMapping("u")
    public String uPost(
        @RequestParam("id") Long id,
        @RequestParam("nombre") String nombre
        ) throws DangerException {
        try {
            this.paisService.u(id,nombre);
        } catch (Exception e) {
            PRG.error("El país " + nombre + " ya existe", "/pais/r");
        }
        return "redirect:/pais/r";
    }
}
