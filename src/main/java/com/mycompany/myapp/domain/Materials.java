package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Materials.
 */
@Entity
@Table(name = "materials")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Materials implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "web_site")
    private String webSite;

    @Size(max = 1024)
    @Column(name = "description", length = 1024)
    private String description;

    @ManyToMany(mappedBy = "materials")
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

    public Materials id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Materials name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebSite() {
        return this.webSite;
    }

    public Materials webSite(String webSite) {
        this.setWebSite(webSite);
        return this;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getDescription() {
        return this.description;
    }

    public Materials description(String description) {
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
            this.fabrics.forEach(i -> i.removeMaterials(this));
        }
        if (fabrics != null) {
            fabrics.forEach(i -> i.addMaterials(this));
        }
        this.fabrics = fabrics;
    }

    public Materials fabrics(Set<Fabric> fabrics) {
        this.setFabrics(fabrics);
        return this;
    }

    public Materials addFabrics(Fabric fabric) {
        this.fabrics.add(fabric);
        fabric.getMaterials().add(this);
        return this;
    }

    public Materials removeFabrics(Fabric fabric) {
        this.fabrics.remove(fabric);
        fabric.getMaterials().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Materials)) {
            return false;
        }
        return id != null && id.equals(((Materials) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Materials{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", webSite='" + getWebSite() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
