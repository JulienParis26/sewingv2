package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Patron;
import com.mycompany.myapp.domain.enumeration.Category;
import com.mycompany.myapp.domain.enumeration.DifficultLevel;
import com.mycompany.myapp.domain.enumeration.PatronType;
import com.mycompany.myapp.domain.enumeration.Qualification;
import com.mycompany.myapp.repository.PatronRepository;
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
 * Integration tests for the {@link PatronResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PatronResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final PatronType DEFAULT_TYPE = PatronType.PAPER;
    private static final PatronType UPDATED_TYPE = PatronType.PDF;

    private static final Category DEFAULT_SEXE = Category.KIDS;
    private static final Category UPDATED_SEXE = Category.MAN;

    private static final LocalDate DEFAULT_BUY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BUY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PUBLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CREATOR = "AAAAAAAAAA";
    private static final String UPDATED_CREATOR = "BBBBBBBBBB";

    private static final DifficultLevel DEFAULT_DIFFICULT_LEVEL = DifficultLevel.BEGINNER;
    private static final DifficultLevel UPDATED_DIFFICULT_LEVEL = DifficultLevel.INTERMEDIATE;

    private static final Qualification DEFAULT_FABRIC_QUALIFICATION = Qualification.SHORT;
    private static final Qualification UPDATED_FABRIC_QUALIFICATION = Qualification.EXTENSIBLE;

    private static final String DEFAULT_REQUIRED_FOOTAGE = "AAAAAAAAAA";
    private static final String UPDATED_REQUIRED_FOOTAGE = "BBBBBBBBBB";

    private static final String DEFAULT_REQUIRED_LAIZE = "AAAAAAAAAA";
    private static final String UPDATED_REQUIRED_LAIZE = "BBBBBBBBBB";

    private static final String DEFAULT_CLOTHING_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CLOTHING_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_PRICE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_PICTURE_TECHNICAL_DRAWING = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PICTURE_TECHNICAL_DRAWING = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_PICTURE_TECHNICAL_DRAWING_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PICTURE_TECHNICAL_DRAWING_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CARRIED_PICTURE_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CARRIED_PICTURE_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CARRIED_PICTURE_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CARRIED_PICTURE_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CARRIED_PICTURE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CARRIED_PICTURE_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CARRIED_PICTURE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CARRIED_PICTURE_2_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/patrons";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PatronRepository patronRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatronMockMvc;

    private Patron patron;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patron createEntity(EntityManager em) {
        Patron patron = new Patron()
            .name(DEFAULT_NAME)
            .ref(DEFAULT_REF)
            .type(DEFAULT_TYPE)
            .sexe(DEFAULT_SEXE)
            .buyDate(DEFAULT_BUY_DATE)
            .publicationDate(DEFAULT_PUBLICATION_DATE)
            .creator(DEFAULT_CREATOR)
            .difficultLevel(DEFAULT_DIFFICULT_LEVEL)
            .fabricQualification(DEFAULT_FABRIC_QUALIFICATION)
            .requiredFootage(DEFAULT_REQUIRED_FOOTAGE)
            .requiredLaize(DEFAULT_REQUIRED_LAIZE)
            .clothingType(DEFAULT_CLOTHING_TYPE)
            .price(DEFAULT_PRICE)
            .pictureTechnicalDrawing(DEFAULT_PICTURE_TECHNICAL_DRAWING)
            .pictureTechnicalDrawingContentType(DEFAULT_PICTURE_TECHNICAL_DRAWING_CONTENT_TYPE)
            .carriedPicture1(DEFAULT_CARRIED_PICTURE_1)
            .carriedPicture1ContentType(DEFAULT_CARRIED_PICTURE_1_CONTENT_TYPE)
            .carriedPicture2(DEFAULT_CARRIED_PICTURE_2)
            .carriedPicture2ContentType(DEFAULT_CARRIED_PICTURE_2_CONTENT_TYPE);
        return patron;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patron createUpdatedEntity(EntityManager em) {
        Patron patron = new Patron()
            .name(UPDATED_NAME)
            .ref(UPDATED_REF)
            .type(UPDATED_TYPE)
            .sexe(UPDATED_SEXE)
            .buyDate(UPDATED_BUY_DATE)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .creator(UPDATED_CREATOR)
            .difficultLevel(UPDATED_DIFFICULT_LEVEL)
            .fabricQualification(UPDATED_FABRIC_QUALIFICATION)
            .requiredFootage(UPDATED_REQUIRED_FOOTAGE)
            .requiredLaize(UPDATED_REQUIRED_LAIZE)
            .clothingType(UPDATED_CLOTHING_TYPE)
            .price(UPDATED_PRICE)
            .pictureTechnicalDrawing(UPDATED_PICTURE_TECHNICAL_DRAWING)
            .pictureTechnicalDrawingContentType(UPDATED_PICTURE_TECHNICAL_DRAWING_CONTENT_TYPE)
            .carriedPicture1(UPDATED_CARRIED_PICTURE_1)
            .carriedPicture1ContentType(UPDATED_CARRIED_PICTURE_1_CONTENT_TYPE)
            .carriedPicture2(UPDATED_CARRIED_PICTURE_2)
            .carriedPicture2ContentType(UPDATED_CARRIED_PICTURE_2_CONTENT_TYPE);
        return patron;
    }

    @BeforeEach
    public void initTest() {
        patron = createEntity(em);
    }

    @Test
    @Transactional
    void createPatron() throws Exception {
        int databaseSizeBeforeCreate = patronRepository.findAll().size();
        // Create the Patron
        restPatronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isCreated());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeCreate + 1);
        Patron testPatron = patronList.get(patronList.size() - 1);
        assertThat(testPatron.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPatron.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testPatron.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPatron.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testPatron.getBuyDate()).isEqualTo(DEFAULT_BUY_DATE);
        assertThat(testPatron.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testPatron.getCreator()).isEqualTo(DEFAULT_CREATOR);
        assertThat(testPatron.getDifficultLevel()).isEqualTo(DEFAULT_DIFFICULT_LEVEL);
        assertThat(testPatron.getFabricQualification()).isEqualTo(DEFAULT_FABRIC_QUALIFICATION);
        assertThat(testPatron.getRequiredFootage()).isEqualTo(DEFAULT_REQUIRED_FOOTAGE);
        assertThat(testPatron.getRequiredLaize()).isEqualTo(DEFAULT_REQUIRED_LAIZE);
        assertThat(testPatron.getClothingType()).isEqualTo(DEFAULT_CLOTHING_TYPE);
        assertThat(testPatron.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPatron.getPictureTechnicalDrawing()).isEqualTo(DEFAULT_PICTURE_TECHNICAL_DRAWING);
        assertThat(testPatron.getPictureTechnicalDrawingContentType()).isEqualTo(DEFAULT_PICTURE_TECHNICAL_DRAWING_CONTENT_TYPE);
        assertThat(testPatron.getCarriedPicture1()).isEqualTo(DEFAULT_CARRIED_PICTURE_1);
        assertThat(testPatron.getCarriedPicture1ContentType()).isEqualTo(DEFAULT_CARRIED_PICTURE_1_CONTENT_TYPE);
        assertThat(testPatron.getCarriedPicture2()).isEqualTo(DEFAULT_CARRIED_PICTURE_2);
        assertThat(testPatron.getCarriedPicture2ContentType()).isEqualTo(DEFAULT_CARRIED_PICTURE_2_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createPatronWithExistingId() throws Exception {
        // Create the Patron with an existing ID
        patron.setId(1L);

        int databaseSizeBeforeCreate = patronRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = patronRepository.findAll().size();
        // set the field null
        patron.setName(null);

        // Create the Patron, which fails.

        restPatronMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isBadRequest());

        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPatrons() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        // Get all the patronList
        restPatronMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patron.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].buyDate").value(hasItem(DEFAULT_BUY_DATE.toString())))
            .andExpect(jsonPath("$.[*].publicationDate").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].creator").value(hasItem(DEFAULT_CREATOR)))
            .andExpect(jsonPath("$.[*].difficultLevel").value(hasItem(DEFAULT_DIFFICULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].fabricQualification").value(hasItem(DEFAULT_FABRIC_QUALIFICATION.toString())))
            .andExpect(jsonPath("$.[*].requiredFootage").value(hasItem(DEFAULT_REQUIRED_FOOTAGE)))
            .andExpect(jsonPath("$.[*].requiredLaize").value(hasItem(DEFAULT_REQUIRED_LAIZE)))
            .andExpect(jsonPath("$.[*].clothingType").value(hasItem(DEFAULT_CLOTHING_TYPE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].pictureTechnicalDrawingContentType").value(hasItem(DEFAULT_PICTURE_TECHNICAL_DRAWING_CONTENT_TYPE)))
            .andExpect(
                jsonPath("$.[*].pictureTechnicalDrawing").value(hasItem(Base64Utils.encodeToString(DEFAULT_PICTURE_TECHNICAL_DRAWING)))
            )
            .andExpect(jsonPath("$.[*].carriedPicture1ContentType").value(hasItem(DEFAULT_CARRIED_PICTURE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].carriedPicture1").value(hasItem(Base64Utils.encodeToString(DEFAULT_CARRIED_PICTURE_1))))
            .andExpect(jsonPath("$.[*].carriedPicture2ContentType").value(hasItem(DEFAULT_CARRIED_PICTURE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].carriedPicture2").value(hasItem(Base64Utils.encodeToString(DEFAULT_CARRIED_PICTURE_2))));
    }

    @Test
    @Transactional
    void getPatron() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        // Get the patron
        restPatronMockMvc
            .perform(get(ENTITY_API_URL_ID, patron.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patron.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.buyDate").value(DEFAULT_BUY_DATE.toString()))
            .andExpect(jsonPath("$.publicationDate").value(DEFAULT_PUBLICATION_DATE.toString()))
            .andExpect(jsonPath("$.creator").value(DEFAULT_CREATOR))
            .andExpect(jsonPath("$.difficultLevel").value(DEFAULT_DIFFICULT_LEVEL.toString()))
            .andExpect(jsonPath("$.fabricQualification").value(DEFAULT_FABRIC_QUALIFICATION.toString()))
            .andExpect(jsonPath("$.requiredFootage").value(DEFAULT_REQUIRED_FOOTAGE))
            .andExpect(jsonPath("$.requiredLaize").value(DEFAULT_REQUIRED_LAIZE))
            .andExpect(jsonPath("$.clothingType").value(DEFAULT_CLOTHING_TYPE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.pictureTechnicalDrawingContentType").value(DEFAULT_PICTURE_TECHNICAL_DRAWING_CONTENT_TYPE))
            .andExpect(jsonPath("$.pictureTechnicalDrawing").value(Base64Utils.encodeToString(DEFAULT_PICTURE_TECHNICAL_DRAWING)))
            .andExpect(jsonPath("$.carriedPicture1ContentType").value(DEFAULT_CARRIED_PICTURE_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.carriedPicture1").value(Base64Utils.encodeToString(DEFAULT_CARRIED_PICTURE_1)))
            .andExpect(jsonPath("$.carriedPicture2ContentType").value(DEFAULT_CARRIED_PICTURE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.carriedPicture2").value(Base64Utils.encodeToString(DEFAULT_CARRIED_PICTURE_2)));
    }

    @Test
    @Transactional
    void getNonExistingPatron() throws Exception {
        // Get the patron
        restPatronMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPatron() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        int databaseSizeBeforeUpdate = patronRepository.findAll().size();

        // Update the patron
        Patron updatedPatron = patronRepository.findById(patron.getId()).get();
        // Disconnect from session so that the updates on updatedPatron are not directly saved in db
        em.detach(updatedPatron);
        updatedPatron
            .name(UPDATED_NAME)
            .ref(UPDATED_REF)
            .type(UPDATED_TYPE)
            .sexe(UPDATED_SEXE)
            .buyDate(UPDATED_BUY_DATE)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .creator(UPDATED_CREATOR)
            .difficultLevel(UPDATED_DIFFICULT_LEVEL)
            .fabricQualification(UPDATED_FABRIC_QUALIFICATION)
            .requiredFootage(UPDATED_REQUIRED_FOOTAGE)
            .requiredLaize(UPDATED_REQUIRED_LAIZE)
            .clothingType(UPDATED_CLOTHING_TYPE)
            .price(UPDATED_PRICE)
            .pictureTechnicalDrawing(UPDATED_PICTURE_TECHNICAL_DRAWING)
            .pictureTechnicalDrawingContentType(UPDATED_PICTURE_TECHNICAL_DRAWING_CONTENT_TYPE)
            .carriedPicture1(UPDATED_CARRIED_PICTURE_1)
            .carriedPicture1ContentType(UPDATED_CARRIED_PICTURE_1_CONTENT_TYPE)
            .carriedPicture2(UPDATED_CARRIED_PICTURE_2)
            .carriedPicture2ContentType(UPDATED_CARRIED_PICTURE_2_CONTENT_TYPE);

        restPatronMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPatron.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPatron))
            )
            .andExpect(status().isOk());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
        Patron testPatron = patronList.get(patronList.size() - 1);
        assertThat(testPatron.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatron.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testPatron.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPatron.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testPatron.getBuyDate()).isEqualTo(UPDATED_BUY_DATE);
        assertThat(testPatron.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testPatron.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testPatron.getDifficultLevel()).isEqualTo(UPDATED_DIFFICULT_LEVEL);
        assertThat(testPatron.getFabricQualification()).isEqualTo(UPDATED_FABRIC_QUALIFICATION);
        assertThat(testPatron.getRequiredFootage()).isEqualTo(UPDATED_REQUIRED_FOOTAGE);
        assertThat(testPatron.getRequiredLaize()).isEqualTo(UPDATED_REQUIRED_LAIZE);
        assertThat(testPatron.getClothingType()).isEqualTo(UPDATED_CLOTHING_TYPE);
        assertThat(testPatron.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPatron.getPictureTechnicalDrawing()).isEqualTo(UPDATED_PICTURE_TECHNICAL_DRAWING);
        assertThat(testPatron.getPictureTechnicalDrawingContentType()).isEqualTo(UPDATED_PICTURE_TECHNICAL_DRAWING_CONTENT_TYPE);
        assertThat(testPatron.getCarriedPicture1()).isEqualTo(UPDATED_CARRIED_PICTURE_1);
        assertThat(testPatron.getCarriedPicture1ContentType()).isEqualTo(UPDATED_CARRIED_PICTURE_1_CONTENT_TYPE);
        assertThat(testPatron.getCarriedPicture2()).isEqualTo(UPDATED_CARRIED_PICTURE_2);
        assertThat(testPatron.getCarriedPicture2ContentType()).isEqualTo(UPDATED_CARRIED_PICTURE_2_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();
        patron.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatronMockMvc
            .perform(
                put(ENTITY_API_URL_ID, patron.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patron))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();
        patron.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatronMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(patron))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();
        patron.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatronMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePatronWithPatch() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        int databaseSizeBeforeUpdate = patronRepository.findAll().size();

        // Update the patron using partial update
        Patron partialUpdatedPatron = new Patron();
        partialUpdatedPatron.setId(patron.getId());

        partialUpdatedPatron
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .sexe(UPDATED_SEXE)
            .creator(UPDATED_CREATOR)
            .fabricQualification(UPDATED_FABRIC_QUALIFICATION)
            .requiredFootage(UPDATED_REQUIRED_FOOTAGE)
            .requiredLaize(UPDATED_REQUIRED_LAIZE)
            .carriedPicture1(UPDATED_CARRIED_PICTURE_1)
            .carriedPicture1ContentType(UPDATED_CARRIED_PICTURE_1_CONTENT_TYPE)
            .carriedPicture2(UPDATED_CARRIED_PICTURE_2)
            .carriedPicture2ContentType(UPDATED_CARRIED_PICTURE_2_CONTENT_TYPE);

        restPatronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatron.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatron))
            )
            .andExpect(status().isOk());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
        Patron testPatron = patronList.get(patronList.size() - 1);
        assertThat(testPatron.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatron.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testPatron.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPatron.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testPatron.getBuyDate()).isEqualTo(DEFAULT_BUY_DATE);
        assertThat(testPatron.getPublicationDate()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testPatron.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testPatron.getDifficultLevel()).isEqualTo(DEFAULT_DIFFICULT_LEVEL);
        assertThat(testPatron.getFabricQualification()).isEqualTo(UPDATED_FABRIC_QUALIFICATION);
        assertThat(testPatron.getRequiredFootage()).isEqualTo(UPDATED_REQUIRED_FOOTAGE);
        assertThat(testPatron.getRequiredLaize()).isEqualTo(UPDATED_REQUIRED_LAIZE);
        assertThat(testPatron.getClothingType()).isEqualTo(DEFAULT_CLOTHING_TYPE);
        assertThat(testPatron.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPatron.getPictureTechnicalDrawing()).isEqualTo(DEFAULT_PICTURE_TECHNICAL_DRAWING);
        assertThat(testPatron.getPictureTechnicalDrawingContentType()).isEqualTo(DEFAULT_PICTURE_TECHNICAL_DRAWING_CONTENT_TYPE);
        assertThat(testPatron.getCarriedPicture1()).isEqualTo(UPDATED_CARRIED_PICTURE_1);
        assertThat(testPatron.getCarriedPicture1ContentType()).isEqualTo(UPDATED_CARRIED_PICTURE_1_CONTENT_TYPE);
        assertThat(testPatron.getCarriedPicture2()).isEqualTo(UPDATED_CARRIED_PICTURE_2);
        assertThat(testPatron.getCarriedPicture2ContentType()).isEqualTo(UPDATED_CARRIED_PICTURE_2_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdatePatronWithPatch() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        int databaseSizeBeforeUpdate = patronRepository.findAll().size();

        // Update the patron using partial update
        Patron partialUpdatedPatron = new Patron();
        partialUpdatedPatron.setId(patron.getId());

        partialUpdatedPatron
            .name(UPDATED_NAME)
            .ref(UPDATED_REF)
            .type(UPDATED_TYPE)
            .sexe(UPDATED_SEXE)
            .buyDate(UPDATED_BUY_DATE)
            .publicationDate(UPDATED_PUBLICATION_DATE)
            .creator(UPDATED_CREATOR)
            .difficultLevel(UPDATED_DIFFICULT_LEVEL)
            .fabricQualification(UPDATED_FABRIC_QUALIFICATION)
            .requiredFootage(UPDATED_REQUIRED_FOOTAGE)
            .requiredLaize(UPDATED_REQUIRED_LAIZE)
            .clothingType(UPDATED_CLOTHING_TYPE)
            .price(UPDATED_PRICE)
            .pictureTechnicalDrawing(UPDATED_PICTURE_TECHNICAL_DRAWING)
            .pictureTechnicalDrawingContentType(UPDATED_PICTURE_TECHNICAL_DRAWING_CONTENT_TYPE)
            .carriedPicture1(UPDATED_CARRIED_PICTURE_1)
            .carriedPicture1ContentType(UPDATED_CARRIED_PICTURE_1_CONTENT_TYPE)
            .carriedPicture2(UPDATED_CARRIED_PICTURE_2)
            .carriedPicture2ContentType(UPDATED_CARRIED_PICTURE_2_CONTENT_TYPE);

        restPatronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPatron.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPatron))
            )
            .andExpect(status().isOk());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
        Patron testPatron = patronList.get(patronList.size() - 1);
        assertThat(testPatron.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatron.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testPatron.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPatron.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testPatron.getBuyDate()).isEqualTo(UPDATED_BUY_DATE);
        assertThat(testPatron.getPublicationDate()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testPatron.getCreator()).isEqualTo(UPDATED_CREATOR);
        assertThat(testPatron.getDifficultLevel()).isEqualTo(UPDATED_DIFFICULT_LEVEL);
        assertThat(testPatron.getFabricQualification()).isEqualTo(UPDATED_FABRIC_QUALIFICATION);
        assertThat(testPatron.getRequiredFootage()).isEqualTo(UPDATED_REQUIRED_FOOTAGE);
        assertThat(testPatron.getRequiredLaize()).isEqualTo(UPDATED_REQUIRED_LAIZE);
        assertThat(testPatron.getClothingType()).isEqualTo(UPDATED_CLOTHING_TYPE);
        assertThat(testPatron.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPatron.getPictureTechnicalDrawing()).isEqualTo(UPDATED_PICTURE_TECHNICAL_DRAWING);
        assertThat(testPatron.getPictureTechnicalDrawingContentType()).isEqualTo(UPDATED_PICTURE_TECHNICAL_DRAWING_CONTENT_TYPE);
        assertThat(testPatron.getCarriedPicture1()).isEqualTo(UPDATED_CARRIED_PICTURE_1);
        assertThat(testPatron.getCarriedPicture1ContentType()).isEqualTo(UPDATED_CARRIED_PICTURE_1_CONTENT_TYPE);
        assertThat(testPatron.getCarriedPicture2()).isEqualTo(UPDATED_CARRIED_PICTURE_2);
        assertThat(testPatron.getCarriedPicture2ContentType()).isEqualTo(UPDATED_CARRIED_PICTURE_2_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();
        patron.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, patron.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patron))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();
        patron.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatronMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(patron))
            )
            .andExpect(status().isBadRequest());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPatron() throws Exception {
        int databaseSizeBeforeUpdate = patronRepository.findAll().size();
        patron.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPatronMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(patron)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Patron in the database
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePatron() throws Exception {
        // Initialize the database
        patronRepository.saveAndFlush(patron);

        int databaseSizeBeforeDelete = patronRepository.findAll().size();

        // Delete the patron
        restPatronMockMvc
            .perform(delete(ENTITY_API_URL_ID, patron.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patron> patronList = patronRepository.findAll();
        assertThat(patronList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
