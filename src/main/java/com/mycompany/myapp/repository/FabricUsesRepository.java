package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FabricUses;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FabricUses entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FabricUsesRepository extends JpaRepository<FabricUses, Long> {}
