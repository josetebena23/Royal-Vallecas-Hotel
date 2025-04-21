package com.hotel.hotel.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false, length = 100)
    protected String nombre;

    @Column(nullable = false, length = 100)
    protected String apellido;

    @Column(nullable = false, unique = true, length = 100)
    protected String email;

    @Column(nullable = false, length = 255)
    protected String password;

    @Column(length = 20)
    protected String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    protected Rol rol = Rol.CLIENTE;

    @Column(name = "fecha_registro", updatable = false)
    protected LocalDateTime fechaRegistro = LocalDateTime.now();

    public enum Rol {
        ADMIN, CLIENTE, EDITOR, INVITADO
    }

    // Getters y setters
}
