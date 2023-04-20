package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FabricTypes;
import com.mycompany.myapp.repository.FabricTypesRepository;
import com.mycompany.myapp.service.FabricTypesService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.FabricTypes}.
 */
@RestController
@RequestMapping("/api")
public class FabricTypesResource {

    private final Logger log = LoggerFactory.getLogger(FabricTypesResource.class);

    private static final String ENTITY_NAME = "fabricTypes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FabricTypesService fabricTypesService;

    private final FabricTypesRepository fabricTypesRepository;

    public FabricTypesResource(FabricTypesService fabricTypesService, FabricTypesRepository fabricTypesRepository) {
        this.fabricTypesService = fabricTypesService;
        this.fabricTypesRepository = fabricTypesRepository;
    }

    /**
     * {@code POST  /fabric-types} : Create a new fabricTypes.
     *
     * @param fabricTypes the fabricTypes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fabricTypes, or with status {@code 400 (Bad Request)} if the fabricTypes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fabric-types")
    public ResponseEntity<FabricTypes> createFabricTypes(@Valid @RequestBody FabricTypes fabricTypes) throws URISyntaxException {
        log.debug("REST request to save FabricTypes : {}", fabricTypes);
        if (fabricTypes.getId() != null) {
            throw new BadRequestAlertException("A new fabricTypes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FabricTypes result = fabricTypesService.save(fabricTypes);
        return ResponseEntity
            .created(new URI("/api/fabric-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fabric-types/:id} : Updates an existing fabricTypes.
     *
     * @param id the id of the fabricTypes to save.
     * @param fabricTypes the fabricTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fabricTypes,
     * or with status {@code 400 (Bad Request)} if the fabricTypes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fabricTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fabric-types/{id}")
    public ResponseEntity<FabricTypes> updateFabricTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FabricTypes fabricTypes
    ) throws URISyntaxException {
        log.debug("REST request to update FabricTypes : {}, {}", id, fabricTypes);
        if (fabricTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fabricTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fabricTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FabricTypes result = fabricTypesService.update(fabricTypes);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fabricTypes.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fabric-types/:id} : Partial updates given fields of an existing fabricTypes, field will ignore if it is null
     *
     * @param id the id of the fabricTypes to save.
     * @param fabricTypes the fabricTypes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fabricTypes,
     * or with status {@code 400 (Bad Request)} if the fabricTypes is not valid,
     * or with status {@code 404 (Not Found)} if the fabricTypes is not found,
     * or with status {@code 500 (Internal Server Error)} if the fabricTypes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fabric-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FabricTypes> partialUpdateFabricTypes(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FabricTypes fabricTypes
    ) throws URISyntaxException {
        log.debug("REST request to partial update FabricTypes partially : {}, {}", id, fabricTypes);
        if (fabricTypes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fabricTypes.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fabricTypesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FabricTypes> result = fabricTypesService.partialUpdate(fabricTypes);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fabricTypes.getId().toString())
        );
    }

    /**
     * {@code GET  /fabric-types} : get all the fabricTypes.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fabricTypes in body.
     */
    @GetMapping("/fabric-types")
    public List<FabricTypes> getAllFabricTypes() {
        log.debug("REST request to get all FabricTypes");
        return fabricTypesService.findAll();
    }

    /**
     * {@code GET  /fabric-types/:id} : get the "id" fabricTypes.
     *
     * @param id the id of the fabricTypes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fabricTypes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fabric-types/{id}")
    public ResponseEntity<FabricTypes> getFabricTypes(@PathVariable Long id) {
        log.debug("REST request to get FabricTypes : {}", id);
        Optional<FabricTypes> fabricTypes = fabricTypesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fabricTypes);
    }

    /**
     * {@code DELETE  /fabric-types/:id} : delete the "id" fabricTypes.
     *
     * @param id the id of the fabricTypes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fabric-types/{id}")
    public ResponseEntity<Void> deleteFabricTypes(@PathVariable Long id) {
        log.debug("REST request to delete FabricTypes : {}", id);
        fabricTypesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
