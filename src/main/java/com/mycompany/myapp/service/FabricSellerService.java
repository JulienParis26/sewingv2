package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.FabricSeller;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FabricSeller}.
 */
public interface FabricSellerService {
    /**
     * Save a fabricSeller.
     *
     * @param fabricSeller the entity to save.
     * @return the persisted entity.
     */
    FabricSeller save(FabricSeller fabricSeller);

    /**
     * Updates a fabricSeller.
     *
     * @param fabricSeller the entity to update.
     * @return the persisted entity.
     */
    FabricSeller update(FabricSeller fabricSeller);

    /**
     * Partially updates a fabricSeller.
     *
     * @param fabricSeller the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FabricSeller> partialUpdate(FabricSeller fabricSeller);

    /**
     * Get all the fabricSellers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FabricSeller> findAll(Pageable pageable);

    /**
     * Get all the fabricSellers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FabricSeller> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" fabricSeller.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FabricSeller> findOne(Long id);

    /**
     * Delete the "id" fabricSeller.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
