package com.hotel.hotel.repositorios;

import com.hotel.hotel.entidades.Administrador;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    List<Administrador> findByDepartamento(String departamento);
}
