package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.FabricUses;
import com.mycompany.myapp.repository.FabricUsesRepository;
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
 * Integration tests for the {@link FabricUsesResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FabricUsesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fabric-uses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FabricUsesRepository fabricUsesRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFabricUsesMockMvc;

    private FabricUses fabricUses;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FabricUses createEntity(EntityManager em) {
        FabricUses fabricUses = new FabricUses().name(DEFAULT_NAME).code(DEFAULT_CODE);
        return fabricUses;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FabricUses createUpdatedEntity(EntityManager em) {
        FabricUses fabricUses = new FabricUses().name(UPDATED_NAME).code(UPDATED_CODE);
        return fabricUses;
    }

    @BeforeEach
    public void initTest() {
        fabricUses = createEntity(em);
    }

    @Test
    @Transactional
    void createFabricUses() throws Exception {
        int databaseSizeBeforeCreate = fabricUsesRepository.findAll().size();
        // Create the FabricUses
        restFabricUsesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricUses)))
            .andExpect(status().isCreated());

        // Validate the FabricUses in the database
        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeCreate + 1);
        FabricUses testFabricUses = fabricUsesList.get(fabricUsesList.size() - 1);
        assertThat(testFabricUses.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFabricUses.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createFabricUsesWithExistingId() throws Exception {
        // Create the FabricUses with an existing ID
        fabricUses.setId(1L);

        int databaseSizeBeforeCreate = fabricUsesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFabricUsesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricUses)))
            .andExpect(status().isBadRequest());

        // Validate the FabricUses in the database
        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricUsesRepository.findAll().size();
        // set the field null
        fabricUses.setName(null);

        // Create the FabricUses, which fails.

        restFabricUsesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricUses)))
            .andExpect(status().isBadRequest());

        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricUsesRepository.findAll().size();
        // set the field null
        fabricUses.setCode(null);

        // Create the FabricUses, which fails.

        restFabricUsesMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricUses)))
            .andExpect(status().isBadRequest());

        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFabricUses() throws Exception {
        // Initialize the database
        fabricUsesRepository.saveAndFlush(fabricUses);

        // Get all the fabricUsesList
        restFabricUsesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fabricUses.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getFabricUses() throws Exception {
        // Initialize the database
        fabricUsesRepository.saveAndFlush(fabricUses);

        // Get the fabricUses
        restFabricUsesMockMvc
            .perform(get(ENTITY_API_URL_ID, fabricUses.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fabricUses.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingFabricUses() throws Exception {
        // Get the fabricUses
        restFabricUsesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFabricUses() throws Exception {
        // Initialize the database
        fabricUsesRepository.saveAndFlush(fabricUses);

        int databaseSizeBeforeUpdate = fabricUsesRepository.findAll().size();

        // Update the fabricUses
        FabricUses updatedFabricUses = fabricUsesRepository.findById(fabricUses.getId()).get();
        // Disconnect from session so that the updates on updatedFabricUses are not directly saved in db
        em.detach(updatedFabricUses);
        updatedFabricUses.name(UPDATED_NAME).code(UPDATED_CODE);

        restFabricUsesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFabricUses.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFabricUses))
            )
            .andExpect(status().isOk());

        // Validate the FabricUses in the database
        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeUpdate);
        FabricUses testFabricUses = fabricUsesList.get(fabricUsesList.size() - 1);
        assertThat(testFabricUses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricUses.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingFabricUses() throws Exception {
        int databaseSizeBeforeUpdate = fabricUsesRepository.findAll().size();
        fabricUses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricUsesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fabricUses.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabricUses))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricUses in the database
        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFabricUses() throws Exception {
        int databaseSizeBeforeUpdate = fabricUsesRepository.findAll().size();
        fabricUses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricUsesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabricUses))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricUses in the database
        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFabricUses() throws Exception {
        int databaseSizeBeforeUpdate = fabricUsesRepository.findAll().size();
        fabricUses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricUsesMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricUses)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FabricUses in the database
        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFabricUsesWithPatch() throws Exception {
        // Initialize the database
        fabricUsesRepository.saveAndFlush(fabricUses);

        int databaseSizeBeforeUpdate = fabricUsesRepository.findAll().size();

        // Update the fabricUses using partial update
        FabricUses partialUpdatedFabricUses = new FabricUses();
        partialUpdatedFabricUses.setId(fabricUses.getId());

        partialUpdatedFabricUses.code(UPDATED_CODE);

        restFabricUsesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabricUses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabricUses))
            )
            .andExpect(status().isOk());

        // Validate the FabricUses in the database
        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeUpdate);
        FabricUses testFabricUses = fabricUsesList.get(fabricUsesList.size() - 1);
        assertThat(testFabricUses.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFabricUses.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void fullUpdateFabricUsesWithPatch() throws Exception {
        // Initialize the database
        fabricUsesRepository.saveAndFlush(fabricUses);

        int databaseSizeBeforeUpdate = fabricUsesRepository.findAll().size();

        // Update the fabricUses using partial update
        FabricUses partialUpdatedFabricUses = new FabricUses();
        partialUpdatedFabricUses.setId(fabricUses.getId());

        partialUpdatedFabricUses.name(UPDATED_NAME).code(UPDATED_CODE);

        restFabricUsesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabricUses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabricUses))
            )
            .andExpect(status().isOk());

        // Validate the FabricUses in the database
        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeUpdate);
        FabricUses testFabricUses = fabricUsesList.get(fabricUsesList.size() - 1);
        assertThat(testFabricUses.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricUses.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingFabricUses() throws Exception {
        int databaseSizeBeforeUpdate = fabricUsesRepository.findAll().size();
        fabricUses.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricUsesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fabricUses.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricUses))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricUses in the database
        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFabricUses() throws Exception {
        int databaseSizeBeforeUpdate = fabricUsesRepository.findAll().size();
        fabricUses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricUsesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricUses))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricUses in the database
        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFabricUses() throws Exception {
        int databaseSizeBeforeUpdate = fabricUsesRepository.findAll().size();
        fabricUses.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricUsesMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fabricUses))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FabricUses in the database
        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFabricUses() throws Exception {
        // Initialize the database
        fabricUsesRepository.saveAndFlush(fabricUses);

        int databaseSizeBeforeDelete = fabricUsesRepository.findAll().size();

        // Delete the fabricUses
        restFabricUsesMockMvc
            .perform(delete(ENTITY_API_URL_ID, fabricUses.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FabricUses> fabricUsesList = fabricUsesRepository.findAll();
        assertThat(fabricUsesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
