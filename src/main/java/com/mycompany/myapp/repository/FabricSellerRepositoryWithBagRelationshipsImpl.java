package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FabricSeller;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class FabricSellerRepositoryWithBagRelationshipsImpl implements FabricSellerRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<FabricSeller> fetchBagRelationships(Optional<FabricSeller> fabricSeller) {
        return fabricSeller.map(this::fetchFabrics);
    }

    @Override
    public Page<FabricSeller> fetchBagRelationships(Page<FabricSeller> fabricSellers) {
        return new PageImpl<>(
            fetchBagRelationships(fabricSellers.getContent()),
            fabricSellers.getPageable(),
            fabricSellers.getTotalElements()
        );
    }

    @Override
    public List<FabricSeller> fetchBagRelationships(List<FabricSeller> fabricSellers) {
        return Optional.of(fabricSellers).map(this::fetchFabrics).orElse(Collections.emptyList());
    }

    FabricSeller fetchFabrics(FabricSeller result) {
        return entityManager
            .createQuery(
                "select fabricSeller from FabricSeller fabricSeller left join fetch fabricSeller.fabrics where fabricSeller is :fabricSeller",
                FabricSeller.class
            )
            .setParameter("fabricSeller", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<FabricSeller> fetchFabrics(List<FabricSeller> fabricSellers) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, fabricSellers.size()).forEach(index -> order.put(fabricSellers.get(index).getId(), index));
        List<FabricSeller> result = entityManager
            .createQuery(
                "select distinct fabricSeller from FabricSeller fabricSeller left join fetch fabricSeller.fabrics where fabricSeller in :fabricSellers",
                FabricSeller.class
            )
            .setParameter("fabricSellers", fabricSellers)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
