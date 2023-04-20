package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FabricUses;
import com.mycompany.myapp.repository.FabricUsesRepository;
import com.mycompany.myapp.service.FabricUsesService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FabricUses}.
 */
@RestController
@RequestMapping("/api")
public class FabricUsesResource {

    private final Logger log = LoggerFactory.getLogger(FabricUsesResource.class);

    private static final String ENTITY_NAME = "fabricUses";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FabricUsesService fabricUsesService;

    private final FabricUsesRepository fabricUsesRepository;

    public FabricUsesResource(FabricUsesService fabricUsesService, FabricUsesRepository fabricUsesRepository) {
        this.fabricUsesService = fabricUsesService;
        this.fabricUsesRepository = fabricUsesRepository;
    }

    /**
     * {@code POST  /fabric-uses} : Create a new fabricUses.
     *
     * @param fabricUses the fabricUses to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fabricUses, or with status {@code 400 (Bad Request)} if the fabricUses has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fabric-uses")
    public ResponseEntity<FabricUses> createFabricUses(@Valid @RequestBody FabricUses fabricUses) throws URISyntaxException {
        log.debug("REST request to save FabricUses : {}", fabricUses);
        if (fabricUses.getId() != null) {
            throw new BadRequestAlertException("A new fabricUses cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FabricUses result = fabricUsesService.save(fabricUses);
        return ResponseEntity
            .created(new URI("/api/fabric-uses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fabric-uses/:id} : Updates an existing fabricUses.
     *
     * @param id the id of the fabricUses to save.
     * @param fabricUses the fabricUses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fabricUses,
     * or with status {@code 400 (Bad Request)} if the fabricUses is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fabricUses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fabric-uses/{id}")
    public ResponseEntity<FabricUses> updateFabricUses(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FabricUses fabricUses
    ) throws URISyntaxException {
        log.debug("REST request to update FabricUses : {}, {}", id, fabricUses);
        if (fabricUses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fabricUses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fabricUsesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FabricUses result = fabricUsesService.update(fabricUses);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fabricUses.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fabric-uses/:id} : Partial updates given fields of an existing fabricUses, field will ignore if it is null
     *
     * @param id the id of the fabricUses to save.
     * @param fabricUses the fabricUses to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fabricUses,
     * or with status {@code 400 (Bad Request)} if the fabricUses is not valid,
     * or with status {@code 404 (Not Found)} if the fabricUses is not found,
     * or with status {@code 500 (Internal Server Error)} if the fabricUses couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fabric-uses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FabricUses> partialUpdateFabricUses(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FabricUses fabricUses
    ) throws URISyntaxException {
        log.debug("REST request to partial update FabricUses partially : {}, {}", id, fabricUses);
        if (fabricUses.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fabricUses.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fabricUsesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FabricUses> result = fabricUsesService.partialUpdate(fabricUses);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fabricUses.getId().toString())
        );
    }

    /**
     * {@code GET  /fabric-uses} : get all the fabricUses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fabricUses in body.
     */
    @GetMapping("/fabric-uses")
    public List<FabricUses> getAllFabricUses() {
        log.debug("REST request to get all FabricUses");
        return fabricUsesService.findAll();
    }

    /**
     * {@code GET  /fabric-uses/:id} : get the "id" fabricUses.
     *
     * @param id the id of the fabricUses to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fabricUses, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fabric-uses/{id}")
    public ResponseEntity<FabricUses> getFabricUses(@PathVariable Long id) {
        log.debug("REST request to get FabricUses : {}", id);
        Optional<FabricUses> fabricUses = fabricUsesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fabricUses);
    }

    /**
     * {@code DELETE  /fabric-uses/:id} : delete the "id" fabricUses.
     *
     * @param id the id of the fabricUses to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fabric-uses/{id}")
    public ResponseEntity<Void> deleteFabricUses(@PathVariable Long id) {
        log.debug("REST request to delete FabricUses : {}", id);
        fabricUsesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
