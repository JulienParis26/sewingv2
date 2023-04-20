package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.FabricTypes;
import com.mycompany.myapp.repository.FabricTypesRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FabricTypesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FabricTypesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fabric-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FabricTypesRepository fabricTypesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFabricTypesMockMvc;

    private FabricTypes fabricTypes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FabricTypes createEntity(EntityManager em) {
        FabricTypes fabricTypes = new FabricTypes().name(DEFAULT_NAME).code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return fabricTypes;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FabricTypes createUpdatedEntity(EntityManager em) {
        FabricTypes fabricTypes = new FabricTypes().name(UPDATED_NAME).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return fabricTypes;
    }

    @BeforeEach
    public void initTest() {
        fabricTypes = createEntity(em);
    }

    @Test
    @Transactional
    void createFabricTypes() throws Exception {
        int databaseSizeBeforeCreate = fabricTypesRepository.findAll().size();
        // Create the FabricTypes
        restFabricTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricTypes)))
            .andExpect(status().isCreated());

        // Validate the FabricTypes in the database
        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeCreate + 1);
        FabricTypes testFabricTypes = fabricTypesList.get(fabricTypesList.size() - 1);
        assertThat(testFabricTypes.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFabricTypes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFabricTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createFabricTypesWithExistingId() throws Exception {
        // Create the FabricTypes with an existing ID
        fabricTypes.setId(1L);

        int databaseSizeBeforeCreate = fabricTypesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFabricTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricTypes)))
            .andExpect(status().isBadRequest());

        // Validate the FabricTypes in the database
        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricTypesRepository.findAll().size();
        // set the field null
        fabricTypes.setName(null);

        // Create the FabricTypes, which fails.

        restFabricTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricTypes)))
            .andExpect(status().isBadRequest());

        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricTypesRepository.findAll().size();
        // set the field null
        fabricTypes.setCode(null);

        // Create the FabricTypes, which fails.

        restFabricTypesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricTypes)))
            .andExpect(status().isBadRequest());

        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFabricTypes() throws Exception {
        // Initialize the database
        fabricTypesRepository.saveAndFlush(fabricTypes);

        // Get all the fabricTypesList
        restFabricTypesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fabricTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getFabricTypes() throws Exception {
        // Initialize the database
        fabricTypesRepository.saveAndFlush(fabricTypes);

        // Get the fabricTypes
        restFabricTypesMockMvc
            .perform(get(ENTITY_API_URL_ID, fabricTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fabricTypes.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingFabricTypes() throws Exception {
        // Get the fabricTypes
        restFabricTypesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFabricTypes() throws Exception {
        // Initialize the database
        fabricTypesRepository.saveAndFlush(fabricTypes);

        int databaseSizeBeforeUpdate = fabricTypesRepository.findAll().size();

        // Update the fabricTypes
        FabricTypes updatedFabricTypes = fabricTypesRepository.findById(fabricTypes.getId()).get();
        // Disconnect from session so that the updates on updatedFabricTypes are not directly saved in db
        em.detach(updatedFabricTypes);
        updatedFabricTypes.name(UPDATED_NAME).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restFabricTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFabricTypes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFabricTypes))
            )
            .andExpect(status().isOk());

        // Validate the FabricTypes in the database
        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeUpdate);
        FabricTypes testFabricTypes = fabricTypesList.get(fabricTypesList.size() - 1);
        assertThat(testFabricTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricTypes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFabricTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingFabricTypes() throws Exception {
        int databaseSizeBeforeUpdate = fabricTypesRepository.findAll().size();
        fabricTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fabricTypes.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabricTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricTypes in the database
        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFabricTypes() throws Exception {
        int databaseSizeBeforeUpdate = fabricTypesRepository.findAll().size();
        fabricTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricTypesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabricTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricTypes in the database
        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFabricTypes() throws Exception {
        int databaseSizeBeforeUpdate = fabricTypesRepository.findAll().size();
        fabricTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricTypesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricTypes)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FabricTypes in the database
        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFabricTypesWithPatch() throws Exception {
        // Initialize the database
        fabricTypesRepository.saveAndFlush(fabricTypes);

        int databaseSizeBeforeUpdate = fabricTypesRepository.findAll().size();

        // Update the fabricTypes using partial update
        FabricTypes partialUpdatedFabricTypes = new FabricTypes();
        partialUpdatedFabricTypes.setId(fabricTypes.getId());

        partialUpdatedFabricTypes.name(UPDATED_NAME);

        restFabricTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabricTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabricTypes))
            )
            .andExpect(status().isOk());

        // Validate the FabricTypes in the database
        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeUpdate);
        FabricTypes testFabricTypes = fabricTypesList.get(fabricTypesList.size() - 1);
        assertThat(testFabricTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricTypes.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testFabricTypes.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateFabricTypesWithPatch() throws Exception {
        // Initialize the database
        fabricTypesRepository.saveAndFlush(fabricTypes);

        int databaseSizeBeforeUpdate = fabricTypesRepository.findAll().size();

        // Update the fabricTypes using partial update
        FabricTypes partialUpdatedFabricTypes = new FabricTypes();
        partialUpdatedFabricTypes.setId(fabricTypes.getId());

        partialUpdatedFabricTypes.name(UPDATED_NAME).code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restFabricTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabricTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabricTypes))
            )
            .andExpect(status().isOk());

        // Validate the FabricTypes in the database
        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeUpdate);
        FabricTypes testFabricTypes = fabricTypesList.get(fabricTypesList.size() - 1);
        assertThat(testFabricTypes.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricTypes.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testFabricTypes.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingFabricTypes() throws Exception {
        int databaseSizeBeforeUpdate = fabricTypesRepository.findAll().size();
        fabricTypes.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fabricTypes.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricTypes in the database
        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFabricTypes() throws Exception {
        int databaseSizeBeforeUpdate = fabricTypesRepository.findAll().size();
        fabricTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricTypesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricTypes))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricTypes in the database
        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFabricTypes() throws Exception {
        int databaseSizeBeforeUpdate = fabricTypesRepository.findAll().size();
        fabricTypes.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricTypesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fabricTypes))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FabricTypes in the database
        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFabricTypes() throws Exception {
        // Initialize the database
        fabricTypesRepository.saveAndFlush(fabricTypes);

        int databaseSizeBeforeDelete = fabricTypesRepository.findAll().size();

        // Delete the fabricTypes
        restFabricTypesMockMvc
            .perform(delete(ENTITY_API_URL_ID, fabricTypes.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FabricTypes> fabricTypesList = fabricTypesRepository.findAll();
        assertThat(fabricTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
