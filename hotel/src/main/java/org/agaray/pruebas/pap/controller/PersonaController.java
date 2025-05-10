package org.agaray.pruebas.pap.controller;

import java.util.ArrayList;
import java.util.List;

import org.agaray.pruebas.pap.exception.DangerException;
import org.agaray.pruebas.pap.helper.PRG;
import org.agaray.pruebas.pap.services.AficionService;
import org.agaray.pruebas.pap.services.PaisService;
import org.agaray.pruebas.pap.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/persona")
public class PersonaController {

    @Autowired
    private PersonaService personaService;

    @Autowired
    private PaisService paisService;

    @Autowired
    private AficionService aficionService;

    
    @GetMapping("r")
    public String r(
        ModelMap m
    ) {
        m.put("personas", personaService.r() );
        m.put("view","persona/r");
        return "_t/frame";
    }

    @GetMapping("c")
    public String c(
        ModelMap m
    ) {
        m.put("paises", paisService.r() );
        m.put("aficiones", aficionService.r() );
        m.put("view","persona/c");
        return "_t/frame";
    }

    @PostMapping("c")
    public String cPost(
        @RequestParam("nombre")
        String nombre,
        @RequestParam("apellido")
        String apellido,
        @RequestParam("idPaisNacimiento")
        Long idPaisNacimiento,
        @RequestParam("idPaisResidencia")
        Long idPaisResidencia,
        @RequestParam(value="idAfG[]", required=false) List<Long> idsAficionGusta,
        @RequestParam(value="idAfO[]", required=false ) List<Long> idsAficionOdia
    ) {
        idsAficionGusta = idsAficionGusta == null ? new ArrayList<>() : idsAficionGusta;
        idsAficionOdia = idsAficionOdia == null ? new ArrayList<>() : idsAficionOdia;
        
        this.personaService.c(nombre, apellido, idPaisNacimiento, idPaisResidencia,idsAficionGusta,idsAficionOdia);
        return "redirect:/persona/r";
    }

     @PostMapping("d")
    public String d(
        @RequestParam("id")
        Long id
    ) throws DangerException {
        try {
            personaService.d(id);
        }
        catch (Exception e) {
            PRG.error(e.getMessage(), "/persona/r");
        }
        return "redirect:/persona/r";
    }

    @GetMapping("u")
    public String u(
            @RequestParam("id") Long id,
            ModelMap m) {
        m.put("persona",personaService.rById(id));
        m.put("paises",paisService.r());
        m.put("aficiones",aficionService.r());
        m.put("view", "persona/u");
        return "_t/frame";
    }

    @PostMapping("u")
    public String uPost(
        @RequestParam("id")
        Long id,
        @RequestParam("nombre")
        String nombre,
        @RequestParam("apellido")
        String apellido,
        @RequestParam("idPaisNacimiento")
        Long idPaisNacimiento,
        @RequestParam("idPaisResidencia")
        Long idPaisResidencia,
        @RequestParam(value="idAfG[]", required=false) List<Long> idsAficionGusta,
        @RequestParam(value="idAfO[]", required=false ) List<Long> idsAficionOdia
    ) {
        idsAficionGusta = idsAficionGusta == null ? new ArrayList<>() : idsAficionGusta;
        idsAficionOdia = idsAficionOdia == null ? new ArrayList<>() : idsAficionOdia;
        
        this.personaService.u(id,nombre, apellido, idPaisNacimiento, idPaisResidencia,idsAficionGusta,idsAficionOdia);
        return "redirect:/persona/r";
    }

}
