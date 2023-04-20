package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FabricTypes;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FabricTypes entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FabricTypesRepository extends JpaRepository<FabricTypes, Long> {}
