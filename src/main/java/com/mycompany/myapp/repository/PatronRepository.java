package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Patron;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Patron entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {}
