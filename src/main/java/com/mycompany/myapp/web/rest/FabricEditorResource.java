package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FabricEditor;
import com.mycompany.myapp.repository.FabricEditorRepository;
import com.mycompany.myapp.service.FabricEditorService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.FabricEditor}.
 */
@RestController
@RequestMapping("/api")
public class FabricEditorResource {

    private final Logger log = LoggerFactory.getLogger(FabricEditorResource.class);

    private static final String ENTITY_NAME = "fabricEditor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FabricEditorService fabricEditorService;

    private final FabricEditorRepository fabricEditorRepository;

    public FabricEditorResource(FabricEditorService fabricEditorService, FabricEditorRepository fabricEditorRepository) {
        this.fabricEditorService = fabricEditorService;
        this.fabricEditorRepository = fabricEditorRepository;
    }

    /**
     * {@code POST  /fabric-editors} : Create a new fabricEditor.
     *
     * @param fabricEditor the fabricEditor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fabricEditor, or with status {@code 400 (Bad Request)} if the fabricEditor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fabric-editors")
    public ResponseEntity<FabricEditor> createFabricEditor(@RequestBody FabricEditor fabricEditor) throws URISyntaxException {
        log.debug("REST request to save FabricEditor : {}", fabricEditor);
        if (fabricEditor.getId() != null) {
            throw new BadRequestAlertException("A new fabricEditor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FabricEditor result = fabricEditorService.save(fabricEditor);
        return ResponseEntity
            .created(new URI("/api/fabric-editors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fabric-editors/:id} : Updates an existing fabricEditor.
     *
     * @param id the id of the fabricEditor to save.
     * @param fabricEditor the fabricEditor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fabricEditor,
     * or with status {@code 400 (Bad Request)} if the fabricEditor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fabricEditor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fabric-editors/{id}")
    public ResponseEntity<FabricEditor> updateFabricEditor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FabricEditor fabricEditor
    ) throws URISyntaxException {
        log.debug("REST request to update FabricEditor : {}, {}", id, fabricEditor);
        if (fabricEditor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fabricEditor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fabricEditorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FabricEditor result = fabricEditorService.update(fabricEditor);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fabricEditor.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fabric-editors/:id} : Partial updates given fields of an existing fabricEditor, field will ignore if it is null
     *
     * @param id the id of the fabricEditor to save.
     * @param fabricEditor the fabricEditor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fabricEditor,
     * or with status {@code 400 (Bad Request)} if the fabricEditor is not valid,
     * or with status {@code 404 (Not Found)} if the fabricEditor is not found,
     * or with status {@code 500 (Internal Server Error)} if the fabricEditor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fabric-editors/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FabricEditor> partialUpdateFabricEditor(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FabricEditor fabricEditor
    ) throws URISyntaxException {
        log.debug("REST request to partial update FabricEditor partially : {}, {}", id, fabricEditor);
        if (fabricEditor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fabricEditor.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fabricEditorRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FabricEditor> result = fabricEditorService.partialUpdate(fabricEditor);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fabricEditor.getId().toString())
        );
    }

    /**
     * {@code GET  /fabric-editors} : get all the fabricEditors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fabricEditors in body.
     */
    @GetMapping("/fabric-editors")
    public ResponseEntity<List<FabricEditor>> getAllFabricEditors(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of FabricEditors");
        Page<FabricEditor> page = fabricEditorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fabric-editors/:id} : get the "id" fabricEditor.
     *
     * @param id the id of the fabricEditor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fabricEditor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fabric-editors/{id}")
    public ResponseEntity<FabricEditor> getFabricEditor(@PathVariable Long id) {
        log.debug("REST request to get FabricEditor : {}", id);
        Optional<FabricEditor> fabricEditor = fabricEditorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fabricEditor);
    }

    /**
     * {@code DELETE  /fabric-editors/:id} : delete the "id" fabricEditor.
     *
     * @param id the id of the fabricEditor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fabric-editors/{id}")
    public ResponseEntity<Void> deleteFabricEditor(@PathVariable Long id) {
        log.debug("REST request to delete FabricEditor : {}", id);
        fabricEditorService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
