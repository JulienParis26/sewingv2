package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.FabricUses;
import com.mycompany.myapp.repository.FabricUsesRepository;
import com.mycompany.myapp.service.FabricUsesService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FabricUses}.
 */
@Service
@Transactional
public class FabricUsesServiceImpl implements FabricUsesService {

    private final Logger log = LoggerFactory.getLogger(FabricUsesServiceImpl.class);

    private final FabricUsesRepository fabricUsesRepository;

    public FabricUsesServiceImpl(FabricUsesRepository fabricUsesRepository) {
        this.fabricUsesRepository = fabricUsesRepository;
    }

    @Override
    public FabricUses save(FabricUses fabricUses) {
        log.debug("Request to save FabricUses : {}", fabricUses);
        return fabricUsesRepository.save(fabricUses);
    }

    @Override
    public FabricUses update(FabricUses fabricUses) {
        log.debug("Request to update FabricUses : {}", fabricUses);
        return fabricUsesRepository.save(fabricUses);
    }

    @Override
    public Optional<FabricUses> partialUpdate(FabricUses fabricUses) {
        log.debug("Request to partially update FabricUses : {}", fabricUses);

        return fabricUsesRepository
            .findById(fabricUses.getId())
            .map(existingFabricUses -> {
                if (fabricUses.getName() != null) {
                    existingFabricUses.setName(fabricUses.getName());
                }
                if (fabricUses.getCode() != null) {
                    existingFabricUses.setCode(fabricUses.getCode());
                }

                return existingFabricUses;
            })
            .map(fabricUsesRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricUses> findAll() {
        log.debug("Request to get all FabricUses");
        return fabricUsesRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FabricUses> findOne(Long id) {
        log.debug("Request to get FabricUses : {}", id);
        return fabricUsesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FabricUses : {}", id);
        fabricUsesRepository.deleteById(id);
    }
}
