package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Fabric.
 */
@Entity
@Table(name = "fabric")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Fabric implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "ref")
    private String ref;

    @NotNull
    @Column(name = "uni", nullable = false)
    private Boolean uni;

    @Column(name = "buy_size")
    private String buySize;

    @NotNull
    @Column(name = "elastic", nullable = false)
    private Boolean elastic;

    @Column(name = "elastic_rate")
    private Float elasticRate;

    @Min(value = 1)
    @Max(value = 5)
    @Column(name = "rating")
    private Integer rating;

    @Column(name = "color_name")
    private String colorName;

    @NotNull
    @Column(name = "color_1", nullable = false)
    private String color1;

    @Column(name = "color_2")
    private String color2;

    @Column(name = "color_3")
    private String color3;

    @Column(name = "laize")
    private Integer laize;

    @Column(name = "meter_price")
    private Float meterPrice;

    @Column(name = "meter_buy")
    private Float meterBuy;

    @Column(name = "meter_in_stock")
    private Float meterInStock;

    @Column(name = "buy_date")
    private LocalDate buyDate;

    @Column(name = "gram_per_meter")
    private Integer gramPerMeter;

    @Column(name = "size_min")
    private Integer sizeMin;

    @Column(name = "size_max")
    private Integer sizeMax;

    @Lob
    @Column(name = "image_1", nullable = false)
    private byte[] image1;

    @NotNull
    @Column(name = "image_1_content_type", nullable = false)
    private String image1ContentType;

    @Lob
    @Column(name = "image_2")
    private byte[] image2;

    @Column(name = "image_2_content_type")
    private String image2ContentType;

    @Lob
    @Column(name = "image_3")
    private byte[] image3;

    @Column(name = "image_3_content_type")
    private String image3ContentType;

    @ManyToOne
    @JsonIgnoreProperties(value = { "fabrics" }, allowSetters = true)
    private FabricEditor from;

    @ManyToMany
    @NotNull
    @JoinTable(
        name = "rel_fabric__fabric_types",
        joinColumns = @JoinColumn(name = "fabric_id"),
        inverseJoinColumns = @JoinColumn(name = "fabric_types_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fabrics" }, allowSetters = true)
    private Set<FabricTypes> fabricTypes = new HashSet<>();

    @ManyToMany
    @NotNull
    @JoinTable(
        name = "rel_fabric__materials",
        joinColumns = @JoinColumn(name = "fabric_id"),
        inverseJoinColumns = @JoinColumn(name = "materials_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fabrics" }, allowSetters = true)
    private Set<Materials> materials = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "rel_fabric__uses", joinColumns = @JoinColumn(name = "fabric_id"), inverseJoinColumns = @JoinColumn(name = "uses_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fabrics" }, allowSetters = true)
    private Set<FabricUses> uses = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_fabric__maintenances",
        joinColumns = @JoinColumn(name = "fabric_id"),
        inverseJoinColumns = @JoinColumn(name = "maintenances_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fabrics" }, allowSetters = true)
    private Set<FabricMaintenance> maintenances = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_fabric__labels",
        joinColumns = @JoinColumn(name = "fabric_id"),
        inverseJoinColumns = @JoinColumn(name = "labels_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fabrics" }, allowSetters = true)
    private Set<FabricLabels> labels = new HashSet<>();

    @ManyToMany(mappedBy = "fabrics")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "fabrics" }, allowSetters = true)
    private Set<FabricSeller> sellers = new HashSet<>();

    @ManyToMany(mappedBy = "fabrics")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patron", "fabrics" }, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Fabric id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Fabric name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return this.ref;
    }

    public Fabric ref(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public Boolean getUni() {
        return this.uni;
    }

    public Fabric uni(Boolean uni) {
        this.setUni(uni);
        return this;
    }

    public void setUni(Boolean uni) {
        this.uni = uni;
    }

    public String getBuySize() {
        return this.buySize;
    }

    public Fabric buySize(String buySize) {
        this.setBuySize(buySize);
        return this;
    }

    public void setBuySize(String buySize) {
        this.buySize = buySize;
    }

    public Boolean getElastic() {
        return this.elastic;
    }

    public Fabric elastic(Boolean elastic) {
        this.setElastic(elastic);
        return this;
    }

    public void setElastic(Boolean elastic) {
        this.elastic = elastic;
    }

    public Float getElasticRate() {
        return this.elasticRate;
    }

    public Fabric elasticRate(Float elasticRate) {
        this.setElasticRate(elasticRate);
        return this;
    }

    public void setElasticRate(Float elasticRate) {
        this.elasticRate = elasticRate;
    }

    public Integer getRating() {
        return this.rating;
    }

    public Fabric rating(Integer rating) {
        this.setRating(rating);
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getColorName() {
        return this.colorName;
    }

    public Fabric colorName(String colorName) {
        this.setColorName(colorName);
        return this;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColor1() {
        return this.color1;
    }

    public Fabric color1(String color1) {
        this.setColor1(color1);
        return this;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return this.color2;
    }

    public Fabric color2(String color2) {
        this.setColor2(color2);
        return this;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }

    public String getColor3() {
        return this.color3;
    }

    public Fabric color3(String color3) {
        this.setColor3(color3);
        return this;
    }

    public void setColor3(String color3) {
        this.color3 = color3;
    }

    public Integer getLaize() {
        return this.laize;
    }

    public Fabric laize(Integer laize) {
        this.setLaize(laize);
        return this;
    }

    public void setLaize(Integer laize) {
        this.laize = laize;
    }

    public Float getMeterPrice() {
        return this.meterPrice;
    }

    public Fabric meterPrice(Float meterPrice) {
        this.setMeterPrice(meterPrice);
        return this;
    }

    public void setMeterPrice(Float meterPrice) {
        this.meterPrice = meterPrice;
    }

    public Float getMeterBuy() {
        return this.meterBuy;
    }

    public Fabric meterBuy(Float meterBuy) {
        this.setMeterBuy(meterBuy);
        return this;
    }

    public void setMeterBuy(Float meterBuy) {
        this.meterBuy = meterBuy;
    }

    public Float getMeterInStock() {
        return this.meterInStock;
    }

    public Fabric meterInStock(Float meterInStock) {
        this.setMeterInStock(meterInStock);
        return this;
    }

    public void setMeterInStock(Float meterInStock) {
        this.meterInStock = meterInStock;
    }

    public LocalDate getBuyDate() {
        return this.buyDate;
    }

    public Fabric buyDate(LocalDate buyDate) {
        this.setBuyDate(buyDate);
        return this;
    }

    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }

    public Integer getGramPerMeter() {
        return this.gramPerMeter;
    }

    public Fabric gramPerMeter(Integer gramPerMeter) {
        this.setGramPerMeter(gramPerMeter);
        return this;
    }

    public void setGramPerMeter(Integer gramPerMeter) {
        this.gramPerMeter = gramPerMeter;
    }

    public Integer getSizeMin() {
        return this.sizeMin;
    }

    public Fabric sizeMin(Integer sizeMin) {
        this.setSizeMin(sizeMin);
        return this;
    }

    public void setSizeMin(Integer sizeMin) {
        this.sizeMin = sizeMin;
    }

    public Integer getSizeMax() {
        return this.sizeMax;
    }

    public Fabric sizeMax(Integer sizeMax) {
        this.setSizeMax(sizeMax);
        return this;
    }

    public void setSizeMax(Integer sizeMax) {
        this.sizeMax = sizeMax;
    }

    public byte[] getImage1() {
        return this.image1;
    }

    public Fabric image1(byte[] image1) {
        this.setImage1(image1);
        return this;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public String getImage1ContentType() {
        return this.image1ContentType;
    }

    public Fabric image1ContentType(String image1ContentType) {
        this.image1ContentType = image1ContentType;
        return this;
    }

    public void setImage1ContentType(String image1ContentType) {
        this.image1ContentType = image1ContentType;
    }

    public byte[] getImage2() {
        return this.image2;
    }

    public Fabric image2(byte[] image2) {
        this.setImage2(image2);
        return this;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public String getImage2ContentType() {
        return this.image2ContentType;
    }

    public Fabric image2ContentType(String image2ContentType) {
        this.image2ContentType = image2ContentType;
        return this;
    }

    public void setImage2ContentType(String image2ContentType) {
        this.image2ContentType = image2ContentType;
    }

    public byte[] getImage3() {
        return this.image3;
    }

    public Fabric image3(byte[] image3) {
        this.setImage3(image3);
        return this;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }

    public String getImage3ContentType() {
        return this.image3ContentType;
    }

    public Fabric image3ContentType(String image3ContentType) {
        this.image3ContentType = image3ContentType;
        return this;
    }

    public void setImage3ContentType(String image3ContentType) {
        this.image3ContentType = image3ContentType;
    }

    public FabricEditor getFrom() {
        return this.from;
    }

    public void setFrom(FabricEditor fabricEditor) {
        this.from = fabricEditor;
    }

    public Fabric from(FabricEditor fabricEditor) {
        this.setFrom(fabricEditor);
        return this;
    }

    public Set<FabricTypes> getFabricTypes() {
        return this.fabricTypes;
    }

    public void setFabricTypes(Set<FabricTypes> fabricTypes) {
        this.fabricTypes = fabricTypes;
    }

    public Fabric fabricTypes(Set<FabricTypes> fabricTypes) {
        this.setFabricTypes(fabricTypes);
        return this;
    }

    public Fabric addFabricTypes(FabricTypes fabricTypes) {
        this.fabricTypes.add(fabricTypes);
        fabricTypes.getFabrics().add(this);
        return this;
    }

    public Fabric removeFabricTypes(FabricTypes fabricTypes) {
        this.fabricTypes.remove(fabricTypes);
        fabricTypes.getFabrics().remove(this);
        return this;
    }

    public Set<Materials> getMaterials() {
        return this.materials;
    }

    public void setMaterials(Set<Materials> materials) {
        this.materials = materials;
    }

    public Fabric materials(Set<Materials> materials) {
        this.setMaterials(materials);
        return this;
    }

    public Fabric addMaterials(Materials materials) {
        this.materials.add(materials);
        materials.getFabrics().add(this);
        return this;
    }

    public Fabric removeMaterials(Materials materials) {
        this.materials.remove(materials);
        materials.getFabrics().remove(this);
        return this;
    }

    public Set<FabricUses> getUses() {
        return this.uses;
    }

    public void setUses(Set<FabricUses> fabricUses) {
        this.uses = fabricUses;
    }

    public Fabric uses(Set<FabricUses> fabricUses) {
        this.setUses(fabricUses);
        return this;
    }

    public Fabric addUses(FabricUses fabricUses) {
        this.uses.add(fabricUses);
        fabricUses.getFabrics().add(this);
        return this;
    }

    public Fabric removeUses(FabricUses fabricUses) {
        this.uses.remove(fabricUses);
        fabricUses.getFabrics().remove(this);
        return this;
    }

    public Set<FabricMaintenance> getMaintenances() {
        return this.maintenances;
    }

    public void setMaintenances(Set<FabricMaintenance> fabricMaintenances) {
        this.maintenances = fabricMaintenances;
    }

    public Fabric maintenances(Set<FabricMaintenance> fabricMaintenances) {
        this.setMaintenances(fabricMaintenances);
        return this;
    }

    public Fabric addMaintenances(FabricMaintenance fabricMaintenance) {
        this.maintenances.add(fabricMaintenance);
        fabricMaintenance.getFabrics().add(this);
        return this;
    }

    public Fabric removeMaintenances(FabricMaintenance fabricMaintenance) {
        this.maintenances.remove(fabricMaintenance);
        fabricMaintenance.getFabrics().remove(this);
        return this;
    }

    public Set<FabricLabels> getLabels() {
        return this.labels;
    }

    public void setLabels(Set<FabricLabels> fabricLabels) {
        this.labels = fabricLabels;
    }

    public Fabric labels(Set<FabricLabels> fabricLabels) {
        this.setLabels(fabricLabels);
        return this;
    }

    public Fabric addLabels(FabricLabels fabricLabels) {
        this.labels.add(fabricLabels);
        fabricLabels.getFabrics().add(this);
        return this;
    }

    public Fabric removeLabels(FabricLabels fabricLabels) {
        this.labels.remove(fabricLabels);
        fabricLabels.getFabrics().remove(this);
        return this;
    }

    public Set<FabricSeller> getSellers() {
        return this.sellers;
    }

    public void setSellers(Set<FabricSeller> fabricSellers) {
        if (this.sellers != null) {
            this.sellers.forEach(i -> i.removeFabric(this));
        }
        if (fabricSellers != null) {
            fabricSellers.forEach(i -> i.addFabric(this));
        }
        this.sellers = fabricSellers;
    }

    public Fabric sellers(Set<FabricSeller> fabricSellers) {
        this.setSellers(fabricSellers);
        return this;
    }

    public Fabric addSellers(FabricSeller fabricSeller) {
        this.sellers.add(fabricSeller);
        fabricSeller.getFabrics().add(this);
        return this;
    }

    public Fabric removeSellers(FabricSeller fabricSeller) {
        this.sellers.remove(fabricSeller);
        fabricSeller.getFabrics().remove(this);
        return this;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.removeFabrics(this));
        }
        if (projects != null) {
            projects.forEach(i -> i.addFabrics(this));
        }
        this.projects = projects;
    }

    public Fabric projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public Fabric addProjects(Project project) {
        this.projects.add(project);
        project.getFabrics().add(this);
        return this;
    }

    public Fabric removeProjects(Project project) {
        this.projects.remove(project);
        project.getFabrics().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fabric)) {
            return false;
        }
        return id != null && id.equals(((Fabric) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fabric{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ref='" + getRef() + "'" +
            ", uni='" + getUni() + "'" +
            ", buySize='" + getBuySize() + "'" +
            ", elastic='" + getElastic() + "'" +
            ", elasticRate=" + getElasticRate() +
            ", rating=" + getRating() +
            ", colorName='" + getColorName() + "'" +
            ", color1='" + getColor1() + "'" +
            ", color2='" + getColor2() + "'" +
            ", color3='" + getColor3() + "'" +
            ", laize=" + getLaize() +
            ", meterPrice=" + getMeterPrice() +
            ", meterBuy=" + getMeterBuy() +
            ", meterInStock=" + getMeterInStock() +
            ", buyDate='" + getBuyDate() + "'" +
            ", gramPerMeter=" + getGramPerMeter() +
            ", sizeMin=" + getSizeMin() +
            ", sizeMax=" + getSizeMax() +
            ", image1='" + getImage1() + "'" +
            ", image1ContentType='" + getImage1ContentType() + "'" +
            ", image2='" + getImage2() + "'" +
            ", image2ContentType='" + getImage2ContentType() + "'" +
            ", image3='" + getImage3() + "'" +
            ", image3ContentType='" + getImage3ContentType() + "'" +
            "}";
    }
}
