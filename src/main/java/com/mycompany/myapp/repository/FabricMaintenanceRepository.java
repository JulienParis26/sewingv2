package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FabricMaintenance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FabricMaintenance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FabricMaintenanceRepository extends JpaRepository<FabricMaintenance, Long> {}
