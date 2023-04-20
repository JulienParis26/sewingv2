package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.FabricUses;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FabricUses}.
 */
public interface FabricUsesService {
    /**
     * Save a fabricUses.
     *
     * @param fabricUses the entity to save.
     * @return the persisted entity.
     */
    FabricUses save(FabricUses fabricUses);

    /**
     * Updates a fabricUses.
     *
     * @param fabricUses the entity to update.
     * @return the persisted entity.
     */
    FabricUses update(FabricUses fabricUses);

    /**
     * Partially updates a fabricUses.
     *
     * @param fabricUses the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FabricUses> partialUpdate(FabricUses fabricUses);

    /**
     * Get all the fabricUses.
     *
     * @return the list of entities.
     */
    List<FabricUses> findAll();

    /**
     * Get the "id" fabricUses.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FabricUses> findOne(Long id);

    /**
     * Delete the "id" fabricUses.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
