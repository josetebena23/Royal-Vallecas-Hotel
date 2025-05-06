package org.agaray.pruebas.pap.entities;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
@Entity
@Data
public class Aficion {

    //==========================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    @ManyToMany(mappedBy = "gustos")
    private Collection<Persona> aficionados;

    @ManyToMany(mappedBy = "odios")
    private Collection<Persona> haters;

    //==========================================================================

    public Aficion(String nombre) {
        this.nombre = nombre;
        this.aficionados = new ArrayList<>();
        this.haters = new ArrayList<>();
    }

    public Aficion() {
        this.nombre = "Afición sin nombre";
        this.aficionados = new ArrayList<>();
        this.haters = new ArrayList<>();
    }
    //Hola
}
