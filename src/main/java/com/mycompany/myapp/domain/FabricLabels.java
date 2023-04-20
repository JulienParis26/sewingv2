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
 * Tissu - ecotex, got
 */
@Schema(description = "Tissu - ecotex, got")
@Entity
@Table(name = "fabric_labels")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FabricLabels implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Size(min = 3)
    @Column(name = "code", nullable = false)
    private String code;

    @ManyToMany(mappedBy = "labels")
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

    public FabricLabels id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public FabricLabels name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return this.code;
    }

    public FabricLabels code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Fabric> getFabrics() {
        return this.fabrics;
    }

    public void setFabrics(Set<Fabric> fabrics) {
        if (this.fabrics != null) {
            this.fabrics.forEach(i -> i.removeLabels(this));
        }
        if (fabrics != null) {
            fabrics.forEach(i -> i.addLabels(this));
        }
        this.fabrics = fabrics;
    }

    public FabricLabels fabrics(Set<Fabric> fabrics) {
        this.setFabrics(fabrics);
        return this;
    }

    public FabricLabels addFabrics(Fabric fabric) {
        this.fabrics.add(fabric);
        fabric.getLabels().add(this);
        return this;
    }

    public FabricLabels removeFabrics(Fabric fabric) {
        this.fabrics.remove(fabric);
        fabric.getLabels().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FabricLabels)) {
            return false;
        }
        return id != null && id.equals(((FabricLabels) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FabricLabels{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
