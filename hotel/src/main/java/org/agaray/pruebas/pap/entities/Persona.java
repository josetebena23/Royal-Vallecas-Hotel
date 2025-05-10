package org.agaray.pruebas.pap.entities;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Persona {

    //==========================================================================
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String apellido;

    @ManyToOne
    private Pais nace;

    @ManyToOne
    private Pais vive;

    @ManyToMany
    private Collection<Aficion> gustos;

    @ManyToMany
    private Collection<Aficion> odios;

    //==========================================================================

    public Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.gustos = new ArrayList<>();
        this.odios = new ArrayList<>();
    }
    public Persona() {
        this.nombre = "John";
        this.apellido = "Doe";
        this.gustos = new ArrayList<>();
        this.odios = new ArrayList<>();
    }
    
}
