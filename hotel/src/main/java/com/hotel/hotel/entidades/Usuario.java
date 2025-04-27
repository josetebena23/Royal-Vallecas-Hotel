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
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}
