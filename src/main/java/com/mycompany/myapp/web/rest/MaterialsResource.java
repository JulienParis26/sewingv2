package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Materials;
import com.mycompany.myapp.repository.MaterialsRepository;
import com.mycompany.myapp.service.MaterialsService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Materials}.
 */
@RestController
@RequestMapping("/api")
public class MaterialsResource {

    private final Logger log = LoggerFactory.getLogger(MaterialsResource.class);

    private static final String ENTITY_NAME = "materials";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MaterialsService materialsService;

    private final MaterialsRepository materialsRepository;

    public MaterialsResource(MaterialsService materialsService, MaterialsRepository materialsRepository) {
        this.materialsService = materialsService;
        this.materialsRepository = materialsRepository;
    }

    /**
     * {@code POST  /materials} : Create a new materials.
     *
     * @param materials the materials to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new materials, or with status {@code 400 (Bad Request)} if the materials has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/materials")
    public ResponseEntity<Materials> createMaterials(@Valid @RequestBody Materials materials) throws URISyntaxException {
        log.debug("REST request to save Materials : {}", materials);
        if (materials.getId() != null) {
            throw new BadRequestAlertException("A new materials cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Materials result = materialsService.save(materials);
        return ResponseEntity
            .created(new URI("/api/materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /materials/:id} : Updates an existing materials.
     *
     * @param id the id of the materials to save.
     * @param materials the materials to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materials,
     * or with status {@code 400 (Bad Request)} if the materials is not valid,
     * or with status {@code 500 (Internal Server Error)} if the materials couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/materials/{id}")
    public ResponseEntity<Materials> updateMaterials(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Materials materials
    ) throws URISyntaxException {
        log.debug("REST request to update Materials : {}, {}", id, materials);
        if (materials.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materials.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Materials result = materialsService.update(materials);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materials.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /materials/:id} : Partial updates given fields of an existing materials, field will ignore if it is null
     *
     * @param id the id of the materials to save.
     * @param materials the materials to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated materials,
     * or with status {@code 400 (Bad Request)} if the materials is not valid,
     * or with status {@code 404 (Not Found)} if the materials is not found,
     * or with status {@code 500 (Internal Server Error)} if the materials couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/materials/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Materials> partialUpdateMaterials(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Materials materials
    ) throws URISyntaxException {
        log.debug("REST request to partial update Materials partially : {}, {}", id, materials);
        if (materials.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, materials.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!materialsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Materials> result = materialsService.partialUpdate(materials);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, materials.getId().toString())
        );
    }

    /**
     * {@code GET  /materials} : get all the materials.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of materials in body.
     */
    @GetMapping("/materials")
    public List<Materials> getAllMaterials() {
        log.debug("REST request to get all Materials");
        return materialsService.findAll();
    }

    /**
     * {@code GET  /materials/:id} : get the "id" materials.
     *
     * @param id the id of the materials to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the materials, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/materials/{id}")
    public ResponseEntity<Materials> getMaterials(@PathVariable Long id) {
        log.debug("REST request to get Materials : {}", id);
        Optional<Materials> materials = materialsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(materials);
    }

    /**
     * {@code DELETE  /materials/:id} : delete the "id" materials.
     *
     * @param id the id of the materials to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/materials/{id}")
    public ResponseEntity<Void> deleteMaterials(@PathVariable Long id) {
        log.debug("REST request to delete Materials : {}", id);
        materialsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
