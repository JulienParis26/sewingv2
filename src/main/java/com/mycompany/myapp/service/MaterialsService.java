package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Materials;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Materials}.
 */
public interface MaterialsService {
    /**
     * Save a materials.
     *
     * @param materials the entity to save.
     * @return the persisted entity.
     */
    Materials save(Materials materials);

    /**
     * Updates a materials.
     *
     * @param materials the entity to update.
     * @return the persisted entity.
     */
    Materials update(Materials materials);

    /**
     * Partially updates a materials.
     *
     * @param materials the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Materials> partialUpdate(Materials materials);

    /**
     * Get all the materials.
     *
     * @return the list of entities.
     */
    List<Materials> findAll();

    /**
     * Get the "id" materials.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Materials> findOne(Long id);

    /**
     * Delete the "id" materials.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
