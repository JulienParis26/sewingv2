package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.FabricEditor;
import com.mycompany.myapp.domain.enumeration.Editors;
import com.mycompany.myapp.domain.enumeration.Language;
import com.mycompany.myapp.repository.FabricEditorRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link FabricEditorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FabricEditorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PRINT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PRINT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Editors DEFAULT_EDITOR = Editors.BURDA;
    private static final Editors UPDATED_EDITOR = Editors.LA_MAISON_VICTOR;

    private static final Language DEFAULT_LANGUAGE = Language.FRENCH;
    private static final Language UPDATED_LANGUAGE = Language.ENGLISH;

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/fabric-editors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FabricEditorRepository fabricEditorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFabricEditorMockMvc;

    private FabricEditor fabricEditor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FabricEditor createEntity(EntityManager em) {
        FabricEditor fabricEditor = new FabricEditor()
            .name(DEFAULT_NAME)
            .printDate(DEFAULT_PRINT_DATE)
            .number(DEFAULT_NUMBER)
            .editor(DEFAULT_EDITOR)
            .language(DEFAULT_LANGUAGE)
            .price(DEFAULT_PRICE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return fabricEditor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FabricEditor createUpdatedEntity(EntityManager em) {
        FabricEditor fabricEditor = new FabricEditor()
            .name(UPDATED_NAME)
            .printDate(UPDATED_PRINT_DATE)
            .number(UPDATED_NUMBER)
            .editor(UPDATED_EDITOR)
            .language(UPDATED_LANGUAGE)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return fabricEditor;
    }

    @BeforeEach
    public void initTest() {
        fabricEditor = createEntity(em);
    }

    @Test
    @Transactional
    void createFabricEditor() throws Exception {
        int databaseSizeBeforeCreate = fabricEditorRepository.findAll().size();
        // Create the FabricEditor
        restFabricEditorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricEditor)))
            .andExpect(status().isCreated());

        // Validate the FabricEditor in the database
        List<FabricEditor> fabricEditorList = fabricEditorRepository.findAll();
        assertThat(fabricEditorList).hasSize(databaseSizeBeforeCreate + 1);
        FabricEditor testFabricEditor = fabricEditorList.get(fabricEditorList.size() - 1);
        assertThat(testFabricEditor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFabricEditor.getPrintDate()).isEqualTo(DEFAULT_PRINT_DATE);
        assertThat(testFabricEditor.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testFabricEditor.getEditor()).isEqualTo(DEFAULT_EDITOR);
        assertThat(testFabricEditor.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testFabricEditor.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testFabricEditor.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testFabricEditor.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createFabricEditorWithExistingId() throws Exception {
        // Create the FabricEditor with an existing ID
        fabricEditor.setId(1L);

        int databaseSizeBeforeCreate = fabricEditorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFabricEditorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricEditor)))
            .andExpect(status().isBadRequest());

        // Validate the FabricEditor in the database
        List<FabricEditor> fabricEditorList = fabricEditorRepository.findAll();
        assertThat(fabricEditorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFabricEditors() throws Exception {
        // Initialize the database
        fabricEditorRepository.saveAndFlush(fabricEditor);

        // Get all the fabricEditorList
        restFabricEditorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fabricEditor.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].printDate").value(hasItem(DEFAULT_PRINT_DATE.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].editor").value(hasItem(DEFAULT_EDITOR.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }

    @Test
    @Transactional
    void getFabricEditor() throws Exception {
        // Initialize the database
        fabricEditorRepository.saveAndFlush(fabricEditor);

        // Get the fabricEditor
        restFabricEditorMockMvc
            .perform(get(ENTITY_API_URL_ID, fabricEditor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fabricEditor.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.printDate").value(DEFAULT_PRINT_DATE.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.editor").value(DEFAULT_EDITOR.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    void getNonExistingFabricEditor() throws Exception {
        // Get the fabricEditor
        restFabricEditorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFabricEditor() throws Exception {
        // Initialize the database
        fabricEditorRepository.saveAndFlush(fabricEditor);

        int databaseSizeBeforeUpdate = fabricEditorRepository.findAll().size();

        // Update the fabricEditor
        FabricEditor updatedFabricEditor = fabricEditorRepository.findById(fabricEditor.getId()).get();
        // Disconnect from session so that the updates on updatedFabricEditor are not directly saved in db
        em.detach(updatedFabricEditor);
        updatedFabricEditor
            .name(UPDATED_NAME)
            .printDate(UPDATED_PRINT_DATE)
            .number(UPDATED_NUMBER)
            .editor(UPDATED_EDITOR)
            .language(UPDATED_LANGUAGE)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restFabricEditorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFabricEditor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFabricEditor))
            )
            .andExpect(status().isOk());

        // Validate the FabricEditor in the database
        List<FabricEditor> fabricEditorList = fabricEditorRepository.findAll();
        assertThat(fabricEditorList).hasSize(databaseSizeBeforeUpdate);
        FabricEditor testFabricEditor = fabricEditorList.get(fabricEditorList.size() - 1);
        assertThat(testFabricEditor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricEditor.getPrintDate()).isEqualTo(UPDATED_PRINT_DATE);
        assertThat(testFabricEditor.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testFabricEditor.getEditor()).isEqualTo(UPDATED_EDITOR);
        assertThat(testFabricEditor.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testFabricEditor.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testFabricEditor.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testFabricEditor.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFabricEditor() throws Exception {
        int databaseSizeBeforeUpdate = fabricEditorRepository.findAll().size();
        fabricEditor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricEditorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fabricEditor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabricEditor))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricEditor in the database
        List<FabricEditor> fabricEditorList = fabricEditorRepository.findAll();
        assertThat(fabricEditorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFabricEditor() throws Exception {
        int databaseSizeBeforeUpdate = fabricEditorRepository.findAll().size();
        fabricEditor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricEditorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabricEditor))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricEditor in the database
        List<FabricEditor> fabricEditorList = fabricEditorRepository.findAll();
        assertThat(fabricEditorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFabricEditor() throws Exception {
        int databaseSizeBeforeUpdate = fabricEditorRepository.findAll().size();
        fabricEditor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricEditorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabricEditor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FabricEditor in the database
        List<FabricEditor> fabricEditorList = fabricEditorRepository.findAll();
        assertThat(fabricEditorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFabricEditorWithPatch() throws Exception {
        // Initialize the database
        fabricEditorRepository.saveAndFlush(fabricEditor);

        int databaseSizeBeforeUpdate = fabricEditorRepository.findAll().size();

        // Update the fabricEditor using partial update
        FabricEditor partialUpdatedFabricEditor = new FabricEditor();
        partialUpdatedFabricEditor.setId(fabricEditor.getId());

        partialUpdatedFabricEditor
            .number(UPDATED_NUMBER)
            .editor(UPDATED_EDITOR)
            .language(UPDATED_LANGUAGE)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restFabricEditorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabricEditor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabricEditor))
            )
            .andExpect(status().isOk());

        // Validate the FabricEditor in the database
        List<FabricEditor> fabricEditorList = fabricEditorRepository.findAll();
        assertThat(fabricEditorList).hasSize(databaseSizeBeforeUpdate);
        FabricEditor testFabricEditor = fabricEditorList.get(fabricEditorList.size() - 1);
        assertThat(testFabricEditor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFabricEditor.getPrintDate()).isEqualTo(DEFAULT_PRINT_DATE);
        assertThat(testFabricEditor.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testFabricEditor.getEditor()).isEqualTo(UPDATED_EDITOR);
        assertThat(testFabricEditor.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testFabricEditor.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testFabricEditor.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testFabricEditor.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFabricEditorWithPatch() throws Exception {
        // Initialize the database
        fabricEditorRepository.saveAndFlush(fabricEditor);

        int databaseSizeBeforeUpdate = fabricEditorRepository.findAll().size();

        // Update the fabricEditor using partial update
        FabricEditor partialUpdatedFabricEditor = new FabricEditor();
        partialUpdatedFabricEditor.setId(fabricEditor.getId());

        partialUpdatedFabricEditor
            .name(UPDATED_NAME)
            .printDate(UPDATED_PRINT_DATE)
            .number(UPDATED_NUMBER)
            .editor(UPDATED_EDITOR)
            .language(UPDATED_LANGUAGE)
            .price(UPDATED_PRICE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restFabricEditorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabricEditor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabricEditor))
            )
            .andExpect(status().isOk());

        // Validate the FabricEditor in the database
        List<FabricEditor> fabricEditorList = fabricEditorRepository.findAll();
        assertThat(fabricEditorList).hasSize(databaseSizeBeforeUpdate);
        FabricEditor testFabricEditor = fabricEditorList.get(fabricEditorList.size() - 1);
        assertThat(testFabricEditor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabricEditor.getPrintDate()).isEqualTo(UPDATED_PRINT_DATE);
        assertThat(testFabricEditor.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testFabricEditor.getEditor()).isEqualTo(UPDATED_EDITOR);
        assertThat(testFabricEditor.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testFabricEditor.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testFabricEditor.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testFabricEditor.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFabricEditor() throws Exception {
        int databaseSizeBeforeUpdate = fabricEditorRepository.findAll().size();
        fabricEditor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricEditorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fabricEditor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricEditor))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricEditor in the database
        List<FabricEditor> fabricEditorList = fabricEditorRepository.findAll();
        assertThat(fabricEditorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFabricEditor() throws Exception {
        int databaseSizeBeforeUpdate = fabricEditorRepository.findAll().size();
        fabricEditor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricEditorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabricEditor))
            )
            .andExpect(status().isBadRequest());

        // Validate the FabricEditor in the database
        List<FabricEditor> fabricEditorList = fabricEditorRepository.findAll();
        assertThat(fabricEditorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFabricEditor() throws Exception {
        int databaseSizeBeforeUpdate = fabricEditorRepository.findAll().size();
        fabricEditor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricEditorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fabricEditor))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FabricEditor in the database
        List<FabricEditor> fabricEditorList = fabricEditorRepository.findAll();
        assertThat(fabricEditorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFabricEditor() throws Exception {
        // Initialize the database
        fabricEditorRepository.saveAndFlush(fabricEditor);

        int databaseSizeBeforeDelete = fabricEditorRepository.findAll().size();

        // Delete the fabricEditor
        restFabricEditorMockMvc
            .perform(delete(ENTITY_API_URL_ID, fabricEditor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FabricEditor> fabricEditorList = fabricEditorRepository.findAll();
        assertThat(fabricEditorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
