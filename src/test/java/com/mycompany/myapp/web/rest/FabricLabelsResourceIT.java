package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.FabricLabels;
import com.mycompany.myapp.repository.FabricLabelsRepository;
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
 * Integration tests for the {@link FabricLabelsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FabricLabelsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fabric-labels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FabricLabelsRepository fabricLabelsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFabricLabelsMockMvc;

    private FabricLabels fabricLabels;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FabricLabels createEntity(EntityManager em) {
        FabricLabels fabricLabels = new FabricLabels().name(DEFAULT_NAME).code(DEFAULT_CODE);
        return fabricLabels;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FabricLabels createUpdatedEntity(EntityManager em) {
        FabricLabels fabricLabels = new FabricLabels().name(UPDATED_NAME).code(UPDATED_CODE);
        return fabricLabels;
    }

    @BeforeEach
    public void initTest() {
        fabricLabels = createEntity(em);
    }

    @Test
    @Transactional
    void createFabricLabels() throws Exception {
        int databaseSizeBeforeCreate = fabricLabelsRepository.findAll().size();
        // Create the FabricLabels
        restFabricLabelsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricLabels)))
            .andExpect(status().isCreated());

        // Validate the FabricLabels in the database
        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeCreate + 1);
        FabricLabels testFabricLabels = fabricLabelsList.get(fabricLabelsList.size() - 1);
        assertThat(testFabricLabels.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFabricLabels.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createFabricLabelsWithExistingId() throws Exception {
        // Create the FabricLabels with an existing ID
        fabricLabels.setId(1L);

        int databaseSizeBeforeCreate = fabricLabelsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFabricLabelsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricLabels)))
            .andExpect(status().isBadRequest());

        // Validate the FabricLabels in the database
        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricLabelsRepository.findAll().size();
        // set the field null
        fabricLabels.setName(null);

        // Create the FabricLabels, which fails.

        restFabricLabelsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricLabels)))
            .andExpect(status().isBadRequest());

        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricLabelsRepository.findAll().size();
        // set the field null
        fabricLabels.setCode(null);

        // Create the FabricLabels, which fails.

        restFabricLabelsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricLabels)))
            .andExpect(status().isBadRequest());

        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFabricLabels() throws Exception {
        // Initialize the database
        fabricLabelsRepository.saveAndFlush(fabricLabels);

        // Get all the fabricLabelsList
        restFabricLabelsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fabricLabels.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getFabricLabels() throws Exception {
        // Initialize the database
        fabricLabelsRepository.saveAndFlush(fabricLabels);

        // Get the fabricLabels
        restFabricLabelsMockMvc
            .perform(get(ENTITY_API_URL_ID, fabricLabels.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fabricLabels.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingFabricLabels() throws Exception {
        // Get the fabricLabels
        restFabricLabelsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFabricLabels() throws Exception {
        // Initialize the database
        fabricLabelsRepository.saveAndFlush(fabricLabels);

        int databaseSizeBeforeUpdate = fabricLabelsRepository.findAll().size();

        // Update the fabricLabels
        FabricLabels updatedFabricLabels = fabricLabelsRepository.findById(fabricLabels.getId()).get();
        // Disconnect from session so that the updates on updatedFabricLabels are not directly saved in db
        em.detach(updatedFabricLabels);
        updatedFabricLabels.name(UPDATED_NAME).code(UPDATED_CODE);

        restFabricLabelsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFabricLabels.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFabricLabels))
            )
            .andExpect(status().isOk());

        // Validate the FabricLabels in the database
        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeUpdate);
        FabricLabels testFabricLabels = fabricLabelsList.get(fabricLabelsList.size() - 1);
        assertThat(testFabricLabels.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricLabels.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingFabricLabels() throws Exception {
        int databaseSizeBeforeUpdate = fabricLabelsRepository.findAll().size();
        fabricLabels.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricLabelsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fabricLabels.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabricLabels))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricLabels in the database
        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFabricLabels() throws Exception {
        int databaseSizeBeforeUpdate = fabricLabelsRepository.findAll().size();
        fabricLabels.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricLabelsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabricLabels))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricLabels in the database
        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFabricLabels() throws Exception {
        int databaseSizeBeforeUpdate = fabricLabelsRepository.findAll().size();
        fabricLabels.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricLabelsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricLabels)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FabricLabels in the database
        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFabricLabelsWithPatch() throws Exception {
        // Initialize the database
        fabricLabelsRepository.saveAndFlush(fabricLabels);

        int databaseSizeBeforeUpdate = fabricLabelsRepository.findAll().size();

        // Update the fabricLabels using partial update
        FabricLabels partialUpdatedFabricLabels = new FabricLabels();
        partialUpdatedFabricLabels.setId(fabricLabels.getId());

        partialUpdatedFabricLabels.name(UPDATED_NAME);

        restFabricLabelsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabricLabels.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabricLabels))
            )
            .andExpect(status().isOk());

        // Validate the FabricLabels in the database
        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeUpdate);
        FabricLabels testFabricLabels = fabricLabelsList.get(fabricLabelsList.size() - 1);
        assertThat(testFabricLabels.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricLabels.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void fullUpdateFabricLabelsWithPatch() throws Exception {
        // Initialize the database
        fabricLabelsRepository.saveAndFlush(fabricLabels);

        int databaseSizeBeforeUpdate = fabricLabelsRepository.findAll().size();

        // Update the fabricLabels using partial update
        FabricLabels partialUpdatedFabricLabels = new FabricLabels();
        partialUpdatedFabricLabels.setId(fabricLabels.getId());

        partialUpdatedFabricLabels.name(UPDATED_NAME).code(UPDATED_CODE);

        restFabricLabelsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabricLabels.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabricLabels))
            )
            .andExpect(status().isOk());

        // Validate the FabricLabels in the database
        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeUpdate);
        FabricLabels testFabricLabels = fabricLabelsList.get(fabricLabelsList.size() - 1);
        assertThat(testFabricLabels.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricLabels.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingFabricLabels() throws Exception {
        int databaseSizeBeforeUpdate = fabricLabelsRepository.findAll().size();
        fabricLabels.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricLabelsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fabricLabels.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricLabels))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricLabels in the database
        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFabricLabels() throws Exception {
        int databaseSizeBeforeUpdate = fabricLabelsRepository.findAll().size();
        fabricLabels.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricLabelsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricLabels))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricLabels in the database
        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFabricLabels() throws Exception {
        int databaseSizeBeforeUpdate = fabricLabelsRepository.findAll().size();
        fabricLabels.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricLabelsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fabricLabels))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FabricLabels in the database
        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFabricLabels() throws Exception {
        // Initialize the database
        fabricLabelsRepository.saveAndFlush(fabricLabels);

        int databaseSizeBeforeDelete = fabricLabelsRepository.findAll().size();

        // Delete the fabricLabels
        restFabricLabelsMockMvc
            .perform(delete(ENTITY_API_URL_ID, fabricLabels.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FabricLabels> fabricLabelsList = fabricLabelsRepository.findAll();
        assertThat(fabricLabelsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
