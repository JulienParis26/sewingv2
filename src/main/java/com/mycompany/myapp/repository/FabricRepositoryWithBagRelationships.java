package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Fabric;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface FabricRepositoryWithBagRelationships {
    Optional<Fabric> fetchBagRelationships(Optional<Fabric> fabric);

    List<Fabric> fetchBagRelationships(List<Fabric> fabrics);

    Page<Fabric> fetchBagRelationships(Page<Fabric> fabrics);
}
