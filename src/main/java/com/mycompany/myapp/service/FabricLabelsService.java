package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.FabricLabels;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FabricLabels}.
 */
public interface FabricLabelsService {
    /**
     * Save a fabricLabels.
     *
     * @param fabricLabels the entity to save.
     * @return the persisted entity.
     */
    FabricLabels save(FabricLabels fabricLabels);

    /**
     * Updates a fabricLabels.
     *
     * @param fabricLabels the entity to update.
     * @return the persisted entity.
     */
    FabricLabels update(FabricLabels fabricLabels);

    /**
     * Partially updates a fabricLabels.
     *
     * @param fabricLabels the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FabricLabels> partialUpdate(FabricLabels fabricLabels);

    /**
     * Get all the fabricLabels.
     *
     * @return the list of entities.
     */
    List<FabricLabels> findAll();

    /**
     * Get the "id" fabricLabels.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FabricLabels> findOne(Long id);

    /**
     * Delete the "id" fabricLabels.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
