package org.agaray.pruebas.pap.entities;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @Column(unique = true)
    private String nombre;

    @OneToMany(mappedBy = "nace")
    private Collection<Persona> nacidos;

    @OneToMany(mappedBy = "vive")
    private Collection<Persona> residentes;

    public Pais(String nombre) {
        this.nombre = nombre;
        this.nacidos = new ArrayList<>();
        this.residentes = new ArrayList<>();
    }

    public Pais() {
        this.nombre = "Desconocido";
        this.nacidos = new ArrayList<>();
        this.residentes = new ArrayList<>();
    }
    
    
}
