package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FabricLabels;
import com.mycompany.myapp.repository.FabricLabelsRepository;
import com.mycompany.myapp.service.FabricLabelsService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FabricLabels}.
 */
@RestController
@RequestMapping("/api")
public class FabricLabelsResource {

    private final Logger log = LoggerFactory.getLogger(FabricLabelsResource.class);

    private static final String ENTITY_NAME = "fabricLabels";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FabricLabelsService fabricLabelsService;

    private final FabricLabelsRepository fabricLabelsRepository;

    public FabricLabelsResource(FabricLabelsService fabricLabelsService, FabricLabelsRepository fabricLabelsRepository) {
        this.fabricLabelsService = fabricLabelsService;
        this.fabricLabelsRepository = fabricLabelsRepository;
    }

    /**
     * {@code POST  /fabric-labels} : Create a new fabricLabels.
     *
     * @param fabricLabels the fabricLabels to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fabricLabels, or with status {@code 400 (Bad Request)} if the fabricLabels has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fabric-labels")
    public ResponseEntity<FabricLabels> createFabricLabels(@Valid @RequestBody FabricLabels fabricLabels) throws URISyntaxException {
        log.debug("REST request to save FabricLabels : {}", fabricLabels);
        if (fabricLabels.getId() != null) {
            throw new BadRequestAlertException("A new fabricLabels cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FabricLabels result = fabricLabelsService.save(fabricLabels);
        return ResponseEntity
            .created(new URI("/api/fabric-labels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fabric-labels/:id} : Updates an existing fabricLabels.
     *
     * @param id the id of the fabricLabels to save.
     * @param fabricLabels the fabricLabels to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fabricLabels,
     * or with status {@code 400 (Bad Request)} if the fabricLabels is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fabricLabels couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fabric-labels/{id}")
    public ResponseEntity<FabricLabels> updateFabricLabels(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FabricLabels fabricLabels
    ) throws URISyntaxException {
        log.debug("REST request to update FabricLabels : {}, {}", id, fabricLabels);
        if (fabricLabels.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fabricLabels.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fabricLabelsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FabricLabels result = fabricLabelsService.update(fabricLabels);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fabricLabels.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fabric-labels/:id} : Partial updates given fields of an existing fabricLabels, field will ignore if it is null
     *
     * @param id the id of the fabricLabels to save.
     * @param fabricLabels the fabricLabels to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fabricLabels,
     * or with status {@code 400 (Bad Request)} if the fabricLabels is not valid,
     * or with status {@code 404 (Not Found)} if the fabricLabels is not found,
     * or with status {@code 500 (Internal Server Error)} if the fabricLabels couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fabric-labels/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FabricLabels> partialUpdateFabricLabels(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FabricLabels fabricLabels
    ) throws URISyntaxException {
        log.debug("REST request to partial update FabricLabels partially : {}, {}", id, fabricLabels);
        if (fabricLabels.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fabricLabels.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fabricLabelsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FabricLabels> result = fabricLabelsService.partialUpdate(fabricLabels);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fabricLabels.getId().toString())
        );
    }

    /**
     * {@code GET  /fabric-labels} : get all the fabricLabels.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fabricLabels in body.
     */
    @GetMapping("/fabric-labels")
    public List<FabricLabels> getAllFabricLabels() {
        log.debug("REST request to get all FabricLabels");
        return fabricLabelsService.findAll();
    }

    /**
     * {@code GET  /fabric-labels/:id} : get the "id" fabricLabels.
     *
     * @param id the id of the fabricLabels to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fabricLabels, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fabric-labels/{id}")
    public ResponseEntity<FabricLabels> getFabricLabels(@PathVariable Long id) {
        log.debug("REST request to get FabricLabels : {}", id);
        Optional<FabricLabels> fabricLabels = fabricLabelsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fabricLabels);
    }

    /**
     * {@code DELETE  /fabric-labels/:id} : delete the "id" fabricLabels.
     *
     * @param id the id of the fabricLabels to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fabric-labels/{id}")
    public ResponseEntity<Void> deleteFabricLabels(@PathVariable Long id) {
        log.debug("REST request to delete FabricLabels : {}", id);
        fabricLabelsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
