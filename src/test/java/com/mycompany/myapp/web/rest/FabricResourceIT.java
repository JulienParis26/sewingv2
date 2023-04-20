package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Fabric;
import com.mycompany.myapp.domain.FabricTypes;
import com.mycompany.myapp.domain.Materials;
import com.mycompany.myapp.repository.FabricRepository;
import com.mycompany.myapp.service.FabricService;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link FabricResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FabricResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REF = "AAAAAAAAAA";
    private static final String UPDATED_REF = "BBBBBBBBBB";

    private static final Boolean DEFAULT_UNI = false;
    private static final Boolean UPDATED_UNI = true;

    private static final String DEFAULT_BUY_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_BUY_SIZE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ELASTIC = false;
    private static final Boolean UPDATED_ELASTIC = true;

    private static final Float DEFAULT_ELASTIC_RATE = 1F;
    private static final Float UPDATED_ELASTIC_RATE = 2F;

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final String DEFAULT_COLOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_1 = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_1 = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_2 = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_2 = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_3 = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_3 = "BBBBBBBBBB";

    private static final Integer DEFAULT_LAIZE = 1;
    private static final Integer UPDATED_LAIZE = 2;

    private static final Float DEFAULT_METER_PRICE = 1F;
    private static final Float UPDATED_METER_PRICE = 2F;

    private static final Float DEFAULT_METER_BUY = 1F;
    private static final Float UPDATED_METER_BUY = 2F;

    private static final Float DEFAULT_METER_IN_STOCK = 1F;
    private static final Float UPDATED_METER_IN_STOCK = 2F;

    private static final LocalDate DEFAULT_BUY_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BUY_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_GRAM_PER_METER = 1;
    private static final Integer UPDATED_GRAM_PER_METER = 2;

    private static final Integer DEFAULT_SIZE_MIN = 1;
    private static final Integer UPDATED_SIZE_MIN = 2;

    private static final Integer DEFAULT_SIZE_MAX = 1;
    private static final Integer UPDATED_SIZE_MAX = 2;

    private static final byte[] DEFAULT_IMAGE_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_3_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/fabrics";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FabricRepository fabricRepository;

    @Mock
    private FabricRepository fabricRepositoryMock;

    @Mock
    private FabricService fabricServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFabricMockMvc;

    private Fabric fabric;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fabric createEntity(EntityManager em) {
        Fabric fabric = new Fabric()
            .name(DEFAULT_NAME)
            .ref(DEFAULT_REF)
            .uni(DEFAULT_UNI)
            .buySize(DEFAULT_BUY_SIZE)
            .elastic(DEFAULT_ELASTIC)
            .elasticRate(DEFAULT_ELASTIC_RATE)
            .rating(DEFAULT_RATING)
            .colorName(DEFAULT_COLOR_NAME)
            .color1(DEFAULT_COLOR_1)
            .color2(DEFAULT_COLOR_2)
            .color3(DEFAULT_COLOR_3)
            .laize(DEFAULT_LAIZE)
            .meterPrice(DEFAULT_METER_PRICE)
            .meterBuy(DEFAULT_METER_BUY)
            .meterInStock(DEFAULT_METER_IN_STOCK)
            .buyDate(DEFAULT_BUY_DATE)
            .gramPerMeter(DEFAULT_GRAM_PER_METER)
            .sizeMin(DEFAULT_SIZE_MIN)
            .sizeMax(DEFAULT_SIZE_MAX)
            .image1(DEFAULT_IMAGE_1)
            .image1ContentType(DEFAULT_IMAGE_1_CONTENT_TYPE)
            .image2(DEFAULT_IMAGE_2)
            .image2ContentType(DEFAULT_IMAGE_2_CONTENT_TYPE)
            .image3(DEFAULT_IMAGE_3)
            .image3ContentType(DEFAULT_IMAGE_3_CONTENT_TYPE);
        // Add required entity
        FabricTypes fabricTypes;
        if (TestUtil.findAll(em, FabricTypes.class).isEmpty()) {
            fabricTypes = FabricTypesResourceIT.createEntity(em);
            em.persist(fabricTypes);
            em.flush();
        } else {
            fabricTypes = TestUtil.findAll(em, FabricTypes.class).get(0);
        }
        fabric.getFabricTypes().add(fabricTypes);
        // Add required entity
        Materials materials;
        if (TestUtil.findAll(em, Materials.class).isEmpty()) {
            materials = MaterialsResourceIT.createEntity(em);
            em.persist(materials);
            em.flush();
        } else {
            materials = TestUtil.findAll(em, Materials.class).get(0);
        }
        fabric.getMaterials().add(materials);
        return fabric;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fabric createUpdatedEntity(EntityManager em) {
        Fabric fabric = new Fabric()
            .name(UPDATED_NAME)
            .ref(UPDATED_REF)
            .uni(UPDATED_UNI)
            .buySize(UPDATED_BUY_SIZE)
            .elastic(UPDATED_ELASTIC)
            .elasticRate(UPDATED_ELASTIC_RATE)
            .rating(UPDATED_RATING)
            .colorName(UPDATED_COLOR_NAME)
            .color1(UPDATED_COLOR_1)
            .color2(UPDATED_COLOR_2)
            .color3(UPDATED_COLOR_3)
            .laize(UPDATED_LAIZE)
            .meterPrice(UPDATED_METER_PRICE)
            .meterBuy(UPDATED_METER_BUY)
            .meterInStock(UPDATED_METER_IN_STOCK)
            .buyDate(UPDATED_BUY_DATE)
            .gramPerMeter(UPDATED_GRAM_PER_METER)
            .sizeMin(UPDATED_SIZE_MIN)
            .sizeMax(UPDATED_SIZE_MAX)
            .image1(UPDATED_IMAGE_1)
            .image1ContentType(UPDATED_IMAGE_1_CONTENT_TYPE)
            .image2(UPDATED_IMAGE_2)
            .image2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE)
            .image3(UPDATED_IMAGE_3)
            .image3ContentType(UPDATED_IMAGE_3_CONTENT_TYPE);
        // Add required entity
        FabricTypes fabricTypes;
        if (TestUtil.findAll(em, FabricTypes.class).isEmpty()) {
            fabricTypes = FabricTypesResourceIT.createUpdatedEntity(em);
            em.persist(fabricTypes);
            em.flush();
        } else {
            fabricTypes = TestUtil.findAll(em, FabricTypes.class).get(0);
        }
        fabric.getFabricTypes().add(fabricTypes);
        // Add required entity
        Materials materials;
        if (TestUtil.findAll(em, Materials.class).isEmpty()) {
            materials = MaterialsResourceIT.createUpdatedEntity(em);
            em.persist(materials);
            em.flush();
        } else {
            materials = TestUtil.findAll(em, Materials.class).get(0);
        }
        fabric.getMaterials().add(materials);
        return fabric;
    }

    @BeforeEach
    public void initTest() {
        fabric = createEntity(em);
    }

    @Test
    @Transactional
    void createFabric() throws Exception {
        int databaseSizeBeforeCreate = fabricRepository.findAll().size();
        // Create the Fabric
        restFabricMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabric)))
            .andExpect(status().isCreated());

        // Validate the Fabric in the database
        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeCreate + 1);
        Fabric testFabric = fabricList.get(fabricList.size() - 1);
        assertThat(testFabric.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFabric.getRef()).isEqualTo(DEFAULT_REF);
        assertThat(testFabric.getUni()).isEqualTo(DEFAULT_UNI);
        assertThat(testFabric.getBuySize()).isEqualTo(DEFAULT_BUY_SIZE);
        assertThat(testFabric.getElastic()).isEqualTo(DEFAULT_ELASTIC);
        assertThat(testFabric.getElasticRate()).isEqualTo(DEFAULT_ELASTIC_RATE);
        assertThat(testFabric.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testFabric.getColorName()).isEqualTo(DEFAULT_COLOR_NAME);
        assertThat(testFabric.getColor1()).isEqualTo(DEFAULT_COLOR_1);
        assertThat(testFabric.getColor2()).isEqualTo(DEFAULT_COLOR_2);
        assertThat(testFabric.getColor3()).isEqualTo(DEFAULT_COLOR_3);
        assertThat(testFabric.getLaize()).isEqualTo(DEFAULT_LAIZE);
        assertThat(testFabric.getMeterPrice()).isEqualTo(DEFAULT_METER_PRICE);
        assertThat(testFabric.getMeterBuy()).isEqualTo(DEFAULT_METER_BUY);
        assertThat(testFabric.getMeterInStock()).isEqualTo(DEFAULT_METER_IN_STOCK);
        assertThat(testFabric.getBuyDate()).isEqualTo(DEFAULT_BUY_DATE);
        assertThat(testFabric.getGramPerMeter()).isEqualTo(DEFAULT_GRAM_PER_METER);
        assertThat(testFabric.getSizeMin()).isEqualTo(DEFAULT_SIZE_MIN);
        assertThat(testFabric.getSizeMax()).isEqualTo(DEFAULT_SIZE_MAX);
        assertThat(testFabric.getImage1()).isEqualTo(DEFAULT_IMAGE_1);
        assertThat(testFabric.getImage1ContentType()).isEqualTo(DEFAULT_IMAGE_1_CONTENT_TYPE);
        assertThat(testFabric.getImage2()).isEqualTo(DEFAULT_IMAGE_2);
        assertThat(testFabric.getImage2ContentType()).isEqualTo(DEFAULT_IMAGE_2_CONTENT_TYPE);
        assertThat(testFabric.getImage3()).isEqualTo(DEFAULT_IMAGE_3);
        assertThat(testFabric.getImage3ContentType()).isEqualTo(DEFAULT_IMAGE_3_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void createFabricWithExistingId() throws Exception {
        // Create the Fabric with an existing ID
        fabric.setId(1L);

        int databaseSizeBeforeCreate = fabricRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFabricMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabric)))
            .andExpect(status().isBadRequest());

        // Validate the Fabric in the database
        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricRepository.findAll().size();
        // set the field null
        fabric.setName(null);

        // Create the Fabric, which fails.

        restFabricMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabric)))
            .andExpect(status().isBadRequest());

        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUniIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricRepository.findAll().size();
        // set the field null
        fabric.setUni(null);

        // Create the Fabric, which fails.

        restFabricMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabric)))
            .andExpect(status().isBadRequest());

        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkElasticIsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricRepository.findAll().size();
        // set the field null
        fabric.setElastic(null);

        // Create the Fabric, which fails.

        restFabricMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabric)))
            .andExpect(status().isBadRequest());

        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkColor1IsRequired() throws Exception {
        int databaseSizeBeforeTest = fabricRepository.findAll().size();
        // set the field null
        fabric.setColor1(null);

        // Create the Fabric, which fails.

        restFabricMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabric)))
            .andExpect(status().isBadRequest());

        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFabrics() throws Exception {
        // Initialize the database
        fabricRepository.saveAndFlush(fabric);

        // Get all the fabricList
        restFabricMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fabric.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].ref").value(hasItem(DEFAULT_REF)))
            .andExpect(jsonPath("$.[*].uni").value(hasItem(DEFAULT_UNI.booleanValue())))
            .andExpect(jsonPath("$.[*].buySize").value(hasItem(DEFAULT_BUY_SIZE)))
            .andExpect(jsonPath("$.[*].elastic").value(hasItem(DEFAULT_ELASTIC.booleanValue())))
            .andExpect(jsonPath("$.[*].elasticRate").value(hasItem(DEFAULT_ELASTIC_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].colorName").value(hasItem(DEFAULT_COLOR_NAME)))
            .andExpect(jsonPath("$.[*].color1").value(hasItem(DEFAULT_COLOR_1)))
            .andExpect(jsonPath("$.[*].color2").value(hasItem(DEFAULT_COLOR_2)))
            .andExpect(jsonPath("$.[*].color3").value(hasItem(DEFAULT_COLOR_3)))
            .andExpect(jsonPath("$.[*].laize").value(hasItem(DEFAULT_LAIZE)))
            .andExpect(jsonPath("$.[*].meterPrice").value(hasItem(DEFAULT_METER_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].meterBuy").value(hasItem(DEFAULT_METER_BUY.doubleValue())))
            .andExpect(jsonPath("$.[*].meterInStock").value(hasItem(DEFAULT_METER_IN_STOCK.doubleValue())))
            .andExpect(jsonPath("$.[*].buyDate").value(hasItem(DEFAULT_BUY_DATE.toString())))
            .andExpect(jsonPath("$.[*].gramPerMeter").value(hasItem(DEFAULT_GRAM_PER_METER)))
            .andExpect(jsonPath("$.[*].sizeMin").value(hasItem(DEFAULT_SIZE_MIN)))
            .andExpect(jsonPath("$.[*].sizeMax").value(hasItem(DEFAULT_SIZE_MAX)))
            .andExpect(jsonPath("$.[*].image1ContentType").value(hasItem(DEFAULT_IMAGE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_1))))
            .andExpect(jsonPath("$.[*].image2ContentType").value(hasItem(DEFAULT_IMAGE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_2))))
            .andExpect(jsonPath("$.[*].image3ContentType").value(hasItem(DEFAULT_IMAGE_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_3))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFabricsWithEagerRelationshipsIsEnabled() throws Exception {
        when(fabricServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFabricMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(fabricServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllFabricsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(fabricServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restFabricMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(fabricRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getFabric() throws Exception {
        // Initialize the database
        fabricRepository.saveAndFlush(fabric);

        // Get the fabric
        restFabricMockMvc
            .perform(get(ENTITY_API_URL_ID, fabric.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fabric.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.ref").value(DEFAULT_REF))
            .andExpect(jsonPath("$.uni").value(DEFAULT_UNI.booleanValue()))
            .andExpect(jsonPath("$.buySize").value(DEFAULT_BUY_SIZE))
            .andExpect(jsonPath("$.elastic").value(DEFAULT_ELASTIC.booleanValue()))
            .andExpect(jsonPath("$.elasticRate").value(DEFAULT_ELASTIC_RATE.doubleValue()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.colorName").value(DEFAULT_COLOR_NAME))
            .andExpect(jsonPath("$.color1").value(DEFAULT_COLOR_1))
            .andExpect(jsonPath("$.color2").value(DEFAULT_COLOR_2))
            .andExpect(jsonPath("$.color3").value(DEFAULT_COLOR_3))
            .andExpect(jsonPath("$.laize").value(DEFAULT_LAIZE))
            .andExpect(jsonPath("$.meterPrice").value(DEFAULT_METER_PRICE.doubleValue()))
            .andExpect(jsonPath("$.meterBuy").value(DEFAULT_METER_BUY.doubleValue()))
            .andExpect(jsonPath("$.meterInStock").value(DEFAULT_METER_IN_STOCK.doubleValue()))
            .andExpect(jsonPath("$.buyDate").value(DEFAULT_BUY_DATE.toString()))
            .andExpect(jsonPath("$.gramPerMeter").value(DEFAULT_GRAM_PER_METER))
            .andExpect(jsonPath("$.sizeMin").value(DEFAULT_SIZE_MIN))
            .andExpect(jsonPath("$.sizeMax").value(DEFAULT_SIZE_MAX))
            .andExpect(jsonPath("$.image1ContentType").value(DEFAULT_IMAGE_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.image1").value(Base64Utils.encodeToString(DEFAULT_IMAGE_1)))
            .andExpect(jsonPath("$.image2ContentType").value(DEFAULT_IMAGE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.image2").value(Base64Utils.encodeToString(DEFAULT_IMAGE_2)))
            .andExpect(jsonPath("$.image3ContentType").value(DEFAULT_IMAGE_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.image3").value(Base64Utils.encodeToString(DEFAULT_IMAGE_3)));
    }

    @Test
    @Transactional
    void getNonExistingFabric() throws Exception {
        // Get the fabric
        restFabricMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFabric() throws Exception {
        // Initialize the database
        fabricRepository.saveAndFlush(fabric);

        int databaseSizeBeforeUpdate = fabricRepository.findAll().size();

        // Update the fabric
        Fabric updatedFabric = fabricRepository.findById(fabric.getId()).get();
        // Disconnect from session so that the updates on updatedFabric are not directly saved in db
        em.detach(updatedFabric);
        updatedFabric
            .name(UPDATED_NAME)
            .ref(UPDATED_REF)
            .uni(UPDATED_UNI)
            .buySize(UPDATED_BUY_SIZE)
            .elastic(UPDATED_ELASTIC)
            .elasticRate(UPDATED_ELASTIC_RATE)
            .rating(UPDATED_RATING)
            .colorName(UPDATED_COLOR_NAME)
            .color1(UPDATED_COLOR_1)
            .color2(UPDATED_COLOR_2)
            .color3(UPDATED_COLOR_3)
            .laize(UPDATED_LAIZE)
            .meterPrice(UPDATED_METER_PRICE)
            .meterBuy(UPDATED_METER_BUY)
            .meterInStock(UPDATED_METER_IN_STOCK)
            .buyDate(UPDATED_BUY_DATE)
            .gramPerMeter(UPDATED_GRAM_PER_METER)
            .sizeMin(UPDATED_SIZE_MIN)
            .sizeMax(UPDATED_SIZE_MAX)
            .image1(UPDATED_IMAGE_1)
            .image1ContentType(UPDATED_IMAGE_1_CONTENT_TYPE)
            .image2(UPDATED_IMAGE_2)
            .image2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE)
            .image3(UPDATED_IMAGE_3)
            .image3ContentType(UPDATED_IMAGE_3_CONTENT_TYPE);

        restFabricMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFabric.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFabric))
            )
            .andExpect(status().isOk());

        // Validate the Fabric in the database
        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeUpdate);
        Fabric testFabric = fabricList.get(fabricList.size() - 1);
        assertThat(testFabric.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabric.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testFabric.getUni()).isEqualTo(UPDATED_UNI);
        assertThat(testFabric.getBuySize()).isEqualTo(UPDATED_BUY_SIZE);
        assertThat(testFabric.getElastic()).isEqualTo(UPDATED_ELASTIC);
        assertThat(testFabric.getElasticRate()).isEqualTo(UPDATED_ELASTIC_RATE);
        assertThat(testFabric.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testFabric.getColorName()).isEqualTo(UPDATED_COLOR_NAME);
        assertThat(testFabric.getColor1()).isEqualTo(UPDATED_COLOR_1);
        assertThat(testFabric.getColor2()).isEqualTo(UPDATED_COLOR_2);
        assertThat(testFabric.getColor3()).isEqualTo(UPDATED_COLOR_3);
        assertThat(testFabric.getLaize()).isEqualTo(UPDATED_LAIZE);
        assertThat(testFabric.getMeterPrice()).isEqualTo(UPDATED_METER_PRICE);
        assertThat(testFabric.getMeterBuy()).isEqualTo(UPDATED_METER_BUY);
        assertThat(testFabric.getMeterInStock()).isEqualTo(UPDATED_METER_IN_STOCK);
        assertThat(testFabric.getBuyDate()).isEqualTo(UPDATED_BUY_DATE);
        assertThat(testFabric.getGramPerMeter()).isEqualTo(UPDATED_GRAM_PER_METER);
        assertThat(testFabric.getSizeMin()).isEqualTo(UPDATED_SIZE_MIN);
        assertThat(testFabric.getSizeMax()).isEqualTo(UPDATED_SIZE_MAX);
        assertThat(testFabric.getImage1()).isEqualTo(UPDATED_IMAGE_1);
        assertThat(testFabric.getImage1ContentType()).isEqualTo(UPDATED_IMAGE_1_CONTENT_TYPE);
        assertThat(testFabric.getImage2()).isEqualTo(UPDATED_IMAGE_2);
        assertThat(testFabric.getImage2ContentType()).isEqualTo(UPDATED_IMAGE_2_CONTENT_TYPE);
        assertThat(testFabric.getImage3()).isEqualTo(UPDATED_IMAGE_3);
        assertThat(testFabric.getImage3ContentType()).isEqualTo(UPDATED_IMAGE_3_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingFabric() throws Exception {
        int databaseSizeBeforeUpdate = fabricRepository.findAll().size();
        fabric.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fabric.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabric))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fabric in the database
        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFabric() throws Exception {
        int databaseSizeBeforeUpdate = fabricRepository.findAll().size();
        fabric.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fabric))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fabric in the database
        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFabric() throws Exception {
        int databaseSizeBeforeUpdate = fabricRepository.findAll().size();
        fabric.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fabric)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fabric in the database
        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFabricWithPatch() throws Exception {
        // Initialize the database
        fabricRepository.saveAndFlush(fabric);

        int databaseSizeBeforeUpdate = fabricRepository.findAll().size();

        // Update the fabric using partial update
        Fabric partialUpdatedFabric = new Fabric();
        partialUpdatedFabric.setId(fabric.getId());

        partialUpdatedFabric
            .ref(UPDATED_REF)
            .color1(UPDATED_COLOR_1)
            .color2(UPDATED_COLOR_2)
            .laize(UPDATED_LAIZE)
            .meterPrice(UPDATED_METER_PRICE)
            .meterInStock(UPDATED_METER_IN_STOCK)
            .gramPerMeter(UPDATED_GRAM_PER_METER)
            .sizeMin(UPDATED_SIZE_MIN)
            .sizeMax(UPDATED_SIZE_MAX)
            .image2(UPDATED_IMAGE_2)
            .image2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE);

        restFabricMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabric.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabric))
            )
            .andExpect(status().isOk());

        // Validate the Fabric in the database
        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeUpdate);
        Fabric testFabric = fabricList.get(fabricList.size() - 1);
        assertThat(testFabric.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFabric.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testFabric.getUni()).isEqualTo(DEFAULT_UNI);
        assertThat(testFabric.getBuySize()).isEqualTo(DEFAULT_BUY_SIZE);
        assertThat(testFabric.getElastic()).isEqualTo(DEFAULT_ELASTIC);
        assertThat(testFabric.getElasticRate()).isEqualTo(DEFAULT_ELASTIC_RATE);
        assertThat(testFabric.getRating()).isEqualTo(DEFAULT_RATING);
        assertThat(testFabric.getColorName()).isEqualTo(DEFAULT_COLOR_NAME);
        assertThat(testFabric.getColor1()).isEqualTo(UPDATED_COLOR_1);
        assertThat(testFabric.getColor2()).isEqualTo(UPDATED_COLOR_2);
        assertThat(testFabric.getColor3()).isEqualTo(DEFAULT_COLOR_3);
        assertThat(testFabric.getLaize()).isEqualTo(UPDATED_LAIZE);
        assertThat(testFabric.getMeterPrice()).isEqualTo(UPDATED_METER_PRICE);
        assertThat(testFabric.getMeterBuy()).isEqualTo(DEFAULT_METER_BUY);
        assertThat(testFabric.getMeterInStock()).isEqualTo(UPDATED_METER_IN_STOCK);
        assertThat(testFabric.getBuyDate()).isEqualTo(DEFAULT_BUY_DATE);
        assertThat(testFabric.getGramPerMeter()).isEqualTo(UPDATED_GRAM_PER_METER);
        assertThat(testFabric.getSizeMin()).isEqualTo(UPDATED_SIZE_MIN);
        assertThat(testFabric.getSizeMax()).isEqualTo(UPDATED_SIZE_MAX);
        assertThat(testFabric.getImage1()).isEqualTo(DEFAULT_IMAGE_1);
        assertThat(testFabric.getImage1ContentType()).isEqualTo(DEFAULT_IMAGE_1_CONTENT_TYPE);
        assertThat(testFabric.getImage2()).isEqualTo(UPDATED_IMAGE_2);
        assertThat(testFabric.getImage2ContentType()).isEqualTo(UPDATED_IMAGE_2_CONTENT_TYPE);
        assertThat(testFabric.getImage3()).isEqualTo(DEFAULT_IMAGE_3);
        assertThat(testFabric.getImage3ContentType()).isEqualTo(DEFAULT_IMAGE_3_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateFabricWithPatch() throws Exception {
        // Initialize the database
        fabricRepository.saveAndFlush(fabric);

        int databaseSizeBeforeUpdate = fabricRepository.findAll().size();

        // Update the fabric using partial update
        Fabric partialUpdatedFabric = new Fabric();
        partialUpdatedFabric.setId(fabric.getId());

        partialUpdatedFabric
            .name(UPDATED_NAME)
            .ref(UPDATED_REF)
            .uni(UPDATED_UNI)
            .buySize(UPDATED_BUY_SIZE)
            .elastic(UPDATED_ELASTIC)
            .elasticRate(UPDATED_ELASTIC_RATE)
            .rating(UPDATED_RATING)
            .colorName(UPDATED_COLOR_NAME)
            .color1(UPDATED_COLOR_1)
            .color2(UPDATED_COLOR_2)
            .color3(UPDATED_COLOR_3)
            .laize(UPDATED_LAIZE)
            .meterPrice(UPDATED_METER_PRICE)
            .meterBuy(UPDATED_METER_BUY)
            .meterInStock(UPDATED_METER_IN_STOCK)
            .buyDate(UPDATED_BUY_DATE)
            .gramPerMeter(UPDATED_GRAM_PER_METER)
            .sizeMin(UPDATED_SIZE_MIN)
            .sizeMax(UPDATED_SIZE_MAX)
            .image1(UPDATED_IMAGE_1)
            .image1ContentType(UPDATED_IMAGE_1_CONTENT_TYPE)
            .image2(UPDATED_IMAGE_2)
            .image2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE)
            .image3(UPDATED_IMAGE_3)
            .image3ContentType(UPDATED_IMAGE_3_CONTENT_TYPE);

        restFabricMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFabric.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFabric))
            )
            .andExpect(status().isOk());

        // Validate the Fabric in the database
        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeUpdate);
        Fabric testFabric = fabricList.get(fabricList.size() - 1);
        assertThat(testFabric.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFabric.getRef()).isEqualTo(UPDATED_REF);
        assertThat(testFabric.getUni()).isEqualTo(UPDATED_UNI);
        assertThat(testFabric.getBuySize()).isEqualTo(UPDATED_BUY_SIZE);
        assertThat(testFabric.getElastic()).isEqualTo(UPDATED_ELASTIC);
        assertThat(testFabric.getElasticRate()).isEqualTo(UPDATED_ELASTIC_RATE);
        assertThat(testFabric.getRating()).isEqualTo(UPDATED_RATING);
        assertThat(testFabric.getColorName()).isEqualTo(UPDATED_COLOR_NAME);
        assertThat(testFabric.getColor1()).isEqualTo(UPDATED_COLOR_1);
        assertThat(testFabric.getColor2()).isEqualTo(UPDATED_COLOR_2);
        assertThat(testFabric.getColor3()).isEqualTo(UPDATED_COLOR_3);
        assertThat(testFabric.getLaize()).isEqualTo(UPDATED_LAIZE);
        assertThat(testFabric.getMeterPrice()).isEqualTo(UPDATED_METER_PRICE);
        assertThat(testFabric.getMeterBuy()).isEqualTo(UPDATED_METER_BUY);
        assertThat(testFabric.getMeterInStock()).isEqualTo(UPDATED_METER_IN_STOCK);
        assertThat(testFabric.getBuyDate()).isEqualTo(UPDATED_BUY_DATE);
        assertThat(testFabric.getGramPerMeter()).isEqualTo(UPDATED_GRAM_PER_METER);
        assertThat(testFabric.getSizeMin()).isEqualTo(UPDATED_SIZE_MIN);
        assertThat(testFabric.getSizeMax()).isEqualTo(UPDATED_SIZE_MAX);
        assertThat(testFabric.getImage1()).isEqualTo(UPDATED_IMAGE_1);
        assertThat(testFabric.getImage1ContentType()).isEqualTo(UPDATED_IMAGE_1_CONTENT_TYPE);
        assertThat(testFabric.getImage2()).isEqualTo(UPDATED_IMAGE_2);
        assertThat(testFabric.getImage2ContentType()).isEqualTo(UPDATED_IMAGE_2_CONTENT_TYPE);
        assertThat(testFabric.getImage3()).isEqualTo(UPDATED_IMAGE_3);
        assertThat(testFabric.getImage3ContentType()).isEqualTo(UPDATED_IMAGE_3_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingFabric() throws Exception {
        int databaseSizeBeforeUpdate = fabricRepository.findAll().size();
        fabric.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFabricMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fabric.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabric))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fabric in the database
        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFabric() throws Exception {
        int databaseSizeBeforeUpdate = fabricRepository.findAll().size();
        fabric.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fabric))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fabric in the database
        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFabric() throws Exception {
        int databaseSizeBeforeUpdate = fabricRepository.findAll().size();
        fabric.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFabricMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fabric)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fabric in the database
        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFabric() throws Exception {
        // Initialize the database
        fabricRepository.saveAndFlush(fabric);

        int databaseSizeBeforeDelete = fabricRepository.findAll().size();

        // Delete the fabric
        restFabricMockMvc
            .perform(delete(ENTITY_API_URL_ID, fabric.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fabric> fabricList = fabricRepository.findAll();
        assertThat(fabricList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
