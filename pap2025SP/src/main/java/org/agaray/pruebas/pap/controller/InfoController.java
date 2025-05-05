package org.agaray.pruebas.pap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class InfoController {

    @GetMapping("/info")
    public String info(
        ModelMap m,
        HttpSession s
    ) {
        String mensaje =    (String)s.getAttribute("_mensaje");
	    String link =       (String)s.getAttribute("_link");
	    String severity =   (String)s.getAttribute("_severity");

        s.invalidate();
        m.put("mensaje",mensaje);
        m.put("link",link);
        m.put("severity",severity);

        m.put("view","_t/info");
        return "_t/frame";
    }
}
