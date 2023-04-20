package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Materials;
import com.mycompany.myapp.repository.MaterialsRepository;
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
 * Integration tests for the {@link MaterialsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MaterialsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/materials";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MaterialsRepository materialsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMaterialsMockMvc;

    private Materials materials;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materials createEntity(EntityManager em) {
        Materials materials = new Materials().name(DEFAULT_NAME).webSite(DEFAULT_WEB_SITE).description(DEFAULT_DESCRIPTION);
        return materials;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Materials createUpdatedEntity(EntityManager em) {
        Materials materials = new Materials().name(UPDATED_NAME).webSite(UPDATED_WEB_SITE).description(UPDATED_DESCRIPTION);
        return materials;
    }

    @BeforeEach
    public void initTest() {
        materials = createEntity(em);
    }

    @Test
    @Transactional
    void createMaterials() throws Exception {
        int databaseSizeBeforeCreate = materialsRepository.findAll().size();
        // Create the Materials
        restMaterialsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materials)))
            .andExpect(status().isCreated());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeCreate + 1);
        Materials testMaterials = materialsList.get(materialsList.size() - 1);
        assertThat(testMaterials.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMaterials.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
        assertThat(testMaterials.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createMaterialsWithExistingId() throws Exception {
        // Create the Materials with an existing ID
        materials.setId(1L);

        int databaseSizeBeforeCreate = materialsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMaterialsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materials)))
            .andExpect(status().isBadRequest());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = materialsRepository.findAll().size();
        // set the field null
        materials.setName(null);

        // Create the Materials, which fails.

        restMaterialsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materials)))
            .andExpect(status().isBadRequest());

        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMaterials() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get all the materialsList
        restMaterialsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(materials.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getMaterials() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        // Get the materials
        restMaterialsMockMvc
            .perform(get(ENTITY_API_URL_ID, materials.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(materials.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.webSite").value(DEFAULT_WEB_SITE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingMaterials() throws Exception {
        // Get the materials
        restMaterialsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMaterials() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();

        // Update the materials
        Materials updatedMaterials = materialsRepository.findById(materials.getId()).get();
        // Disconnect from session so that the updates on updatedMaterials are not directly saved in db
        em.detach(updatedMaterials);
        updatedMaterials.name(UPDATED_NAME).webSite(UPDATED_WEB_SITE).description(UPDATED_DESCRIPTION);

        restMaterialsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedMaterials.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMaterials))
            )
            .andExpect(status().isOk());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
        Materials testMaterials = materialsList.get(materialsList.size() - 1);
        assertThat(testMaterials.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMaterials.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testMaterials.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingMaterials() throws Exception {
        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();
        materials.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, materials.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materials))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMaterials() throws Exception {
        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();
        materials.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(materials))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMaterials() throws Exception {
        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();
        materials.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(materials)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMaterialsWithPatch() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();

        // Update the materials using partial update
        Materials partialUpdatedMaterials = new Materials();
        partialUpdatedMaterials.setId(materials.getId());

        partialUpdatedMaterials.name(UPDATED_NAME).webSite(UPDATED_WEB_SITE);

        restMaterialsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterials.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterials))
            )
            .andExpect(status().isOk());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
        Materials testMaterials = materialsList.get(materialsList.size() - 1);
        assertThat(testMaterials.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMaterials.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testMaterials.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateMaterialsWithPatch() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();

        // Update the materials using partial update
        Materials partialUpdatedMaterials = new Materials();
        partialUpdatedMaterials.setId(materials.getId());

        partialUpdatedMaterials.name(UPDATED_NAME).webSite(UPDATED_WEB_SITE).description(UPDATED_DESCRIPTION);

        restMaterialsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMaterials.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMaterials))
            )
            .andExpect(status().isOk());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
        Materials testMaterials = materialsList.get(materialsList.size() - 1);
        assertThat(testMaterials.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMaterials.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testMaterials.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingMaterials() throws Exception {
        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();
        materials.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMaterialsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, materials.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materials))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMaterials() throws Exception {
        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();
        materials.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(materials))
            )
            .andExpect(status().isBadRequest());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMaterials() throws Exception {
        int databaseSizeBeforeUpdate = materialsRepository.findAll().size();
        materials.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMaterialsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(materials))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Materials in the database
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMaterials() throws Exception {
        // Initialize the database
        materialsRepository.saveAndFlush(materials);

        int databaseSizeBeforeDelete = materialsRepository.findAll().size();

        // Delete the materials
        restMaterialsMockMvc
            .perform(delete(ENTITY_API_URL_ID, materials.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Materials> materialsList = materialsRepository.findAll();
        assertThat(materialsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
