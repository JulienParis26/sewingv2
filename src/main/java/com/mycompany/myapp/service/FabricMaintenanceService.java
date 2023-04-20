package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.FabricMaintenance;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link FabricMaintenance}.
 */
public interface FabricMaintenanceService {
    /**
     * Save a fabricMaintenance.
     *
     * @param fabricMaintenance the entity to save.
     * @return the persisted entity.
     */
    FabricMaintenance save(FabricMaintenance fabricMaintenance);

    /**
     * Updates a fabricMaintenance.
     *
     * @param fabricMaintenance the entity to update.
     * @return the persisted entity.
     */
    FabricMaintenance update(FabricMaintenance fabricMaintenance);

    /**
     * Partially updates a fabricMaintenance.
     *
     * @param fabricMaintenance the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FabricMaintenance> partialUpdate(FabricMaintenance fabricMaintenance);

    /**
     * Get all the fabricMaintenances.
     *
     * @return the list of entities.
     */
    List<FabricMaintenance> findAll();

    /**
     * Get the "id" fabricMaintenance.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FabricMaintenance> findOne(Long id);

    /**
     * Delete the "id" fabricMaintenance.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
