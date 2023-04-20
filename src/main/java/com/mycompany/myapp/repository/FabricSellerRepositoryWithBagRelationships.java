package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.FabricSeller;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface FabricSellerRepositoryWithBagRelationships {
    Optional<FabricSeller> fetchBagRelationships(Optional<FabricSeller> fabricSeller);

    List<FabricSeller> fetchBagRelationships(List<FabricSeller> fabricSellers);

    Page<FabricSeller> fetchBagRelationships(Page<FabricSeller> fabricSellers);
}
