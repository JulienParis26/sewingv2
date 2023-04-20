package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FabricMaintenance;
import com.mycompany.myapp.repository.FabricMaintenanceRepository;
import com.mycompany.myapp.service.FabricMaintenanceService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FabricMaintenance}.
 */
@RestController
@RequestMapping("/api")
public class FabricMaintenanceResource {

    private final Logger log = LoggerFactory.getLogger(FabricMaintenanceResource.class);

    private static final String ENTITY_NAME = "fabricMaintenance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FabricMaintenanceService fabricMaintenanceService;

    private final FabricMaintenanceRepository fabricMaintenanceRepository;

    public FabricMaintenanceResource(
        FabricMaintenanceService fabricMaintenanceService,
        FabricMaintenanceRepository fabricMaintenanceRepository
    ) {
        this.fabricMaintenanceService = fabricMaintenanceService;
        this.fabricMaintenanceRepository = fabricMaintenanceRepository;
    }

    /**
     * {@code POST  /fabric-maintenances} : Create a new fabricMaintenance.
     *
     * @param fabricMaintenance the fabricMaintenance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fabricMaintenance, or with status {@code 400 (Bad Request)} if the fabricMaintenance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fabric-maintenances")
    public ResponseEntity<FabricMaintenance> createFabricMaintenance(@Valid @RequestBody FabricMaintenance fabricMaintenance)
        throws URISyntaxException {
        log.debug("REST request to save FabricMaintenance : {}", fabricMaintenance);
        if (fabricMaintenance.getId() != null) {
            throw new BadRequestAlertException("A new fabricMaintenance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FabricMaintenance result = fabricMaintenanceService.save(fabricMaintenance);
        return ResponseEntity
            .created(new URI("/api/fabric-maintenances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fabric-maintenances/:id} : Updates an existing fabricMaintenance.
     *
     * @param id the id of the fabricMaintenance to save.
     * @param fabricMaintenance the fabricMaintenance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fabricMaintenance,
     * or with status {@code 400 (Bad Request)} if the fabricMaintenance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fabricMaintenance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fabric-maintenances/{id}")
    public ResponseEntity<FabricMaintenance> updateFabricMaintenance(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FabricMaintenance fabricMaintenance
    ) throws URISyntaxException {
        log.debug("REST request to update FabricMaintenance : {}, {}", id, fabricMaintenance);
        if (fabricMaintenance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fabricMaintenance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fabricMaintenanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FabricMaintenance result = fabricMaintenanceService.update(fabricMaintenance);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fabricMaintenance.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fabric-maintenances/:id} : Partial updates given fields of an existing fabricMaintenance, field will ignore if it is null
     *
     * @param id the id of the fabricMaintenance to save.
     * @param fabricMaintenance the fabricMaintenance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fabricMaintenance,
     * or with status {@code 400 (Bad Request)} if the fabricMaintenance is not valid,
     * or with status {@code 404 (Not Found)} if the fabricMaintenance is not found,
     * or with status {@code 500 (Internal Server Error)} if the fabricMaintenance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fabric-maintenances/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FabricMaintenance> partialUpdateFabricMaintenance(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FabricMaintenance fabricMaintenance
    ) throws URISyntaxException {
        log.debug("REST request to partial update FabricMaintenance partially : {}, {}", id, fabricMaintenance);
        if (fabricMaintenance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fabricMaintenance.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fabricMaintenanceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FabricMaintenance> result = fabricMaintenanceService.partialUpdate(fabricMaintenance);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fabricMaintenance.getId().toString())
        );
    }

    /**
     * {@code GET  /fabric-maintenances} : get all the fabricMaintenances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fabricMaintenances in body.
     */
    @GetMapping("/fabric-maintenances")
    public List<FabricMaintenance> getAllFabricMaintenances() {
        log.debug("REST request to get all FabricMaintenances");
        return fabricMaintenanceService.findAll();
    }

    /**
     * {@code GET  /fabric-maintenances/:id} : get the "id" fabricMaintenance.
     *
     * @param id the id of the fabricMaintenance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fabricMaintenance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fabric-maintenances/{id}")
    public ResponseEntity<FabricMaintenance> getFabricMaintenance(@PathVariable Long id) {
        log.debug("REST request to get FabricMaintenance : {}", id);
        Optional<FabricMaintenance> fabricMaintenance = fabricMaintenanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fabricMaintenance);
    }

    /**
     * {@code DELETE  /fabric-maintenances/:id} : delete the "id" fabricMaintenance.
     *
     * @param id the id of the fabricMaintenance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fabric-maintenances/{id}")
    public ResponseEntity<Void> deleteFabricMaintenance(@PathVariable Long id) {
        log.debug("REST request to delete FabricMaintenance : {}", id);
        fabricMaintenanceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
