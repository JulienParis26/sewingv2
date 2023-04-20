package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.FabricTypes;
import com.mycompany.myapp.repository.FabricTypesRepository;
import com.mycompany.myapp.service.FabricTypesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FabricTypes}.
 */
@Service
@Transactional
public class FabricTypesServiceImpl implements FabricTypesService {

    private final Logger log = LoggerFactory.getLogger(FabricTypesServiceImpl.class);

    private final FabricTypesRepository fabricTypesRepository;

    public FabricTypesServiceImpl(FabricTypesRepository fabricTypesRepository) {
        this.fabricTypesRepository = fabricTypesRepository;
    }

    @Override
    public FabricTypes save(FabricTypes fabricTypes) {
        log.debug("Request to save FabricTypes : {}", fabricTypes);
        return fabricTypesRepository.save(fabricTypes);
    }

    @Override
    public FabricTypes update(FabricTypes fabricTypes) {
        log.debug("Request to update FabricTypes : {}", fabricTypes);
        return fabricTypesRepository.save(fabricTypes);
    }

    @Override
    public Optional<FabricTypes> partialUpdate(FabricTypes fabricTypes) {
        log.debug("Request to partially update FabricTypes : {}", fabricTypes);

        return fabricTypesRepository
            .findById(fabricTypes.getId())
            .map(existingFabricTypes -> {
                if (fabricTypes.getName() != null) {
                    existingFabricTypes.setName(fabricTypes.getName());
                }
                if (fabricTypes.getCode() != null) {
                    existingFabricTypes.setCode(fabricTypes.getCode());
                }
                if (fabricTypes.getDescription() != null) {
                    existingFabricTypes.setDescription(fabricTypes.getDescription());
                }

                return existingFabricTypes;
            })
            .map(fabricTypesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricTypes> findAll() {
        log.debug("Request to get all FabricTypes");
        return fabricTypesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FabricTypes> findOne(Long id) {
        log.debug("Request to get FabricTypes : {}", id);
        return fabricTypesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FabricTypes : {}", id);
        fabricTypesRepository.deleteById(id);
    }
}
