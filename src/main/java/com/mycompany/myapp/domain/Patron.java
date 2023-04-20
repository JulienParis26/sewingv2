package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.myapp.domain.enumeration.Category;
import com.mycompany.myapp.domain.enumeration.DifficultLevel;
import com.mycompany.myapp.domain.enumeration.PatronType;
import com.mycompany.myapp.domain.enumeration.Qualification;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Patron.
 */
@Entity
@Table(name = "patron")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Patron implements Serializable {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PatronType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexe")
    private Category sexe;

    @Column(name = "buy_date")
    private LocalDate buyDate;

    @Column(name = "publication_date")
    private LocalDate publicationDate;

    @Column(name = "creator")
    private String creator;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficult_level")
    private DifficultLevel difficultLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "fabric_qualification")
    private Qualification fabricQualification;

    @Column(name = "required_footage")
    private String requiredFootage;

    @Column(name = "required_laize")
    private String requiredLaize;

    @Column(name = "clothing_type")
    private String clothingType;

    @Column(name = "price")
    private String price;

    @Lob
    @Column(name = "picture_technical_drawing")
    private byte[] pictureTechnicalDrawing;

    @Column(name = "picture_technical_drawing_content_type")
    private String pictureTechnicalDrawingContentType;

    @Lob
    @Column(name = "carried_picture_1", nullable = false)
    private byte[] carriedPicture1;

    @NotNull
    @Column(name = "carried_picture_1_content_type", nullable = false)
    private String carriedPicture1ContentType;

    @Lob
    @Column(name = "carried_picture_2")
    private byte[] carriedPicture2;

    @Column(name = "carried_picture_2_content_type")
    private String carriedPicture2ContentType;

    @OneToMany(mappedBy = "patron")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "patron", "fabrics" }, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Patron id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Patron name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return this.ref;
    }

    public Patron ref(String ref) {
        this.setRef(ref);
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public PatronType getType() {
        return this.type;
    }

    public Patron type(PatronType type) {
        this.setType(type);
        return this;
    }

    public void setType(PatronType type) {
        this.type = type;
    }

    public Category getSexe() {
        return this.sexe;
    }

    public Patron sexe(Category sexe) {
        this.setSexe(sexe);
        return this;
    }

    public void setSexe(Category sexe) {
        this.sexe = sexe;
    }

    public LocalDate getBuyDate() {
        return this.buyDate;
    }

    public Patron buyDate(LocalDate buyDate) {
        this.setBuyDate(buyDate);
        return this;
    }

    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }

    public LocalDate getPublicationDate() {
        return this.publicationDate;
    }

    public Patron publicationDate(LocalDate publicationDate) {
        this.setPublicationDate(publicationDate);
        return this;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getCreator() {
        return this.creator;
    }

    public Patron creator(String creator) {
        this.setCreator(creator);
        return this;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public DifficultLevel getDifficultLevel() {
        return this.difficultLevel;
    }

    public Patron difficultLevel(DifficultLevel difficultLevel) {
        this.setDifficultLevel(difficultLevel);
        return this;
    }

    public void setDifficultLevel(DifficultLevel difficultLevel) {
        this.difficultLevel = difficultLevel;
    }

    public Qualification getFabricQualification() {
        return this.fabricQualification;
    }

    public Patron fabricQualification(Qualification fabricQualification) {
        this.setFabricQualification(fabricQualification);
        return this;
    }

    public void setFabricQualification(Qualification fabricQualification) {
        this.fabricQualification = fabricQualification;
    }

    public String getRequiredFootage() {
        return this.requiredFootage;
    }

    public Patron requiredFootage(String requiredFootage) {
        this.setRequiredFootage(requiredFootage);
        return this;
    }

    public void setRequiredFootage(String requiredFootage) {
        this.requiredFootage = requiredFootage;
    }

    public String getRequiredLaize() {
        return this.requiredLaize;
    }

    public Patron requiredLaize(String requiredLaize) {
        this.setRequiredLaize(requiredLaize);
        return this;
    }

    public void setRequiredLaize(String requiredLaize) {
        this.requiredLaize = requiredLaize;
    }

    public String getClothingType() {
        return this.clothingType;
    }

    public Patron clothingType(String clothingType) {
        this.setClothingType(clothingType);
        return this;
    }

    public void setClothingType(String clothingType) {
        this.clothingType = clothingType;
    }

    public String getPrice() {
        return this.price;
    }

    public Patron price(String price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public byte[] getPictureTechnicalDrawing() {
        return this.pictureTechnicalDrawing;
    }

    public Patron pictureTechnicalDrawing(byte[] pictureTechnicalDrawing) {
        this.setPictureTechnicalDrawing(pictureTechnicalDrawing);
        return this;
    }

    public void setPictureTechnicalDrawing(byte[] pictureTechnicalDrawing) {
        this.pictureTechnicalDrawing = pictureTechnicalDrawing;
    }

    public String getPictureTechnicalDrawingContentType() {
        return this.pictureTechnicalDrawingContentType;
    }

    public Patron pictureTechnicalDrawingContentType(String pictureTechnicalDrawingContentType) {
        this.pictureTechnicalDrawingContentType = pictureTechnicalDrawingContentType;
        return this;
    }

    public void setPictureTechnicalDrawingContentType(String pictureTechnicalDrawingContentType) {
        this.pictureTechnicalDrawingContentType = pictureTechnicalDrawingContentType;
    }

    public byte[] getCarriedPicture1() {
        return this.carriedPicture1;
    }

    public Patron carriedPicture1(byte[] carriedPicture1) {
        this.setCarriedPicture1(carriedPicture1);
        return this;
    }

    public void setCarriedPicture1(byte[] carriedPicture1) {
        this.carriedPicture1 = carriedPicture1;
    }

    public String getCarriedPicture1ContentType() {
        return this.carriedPicture1ContentType;
    }

    public Patron carriedPicture1ContentType(String carriedPicture1ContentType) {
        this.carriedPicture1ContentType = carriedPicture1ContentType;
        return this;
    }

    public void setCarriedPicture1ContentType(String carriedPicture1ContentType) {
        this.carriedPicture1ContentType = carriedPicture1ContentType;
    }

    public byte[] getCarriedPicture2() {
        return this.carriedPicture2;
    }

    public Patron carriedPicture2(byte[] carriedPicture2) {
        this.setCarriedPicture2(carriedPicture2);
        return this;
    }

    public void setCarriedPicture2(byte[] carriedPicture2) {
        this.carriedPicture2 = carriedPicture2;
    }

    public String getCarriedPicture2ContentType() {
        return this.carriedPicture2ContentType;
    }

    public Patron carriedPicture2ContentType(String carriedPicture2ContentType) {
        this.carriedPicture2ContentType = carriedPicture2ContentType;
        return this;
    }

    public void setCarriedPicture2ContentType(String carriedPicture2ContentType) {
        this.carriedPicture2ContentType = carriedPicture2ContentType;
    }

    public Set<Project> getProjects() {
        return this.projects;
    }

    public void setProjects(Set<Project> projects) {
        if (this.projects != null) {
            this.projects.forEach(i -> i.setPatron(null));
        }
        if (projects != null) {
            projects.forEach(i -> i.setPatron(this));
        }
        this.projects = projects;
    }

    public Patron projects(Set<Project> projects) {
        this.setProjects(projects);
        return this;
    }

    public Patron addProjects(Project project) {
        this.projects.add(project);
        project.setPatron(this);
        return this;
    }

    public Patron removeProjects(Project project) {
        this.projects.remove(project);
        project.setPatron(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patron)) {
            return false;
        }
        return id != null && id.equals(((Patron) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patron{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", ref='" + getRef() + "'" +
            ", type='" + getType() + "'" +
            ", sexe='" + getSexe() + "'" +
            ", buyDate='" + getBuyDate() + "'" +
            ", publicationDate='" + getPublicationDate() + "'" +
            ", creator='" + getCreator() + "'" +
            ", difficultLevel='" + getDifficultLevel() + "'" +
            ", fabricQualification='" + getFabricQualification() + "'" +
            ", requiredFootage='" + getRequiredFootage() + "'" +
            ", requiredLaize='" + getRequiredLaize() + "'" +
            ", clothingType='" + getClothingType() + "'" +
            ", price='" + getPrice() + "'" +
            ", pictureTechnicalDrawing='" + getPictureTechnicalDrawing() + "'" +
            ", pictureTechnicalDrawingContentType='" + getPictureTechnicalDrawingContentType() + "'" +
            ", carriedPicture1='" + getCarriedPicture1() + "'" +
            ", carriedPicture1ContentType='" + getCarriedPicture1ContentType() + "'" +
            ", carriedPicture2='" + getCarriedPicture2() + "'" +
            ", carriedPicture2ContentType='" + getCarriedPicture2ContentType() + "'" +
            "}";
    }
}
