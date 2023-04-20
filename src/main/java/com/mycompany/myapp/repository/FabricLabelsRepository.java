package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FabricLabels;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FabricLabels entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FabricLabelsRepository extends JpaRepository<FabricLabels, Long> {}
