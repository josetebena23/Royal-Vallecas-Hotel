package org.agaray.pruebas.pap.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "imagenes_habitacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagenHabitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "habitacion_id")
    private Habitacion habitacion;
}
