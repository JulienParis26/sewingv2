package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Fabric;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Fabric entity.
 *
 * When extending this class, extend FabricRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface FabricRepository extends FabricRepositoryWithBagRelationships, JpaRepository<Fabric, Long> {
    default Optional<Fabric> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Fabric> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Fabric> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select distinct fabric from Fabric fabric left join fetch fabric.from",
        countQuery = "select count(distinct fabric) from Fabric fabric"
    )
    Page<Fabric> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct fabric from Fabric fabric left join fetch fabric.from")
    List<Fabric> findAllWithToOneRelationships();

    @Query("select fabric from Fabric fabric left join fetch fabric.from where fabric.id =:id")
    Optional<Fabric> findOneWithToOneRelationships(@Param("id") Long id);
}
