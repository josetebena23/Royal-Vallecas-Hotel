package org.agaray.pruebas.pap.repositories;

import org.agaray.pruebas.pap.entities.Editor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EditorRepository extends JpaRepository<Editor, Long> {
}
