package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.FabricMaintenance;
import com.mycompany.myapp.repository.FabricMaintenanceRepository;
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
 * Integration tests for the {@link FabricMaintenanceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FabricMaintenanceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fabric-maintenances";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FabricMaintenanceRepository fabricMaintenanceRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFabricMaintenanceMockMvc;

    private FabricMaintenance fabricMaintenance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FabricMaintenance createEntity(EntityManager em) {
        FabricMaintenance fabricMaintenance = new FabricMaintenance().name(DEFAULT_NAME).code(DEFAULT_CODE);
        return fabricMaintenance;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FabricMaintenance createUpdatedEntity(EntityManager em) {
        FabricMaintenance fabricMaintenance = new FabricMaintenance().name(UPDATED_NAME).code(UPDATED_CODE);
        return fabricMaintenance;
    }

    @BeforeEach
    public void initTest() {
        fabricMaintenance = createEntity(em);
    }

    @Test
    @Transactional
    void createFabricMaintenance() throws Exception {
        int databaseSizeBeforeCreate = fabricMaintenanceRepository.findAll().size();
        // Create the FabricMaintenance
        restFabricMaintenanceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricMaintenance))
            )
            .andExpect(status().isCreated());

        // Validate the FabricMaintenance in the database
        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeCreate + 1);
        FabricMaintenance testFabricMaintenance = fabricMaintenanceList.get(fabricMaintenanceList.size() - 1);
        assertThat(testFabricMaintenance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFabricMaintenance.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    void createFabricMaintenanceWithExistingId() throws Exception {
        // Create the FabricMaintenance with an existing ID
        fabricMaintenance.setId(1L);

        int databaseSizeBeforeCreate = fabricMaintenanceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFabricMaintenanceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricMaintenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricMaintenance in the database
        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricMaintenanceRepository.findAll().size();
        // set the field null
        fabricMaintenance.setName(null);

        // Create the FabricMaintenance, which fails.

        restFabricMaintenanceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricMaintenance))
            )
            .andExpect(status().isBadRequest());

        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricMaintenanceRepository.findAll().size();
        // set the field null
        fabricMaintenance.setCode(null);

        // Create the FabricMaintenance, which fails.

        restFabricMaintenanceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricMaintenance))
            )
            .andExpect(status().isBadRequest());

        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFabricMaintenances() throws Exception {
        // Initialize the database
        fabricMaintenanceRepository.saveAndFlush(fabricMaintenance);

        // Get all the fabricMaintenanceList
        restFabricMaintenanceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fabricMaintenance.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }

    @Test
    @Transactional
    void getFabricMaintenance() throws Exception {
        // Initialize the database
        fabricMaintenanceRepository.saveAndFlush(fabricMaintenance);

        // Get the fabricMaintenance
        restFabricMaintenanceMockMvc
            .perform(get(ENTITY_API_URL_ID, fabricMaintenance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fabricMaintenance.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }

    @Test
    @Transactional
    void getNonExistingFabricMaintenance() throws Exception {
        // Get the fabricMaintenance
        restFabricMaintenanceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFabricMaintenance() throws Exception {
        // Initialize the database
        fabricMaintenanceRepository.saveAndFlush(fabricMaintenance);

        int databaseSizeBeforeUpdate = fabricMaintenanceRepository.findAll().size();

        // Update the fabricMaintenance
        FabricMaintenance updatedFabricMaintenance = fabricMaintenanceRepository.findById(fabricMaintenance.getId()).get();
        // Disconnect from session so that the updates on updatedFabricMaintenance are not directly saved in db
        em.detach(updatedFabricMaintenance);
        updatedFabricMaintenance.name(UPDATED_NAME).code(UPDATED_CODE);

        restFabricMaintenanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFabricMaintenance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFabricMaintenance))
            )
            .andExpect(status().isOk());

        // Validate the FabricMaintenance in the database
        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeUpdate);
        FabricMaintenance testFabricMaintenance = fabricMaintenanceList.get(fabricMaintenanceList.size() - 1);
        assertThat(testFabricMaintenance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricMaintenance.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void putNonExistingFabricMaintenance() throws Exception {
        int databaseSizeBeforeUpdate = fabricMaintenanceRepository.findAll().size();
        fabricMaintenance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricMaintenanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fabricMaintenance.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabricMaintenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricMaintenance in the database
        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFabricMaintenance() throws Exception {
        int databaseSizeBeforeUpdate = fabricMaintenanceRepository.findAll().size();
        fabricMaintenance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricMaintenanceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabricMaintenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricMaintenance in the database
        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFabricMaintenance() throws Exception {
        int databaseSizeBeforeUpdate = fabricMaintenanceRepository.findAll().size();
        fabricMaintenance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricMaintenanceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricMaintenance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FabricMaintenance in the database
        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFabricMaintenanceWithPatch() throws Exception {
        // Initialize the database
        fabricMaintenanceRepository.saveAndFlush(fabricMaintenance);

        int databaseSizeBeforeUpdate = fabricMaintenanceRepository.findAll().size();

        // Update the fabricMaintenance using partial update
        FabricMaintenance partialUpdatedFabricMaintenance = new FabricMaintenance();
        partialUpdatedFabricMaintenance.setId(fabricMaintenance.getId());

        partialUpdatedFabricMaintenance.code(UPDATED_CODE);

        restFabricMaintenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabricMaintenance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabricMaintenance))
            )
            .andExpect(status().isOk());

        // Validate the FabricMaintenance in the database
        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeUpdate);
        FabricMaintenance testFabricMaintenance = fabricMaintenanceList.get(fabricMaintenanceList.size() - 1);
        assertThat(testFabricMaintenance.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFabricMaintenance.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void fullUpdateFabricMaintenanceWithPatch() throws Exception {
        // Initialize the database
        fabricMaintenanceRepository.saveAndFlush(fabricMaintenance);

        int databaseSizeBeforeUpdate = fabricMaintenanceRepository.findAll().size();

        // Update the fabricMaintenance using partial update
        FabricMaintenance partialUpdatedFabricMaintenance = new FabricMaintenance();
        partialUpdatedFabricMaintenance.setId(fabricMaintenance.getId());

        partialUpdatedFabricMaintenance.name(UPDATED_NAME).code(UPDATED_CODE);

        restFabricMaintenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabricMaintenance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabricMaintenance))
            )
            .andExpect(status().isOk());

        // Validate the FabricMaintenance in the database
        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeUpdate);
        FabricMaintenance testFabricMaintenance = fabricMaintenanceList.get(fabricMaintenanceList.size() - 1);
        assertThat(testFabricMaintenance.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricMaintenance.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    void patchNonExistingFabricMaintenance() throws Exception {
        int databaseSizeBeforeUpdate = fabricMaintenanceRepository.findAll().size();
        fabricMaintenance.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricMaintenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fabricMaintenance.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricMaintenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricMaintenance in the database
        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFabricMaintenance() throws Exception {
        int databaseSizeBeforeUpdate = fabricMaintenanceRepository.findAll().size();
        fabricMaintenance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricMaintenanceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricMaintenance))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricMaintenance in the database
        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFabricMaintenance() throws Exception {
        int databaseSizeBeforeUpdate = fabricMaintenanceRepository.findAll().size();
        fabricMaintenance.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricMaintenanceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricMaintenance))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FabricMaintenance in the database
        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFabricMaintenance() throws Exception {
        // Initialize the database
        fabricMaintenanceRepository.saveAndFlush(fabricMaintenance);

        int databaseSizeBeforeDelete = fabricMaintenanceRepository.findAll().size();

        // Delete the fabricMaintenance
        restFabricMaintenanceMockMvc
            .perform(delete(ENTITY_API_URL_ID, fabricMaintenance.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FabricMaintenance> fabricMaintenanceList = fabricMaintenanceRepository.findAll();
        assertThat(fabricMaintenanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
