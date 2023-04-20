package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Fabric;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Fabric}.
 */
public interface FabricService {
    /**
     * Save a fabric.
     *
     * @param fabric the entity to save.
     * @return the persisted entity.
     */
    Fabric save(Fabric fabric);

    /**
     * Updates a fabric.
     *
     * @param fabric the entity to update.
     * @return the persisted entity.
     */
    Fabric update(Fabric fabric);

    /**
     * Partially updates a fabric.
     *
     * @param fabric the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Fabric> partialUpdate(Fabric fabric);

    /**
     * Get all the fabrics.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Fabric> findAll(Pageable pageable);

    /**
     * Get all the fabrics with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Fabric> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" fabric.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Fabric> findOne(Long id);

    /**
     * Delete the "id" fabric.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
