package org.agaray.pruebas.pap.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "editores")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Editor extends Usuario {
    @Column(name = "puede_publicar", nullable = false)
    private Boolean puedePublicar = false;

    @Column(name = "area_responsabilidad")
    private String areaResponsabilidad;
}