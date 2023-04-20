package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Materials;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Materials entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MaterialsRepository extends JpaRepository<Materials, Long> {}
