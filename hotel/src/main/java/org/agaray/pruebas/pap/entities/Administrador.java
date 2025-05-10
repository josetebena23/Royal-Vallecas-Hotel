package org.agaray.pruebas.pap.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "administradores")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Administrador extends Usuario {
    @Column(nullable = false)
    private String departamento;

    @Column(name = "nivel_acceso", nullable = false)
    private Integer nivelAcceso;
}