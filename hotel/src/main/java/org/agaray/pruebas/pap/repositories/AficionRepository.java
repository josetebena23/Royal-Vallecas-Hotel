package org.agaray.pruebas.pap.repositories;

import org.agaray.pruebas.pap.entities.Aficion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AficionRepository extends JpaRepository<Aficion,Long> {
}
