package com.hotel.hotel.entidades;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "habitaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Integer numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Tipo tipo;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Estado estado = Estado.DISPONIBLE;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String reseña;

    public enum Tipo {
        INDIVIDUAL, DOBLE, SUITE
    }

    public enum Estado {
        DISPONIBLE, OCUPADA, MANTENIMIENTO
    }
}