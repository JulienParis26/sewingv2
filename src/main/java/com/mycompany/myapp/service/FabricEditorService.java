package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.FabricEditor;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FabricEditor}.
 */
public interface FabricEditorService {
    /**
     * Save a fabricEditor.
     *
     * @param fabricEditor the entity to save.
     * @return the persisted entity.
     */
    FabricEditor save(FabricEditor fabricEditor);

    /**
     * Updates a fabricEditor.
     *
     * @param fabricEditor the entity to update.
     * @return the persisted entity.
     */
    FabricEditor update(FabricEditor fabricEditor);

    /**
     * Partially updates a fabricEditor.
     *
     * @param fabricEditor the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FabricEditor> partialUpdate(FabricEditor fabricEditor);

    /**
     * Get all the fabricEditors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FabricEditor> findAll(Pageable pageable);

    /**
     * Get the "id" fabricEditor.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FabricEditor> findOne(Long id);

    /**
     * Delete the "id" fabricEditor.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
