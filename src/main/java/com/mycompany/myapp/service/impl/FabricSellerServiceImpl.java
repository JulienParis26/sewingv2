package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.FabricSeller;
import com.mycompany.myapp.repository.FabricSellerRepository;
import com.mycompany.myapp.service.FabricSellerService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FabricSeller}.
 */
@Service
@Transactional
public class FabricSellerServiceImpl implements FabricSellerService {

    private final Logger log = LoggerFactory.getLogger(FabricSellerServiceImpl.class);

    private final FabricSellerRepository fabricSellerRepository;

    public FabricSellerServiceImpl(FabricSellerRepository fabricSellerRepository) {
        this.fabricSellerRepository = fabricSellerRepository;
    }

    @Override
    public FabricSeller save(FabricSeller fabricSeller) {
        log.debug("Request to save FabricSeller : {}", fabricSeller);
        return fabricSellerRepository.save(fabricSeller);
    }

    @Override
    public FabricSeller update(FabricSeller fabricSeller) {
        log.debug("Request to update FabricSeller : {}", fabricSeller);
        return fabricSellerRepository.save(fabricSeller);
    }

    @Override
    public Optional<FabricSeller> partialUpdate(FabricSeller fabricSeller) {
        log.debug("Request to partially update FabricSeller : {}", fabricSeller);

        return fabricSellerRepository
            .findById(fabricSeller.getId())
            .map(existingFabricSeller -> {
                if (fabricSeller.getName() != null) {
                    existingFabricSeller.setName(fabricSeller.getName());
                }
                if (fabricSeller.getWebSite() != null) {
                    existingFabricSeller.setWebSite(fabricSeller.getWebSite());
                }
                if (fabricSeller.getDescription() != null) {
                    existingFabricSeller.setDescription(fabricSeller.getDescription());
                }

                return existingFabricSeller;
            })
            .map(fabricSellerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricSeller> findAll(Pageable pageable) {
        log.debug("Request to get all FabricSellers");
        return fabricSellerRepository.findAll(pageable);
    }

    public Page<FabricSeller> findAllWithEagerRelationships(Pageable pageable) {
        return fabricSellerRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FabricSeller> findOne(Long id) {
        log.debug("Request to get FabricSeller : {}", id);
        return fabricSellerRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FabricSeller : {}", id);
        fabricSellerRepository.deleteById(id);
    }
}
