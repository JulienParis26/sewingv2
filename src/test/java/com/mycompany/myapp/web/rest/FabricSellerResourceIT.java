package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.FabricSeller;
import com.mycompany.myapp.repository.FabricSellerRepository;
import com.mycompany.myapp.service.FabricSellerService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FabricSellerResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FabricSellerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fabric-sellers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FabricSellerRepository fabricSellerRepository;

    @Mock
    private FabricSellerRepository fabricSellerRepositoryMock;

    @Mock
    private FabricSellerService fabricSellerServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFabricSellerMockMvc;

    private FabricSeller fabricSeller;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FabricSeller createEntity(EntityManager em) {
        FabricSeller fabricSeller = new FabricSeller().name(DEFAULT_NAME).webSite(DEFAULT_WEB_SITE).description(DEFAULT_DESCRIPTION);
        return fabricSeller;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FabricSeller createUpdatedEntity(EntityManager em) {
        FabricSeller fabricSeller = new FabricSeller().name(UPDATED_NAME).webSite(UPDATED_WEB_SITE).description(UPDATED_DESCRIPTION);
        return fabricSeller;
    }

    @BeforeEach
    public void initTest() {
        fabricSeller = createEntity(em);
    }

    @Test
    @Transactional
    void createFabricSeller() throws Exception {
        int databaseSizeBeforeCreate = fabricSellerRepository.findAll().size();
        // Create the FabricSeller
        restFabricSellerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricSeller)))
            .andExpect(status().isCreated());

        // Validate the FabricSeller in the database
        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeCreate + 1);
        FabricSeller testFabricSeller = fabricSellerList.get(fabricSellerList.size() - 1);
        assertThat(testFabricSeller.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFabricSeller.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
        assertThat(testFabricSeller.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createFabricSellerWithExistingId() throws Exception {
        // Create the FabricSeller with an existing ID
        fabricSeller.setId(1L);

        int databaseSizeBeforeCreate = fabricSellerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFabricSellerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricSeller)))
            .andExpect(status().isBadRequest());

        // Validate the FabricSeller in the database
        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricSellerRepository.findAll().size();
        // set the field null
        fabricSeller.setName(null);

        // Create the FabricSeller, which fails.

        restFabricSellerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricSeller)))
            .andExpect(status().isBadRequest());

        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFabricSellers() throws Exception {
        // Initialize the database
        fabricSellerRepository.saveAndFlush(fabricSeller);

        // Get all the fabricSellerList
        restFabricSellerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fabricSeller.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFabricSellersWithEagerRelationshipsIsEnabled() throws Exception {
        when(fabricSellerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFabricSellerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(fabricSellerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFabricSellersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(fabricSellerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFabricSellerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(fabricSellerRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFabricSeller() throws Exception {
        // Initialize the database
        fabricSellerRepository.saveAndFlush(fabricSeller);

        // Get the fabricSeller
        restFabricSellerMockMvc
            .perform(get(ENTITY_API_URL_ID, fabricSeller.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fabricSeller.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.webSite").value(DEFAULT_WEB_SITE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingFabricSeller() throws Exception {
        // Get the fabricSeller
        restFabricSellerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFabricSeller() throws Exception {
        // Initialize the database
        fabricSellerRepository.saveAndFlush(fabricSeller);

        int databaseSizeBeforeUpdate = fabricSellerRepository.findAll().size();

        // Update the fabricSeller
        FabricSeller updatedFabricSeller = fabricSellerRepository.findById(fabricSeller.getId()).get();
        // Disconnect from session so that the updates on updatedFabricSeller are not directly saved in db
        em.detach(updatedFabricSeller);
        updatedFabricSeller.name(UPDATED_NAME).webSite(UPDATED_WEB_SITE).description(UPDATED_DESCRIPTION);

        restFabricSellerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFabricSeller.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFabricSeller))
            )
            .andExpect(status().isOk());

        // Validate the FabricSeller in the database
        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeUpdate);
        FabricSeller testFabricSeller = fabricSellerList.get(fabricSellerList.size() - 1);
        assertThat(testFabricSeller.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricSeller.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testFabricSeller.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingFabricSeller() throws Exception {
        int databaseSizeBeforeUpdate = fabricSellerRepository.findAll().size();
        fabricSeller.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricSellerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fabricSeller.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabricSeller))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricSeller in the database
        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFabricSeller() throws Exception {
        int databaseSizeBeforeUpdate = fabricSellerRepository.findAll().size();
        fabricSeller.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricSellerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabricSeller))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricSeller in the database
        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFabricSeller() throws Exception {
        int databaseSizeBeforeUpdate = fabricSellerRepository.findAll().size();
        fabricSeller.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricSellerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricSeller)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FabricSeller in the database
        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFabricSellerWithPatch() throws Exception {
        // Initialize the database
        fabricSellerRepository.saveAndFlush(fabricSeller);

        int databaseSizeBeforeUpdate = fabricSellerRepository.findAll().size();

        // Update the fabricSeller using partial update
        FabricSeller partialUpdatedFabricSeller = new FabricSeller();
        partialUpdatedFabricSeller.setId(fabricSeller.getId());

        partialUpdatedFabricSeller.webSite(UPDATED_WEB_SITE).description(UPDATED_DESCRIPTION);

        restFabricSellerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabricSeller.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabricSeller))
            )
            .andExpect(status().isOk());

        // Validate the FabricSeller in the database
        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeUpdate);
        FabricSeller testFabricSeller = fabricSellerList.get(fabricSellerList.size() - 1);
        assertThat(testFabricSeller.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFabricSeller.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testFabricSeller.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateFabricSellerWithPatch() throws Exception {
        // Initialize the database
        fabricSellerRepository.saveAndFlush(fabricSeller);

        int databaseSizeBeforeUpdate = fabricSellerRepository.findAll().size();

        // Update the fabricSeller using partial update
        FabricSeller partialUpdatedFabricSeller = new FabricSeller();
        partialUpdatedFabricSeller.setId(fabricSeller.getId());

        partialUpdatedFabricSeller.name(UPDATED_NAME).webSite(UPDATED_WEB_SITE).description(UPDATED_DESCRIPTION);

        restFabricSellerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabricSeller.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabricSeller))
            )
            .andExpect(status().isOk());

        // Validate the FabricSeller in the database
        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeUpdate);
        FabricSeller testFabricSeller = fabricSellerList.get(fabricSellerList.size() - 1);
        assertThat(testFabricSeller.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricSeller.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testFabricSeller.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingFabricSeller() throws Exception {
        int databaseSizeBeforeUpdate = fabricSellerRepository.findAll().size();
        fabricSeller.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricSellerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fabricSeller.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricSeller))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricSeller in the database
        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFabricSeller() throws Exception {
        int databaseSizeBeforeUpdate = fabricSellerRepository.findAll().size();
        fabricSeller.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricSellerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricSeller))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricSeller in the database
        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFabricSeller() throws Exception {
        int databaseSizeBeforeUpdate = fabricSellerRepository.findAll().size();
        fabricSeller.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricSellerMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fabricSeller))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FabricSeller in the database
        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFabricSeller() throws Exception {
        // Initialize the database
        fabricSellerRepository.saveAndFlush(fabricSeller);

        int databaseSizeBeforeDelete = fabricSellerRepository.findAll().size();

        // Delete the fabricSeller
        restFabricSellerMockMvc
            .perform(delete(ENTITY_API_URL_ID, fabricSeller.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FabricSeller> fabricSellerList = fabricSellerRepository.findAll();
        assertThat(fabricSellerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
