package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.FabricTypes;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FabricTypes}.
 */
public interface FabricTypesService {
    /**
     * Save a fabricTypes.
     *
     * @param fabricTypes the entity to save.
     * @return the persisted entity.
     */
    FabricTypes save(FabricTypes fabricTypes);

    /**
     * Updates a fabricTypes.
     *
     * @param fabricTypes the entity to update.
     * @return the persisted entity.
     */
    FabricTypes update(FabricTypes fabricTypes);

    /**
     * Partially updates a fabricTypes.
     *
     * @param fabricTypes the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FabricTypes> partialUpdate(FabricTypes fabricTypes);

    /**
     * Get all the fabricTypes.
     *
     * @return the list of entities.
     */
    List<FabricTypes> findAll();

    /**
     * Get the "id" fabricTypes.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FabricTypes> findOne(Long id);

    /**
     * Delete the "id" fabricTypes.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
