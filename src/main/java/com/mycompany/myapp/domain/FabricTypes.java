package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Tissu - type\nBouclette, jersey, bord cote, jean
 */
@Schema(description = "Tissu - type\nBouclette, jersey, bord cote, jean")
@Entity
@Table(name = "fabric_types")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FabricTypes implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 5)
    @Column(name = "code", nullable = false)
    private String code;

    @Size(max = 1024)
    @Column(name = "description", length = 1024)
    private String description;

    @ManyToMany(mappedBy = "fabricTypes")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(
        value = { "from", "fabricTypes", "materials", "uses", "maintenances", "labels", "sellers", "projects" },
        allowSetters = true
    )
    private Set<Fabric> fabrics = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FabricTypes id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public FabricTypes name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public FabricTypes code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public FabricTypes description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Fabric> getFabrics() {
        return this.fabrics;
    }

    public void setFabrics(Set<Fabric> fabrics) {
        if (this.fabrics != null) {
            this.fabrics.forEach(i -> i.removeFabricTypes(this));
        }
        if (fabrics != null) {
            fabrics.forEach(i -> i.addFabricTypes(this));
        }
        this.fabrics = fabrics;
    }

    public FabricTypes fabrics(Set<Fabric> fabrics) {
        this.setFabrics(fabrics);
        return this;
    }

    public FabricTypes addFabrics(Fabric fabric) {
        this.fabrics.add(fabric);
        fabric.getFabricTypes().add(this);
        return this;
    }

    public FabricTypes removeFabrics(Fabric fabric) {
        this.fabrics.remove(fabric);
        fabric.getFabricTypes().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FabricTypes)) {
            return false;
        }
        return id != null && id.equals(((FabricTypes) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FabricTypes{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
