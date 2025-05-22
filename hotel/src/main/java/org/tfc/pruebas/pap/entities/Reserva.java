package org.tfc.pruebas.pap.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@Data // Genera getters, setters, equals, hashCode y toString
@NoArgsConstructor // Constructor sin argumentos
@AllArgsConstructor // Constructor con todos los argumentos
@Builder // Patrón builder para creación flexible de objetos
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "habitacion_id", nullable = false)
    private Habitacion habitacion;

    @Column(name = "fecha_entrada", nullable = false)
    private LocalDate fechaEntrada;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate fechaSalida;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default // Valor por defecto al usar el builder
    private Estado estado = Estado.PENDIENTE;

    @Column(name = "fecha_reserva", updatable = false)
    @Builder.Default
    private LocalDate fechaReserva = LocalDate.now();

    public enum Estado {
        PENDIENTE, CONFIRMADA, CANCELADA, COMPLETADA
    }
}