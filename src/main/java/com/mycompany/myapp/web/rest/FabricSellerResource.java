package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.FabricSeller;
import com.mycompany.myapp.repository.FabricSellerRepository;
import com.mycompany.myapp.service.FabricSellerService;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.FabricSeller}.
 */
@RestController
@RequestMapping("/api")
public class FabricSellerResource {

    private final Logger log = LoggerFactory.getLogger(FabricSellerResource.class);

    private static final String ENTITY_NAME = "fabricSeller";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FabricSellerService fabricSellerService;

    private final FabricSellerRepository fabricSellerRepository;

    public FabricSellerResource(FabricSellerService fabricSellerService, FabricSellerRepository fabricSellerRepository) {
        this.fabricSellerService = fabricSellerService;
        this.fabricSellerRepository = fabricSellerRepository;
    }

    /**
     * {@code POST  /fabric-sellers} : Create a new fabricSeller.
     *
     * @param fabricSeller the fabricSeller to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fabricSeller, or with status {@code 400 (Bad Request)} if the fabricSeller has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fabric-sellers")
    public ResponseEntity<FabricSeller> createFabricSeller(@Valid @RequestBody FabricSeller fabricSeller) throws URISyntaxException {
        log.debug("REST request to save FabricSeller : {}", fabricSeller);
        if (fabricSeller.getId() != null) {
            throw new BadRequestAlertException("A new fabricSeller cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FabricSeller result = fabricSellerService.save(fabricSeller);
        return ResponseEntity
            .created(new URI("/api/fabric-sellers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fabric-sellers/:id} : Updates an existing fabricSeller.
     *
     * @param id the id of the fabricSeller to save.
     * @param fabricSeller the fabricSeller to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fabricSeller,
     * or with status {@code 400 (Bad Request)} if the fabricSeller is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fabricSeller couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fabric-sellers/{id}")
    public ResponseEntity<FabricSeller> updateFabricSeller(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FabricSeller fabricSeller
    ) throws URISyntaxException {
        log.debug("REST request to update FabricSeller : {}, {}", id, fabricSeller);
        if (fabricSeller.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fabricSeller.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fabricSellerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FabricSeller result = fabricSellerService.update(fabricSeller);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fabricSeller.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fabric-sellers/:id} : Partial updates given fields of an existing fabricSeller, field will ignore if it is null
     *
     * @param id the id of the fabricSeller to save.
     * @param fabricSeller the fabricSeller to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fabricSeller,
     * or with status {@code 400 (Bad Request)} if the fabricSeller is not valid,
     * or with status {@code 404 (Not Found)} if the fabricSeller is not found,
     * or with status {@code 500 (Internal Server Error)} if the fabricSeller couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/fabric-sellers/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FabricSeller> partialUpdateFabricSeller(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FabricSeller fabricSeller
    ) throws URISyntaxException {
        log.debug("REST request to partial update FabricSeller partially : {}, {}", id, fabricSeller);
        if (fabricSeller.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fabricSeller.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fabricSellerRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FabricSeller> result = fabricSellerService.partialUpdate(fabricSeller);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fabricSeller.getId().toString())
        );
    }

    /**
     * {@code GET  /fabric-sellers} : get all the fabricSellers.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fabricSellers in body.
     */
    @GetMapping("/fabric-sellers")
    public ResponseEntity<List<FabricSeller>> getAllFabricSellers(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of FabricSellers");
        Page<FabricSeller> page;
        if (eagerload) {
            page = fabricSellerService.findAllWithEagerRelationships(pageable);
        } else {
            page = fabricSellerService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fabric-sellers/:id} : get the "id" fabricSeller.
     *
     * @param id the id of the fabricSeller to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fabricSeller, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fabric-sellers/{id}")
    public ResponseEntity<FabricSeller> getFabricSeller(@PathVariable Long id) {
        log.debug("REST request to get FabricSeller : {}", id);
        Optional<FabricSeller> fabricSeller = fabricSellerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fabricSeller);
    }

    /**
     * {@code DELETE  /fabric-sellers/:id} : delete the "id" fabricSeller.
     *
     * @param id the id of the fabricSeller to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fabric-sellers/{id}")
    public ResponseEntity<Void> deleteFabricSeller(@PathVariable Long id) {
        log.debug("REST request to delete FabricSeller : {}", id);
        fabricSellerService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
