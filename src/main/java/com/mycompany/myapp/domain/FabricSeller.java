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
 * A FabricSeller.
 */
@Entity
@Table(name = "fabric_seller")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FabricSeller implements Serializable {

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

    @ManyToMany
    @JoinTable(
        name = "rel_fabric_seller__fabric",
        joinColumns = @JoinColumn(name = "fabric_seller_id"),
        inverseJoinColumns = @JoinColumn(name = "fabric_id")
    )
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

    public FabricSeller id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public FabricSeller name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebSite() {
        return this.webSite;
    }

    public FabricSeller webSite(String webSite) {
        this.setWebSite(webSite);
        return this;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public String getDescription() {
        return this.description;
    }

    public FabricSeller description(String description) {
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
        this.fabrics = fabrics;
    }

    public FabricSeller fabrics(Set<Fabric> fabrics) {
        this.setFabrics(fabrics);
        return this;
    }

    public FabricSeller addFabric(Fabric fabric) {
        this.fabrics.add(fabric);
        fabric.getSellers().add(this);
        return this;
    }

    public FabricSeller removeFabric(Fabric fabric) {
        this.fabrics.remove(fabric);
        fabric.getSellers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FabricSeller)) {
            return false;
        }
        return id != null && id.equals(((FabricSeller) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FabricSeller{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", webSite='" + getWebSite() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
