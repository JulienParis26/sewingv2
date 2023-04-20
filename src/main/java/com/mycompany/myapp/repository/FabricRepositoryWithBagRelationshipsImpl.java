package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Fabric;
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
public class FabricRepositoryWithBagRelationshipsImpl implements FabricRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Fabric> fetchBagRelationships(Optional<Fabric> fabric) {
        return fabric
            .map(this::fetchFabricTypes)
            .map(this::fetchMaterials)
            .map(this::fetchUses)
            .map(this::fetchMaintenances)
            .map(this::fetchLabels);
    }

    @Override
    public Page<Fabric> fetchBagRelationships(Page<Fabric> fabrics) {
        return new PageImpl<>(fetchBagRelationships(fabrics.getContent()), fabrics.getPageable(), fabrics.getTotalElements());
    }

    @Override
    public List<Fabric> fetchBagRelationships(List<Fabric> fabrics) {
        return Optional
            .of(fabrics)
            .map(this::fetchFabricTypes)
            .map(this::fetchMaterials)
            .map(this::fetchUses)
            .map(this::fetchMaintenances)
            .map(this::fetchLabels)
            .orElse(Collections.emptyList());
    }

    Fabric fetchFabricTypes(Fabric result) {
        return entityManager
            .createQuery("select fabric from Fabric fabric left join fetch fabric.fabricTypes where fabric is :fabric", Fabric.class)
            .setParameter("fabric", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Fabric> fetchFabricTypes(List<Fabric> fabrics) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, fabrics.size()).forEach(index -> order.put(fabrics.get(index).getId(), index));
        List<Fabric> result = entityManager
            .createQuery(
                "select distinct fabric from Fabric fabric left join fetch fabric.fabricTypes where fabric in :fabrics",
                Fabric.class
            )
            .setParameter("fabrics", fabrics)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Fabric fetchMaterials(Fabric result) {
        return entityManager
            .createQuery("select fabric from Fabric fabric left join fetch fabric.materials where fabric is :fabric", Fabric.class)
            .setParameter("fabric", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Fabric> fetchMaterials(List<Fabric> fabrics) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, fabrics.size()).forEach(index -> order.put(fabrics.get(index).getId(), index));
        List<Fabric> result = entityManager
            .createQuery(
                "select distinct fabric from Fabric fabric left join fetch fabric.materials where fabric in :fabrics",
                Fabric.class
            )
            .setParameter("fabrics", fabrics)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Fabric fetchUses(Fabric result) {
        return entityManager
            .createQuery("select fabric from Fabric fabric left join fetch fabric.uses where fabric is :fabric", Fabric.class)
            .setParameter("fabric", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Fabric> fetchUses(List<Fabric> fabrics) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, fabrics.size()).forEach(index -> order.put(fabrics.get(index).getId(), index));
        List<Fabric> result = entityManager
            .createQuery("select distinct fabric from Fabric fabric left join fetch fabric.uses where fabric in :fabrics", Fabric.class)
            .setParameter("fabrics", fabrics)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Fabric fetchMaintenances(Fabric result) {
        return entityManager
            .createQuery("select fabric from Fabric fabric left join fetch fabric.maintenances where fabric is :fabric", Fabric.class)
            .setParameter("fabric", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Fabric> fetchMaintenances(List<Fabric> fabrics) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, fabrics.size()).forEach(index -> order.put(fabrics.get(index).getId(), index));
        List<Fabric> result = entityManager
            .createQuery(
                "select distinct fabric from Fabric fabric left join fetch fabric.maintenances where fabric in :fabrics",
                Fabric.class
            )
            .setParameter("fabrics", fabrics)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Fabric fetchLabels(Fabric result) {
        return entityManager
            .createQuery("select fabric from Fabric fabric left join fetch fabric.labels where fabric is :fabric", Fabric.class)
            .setParameter("fabric", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Fabric> fetchLabels(List<Fabric> fabrics) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, fabrics.size()).forEach(index -> order.put(fabrics.get(index).getId(), index));
        List<Fabric> result = entityManager
            .createQuery("select distinct fabric from Fabric fabric left join fetch fabric.labels where fabric in :fabrics", Fabric.class)
            .setParameter("fabrics", fabrics)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
