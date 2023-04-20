package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.FabricLabels;
import com.mycompany.myapp.repository.FabricLabelsRepository;
import com.mycompany.myapp.service.FabricLabelsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FabricLabels}.
 */
@Service
@Transactional
public class FabricLabelsServiceImpl implements FabricLabelsService {

    private final Logger log = LoggerFactory.getLogger(FabricLabelsServiceImpl.class);

    private final FabricLabelsRepository fabricLabelsRepository;

    public FabricLabelsServiceImpl(FabricLabelsRepository fabricLabelsRepository) {
        this.fabricLabelsRepository = fabricLabelsRepository;
    }

    @Override
    public FabricLabels save(FabricLabels fabricLabels) {
        log.debug("Request to save FabricLabels : {}", fabricLabels);
        return fabricLabelsRepository.save(fabricLabels);
    }

    @Override
    public FabricLabels update(FabricLabels fabricLabels) {
        log.debug("Request to update FabricLabels : {}", fabricLabels);
        return fabricLabelsRepository.save(fabricLabels);
    }

    @Override
    public Optional<FabricLabels> partialUpdate(FabricLabels fabricLabels) {
        log.debug("Request to partially update FabricLabels : {}", fabricLabels);

        return fabricLabelsRepository
            .findById(fabricLabels.getId())
            .map(existingFabricLabels -> {
                if (fabricLabels.getName() != null) {
                    existingFabricLabels.setName(fabricLabels.getName());
                }
                if (fabricLabels.getCode() != null) {
                    existingFabricLabels.setCode(fabricLabels.getCode());
                }

                return existingFabricLabels;
            })
            .map(fabricLabelsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricLabels> findAll() {
        log.debug("Request to get all FabricLabels");
        return fabricLabelsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FabricLabels> findOne(Long id) {
        log.debug("Request to get FabricLabels : {}", id);
        return fabricLabelsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FabricLabels : {}", id);
        fabricLabelsRepository.deleteById(id);
    }
}
