package org.tfc.pruebas.pap.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.tfc.pruebas.pap.entities.Editor;

public interface EditorRepository extends JpaRepository<Editor, Long> {
}
