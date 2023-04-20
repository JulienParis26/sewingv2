package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FabricEditor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FabricEditor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FabricEditorRepository extends JpaRepository<FabricEditor, Long> {}
