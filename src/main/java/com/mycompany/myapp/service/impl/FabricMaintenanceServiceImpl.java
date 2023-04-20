package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.FabricMaintenance;
import com.mycompany.myapp.repository.FabricMaintenanceRepository;
import com.mycompany.myapp.service.FabricMaintenanceService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FabricMaintenance}.
 */
@Service
@Transactional
public class FabricMaintenanceServiceImpl implements FabricMaintenanceService {

    private final Logger log = LoggerFactory.getLogger(FabricMaintenanceServiceImpl.class);

    private final FabricMaintenanceRepository fabricMaintenanceRepository;

    public FabricMaintenanceServiceImpl(FabricMaintenanceRepository fabricMaintenanceRepository) {
        this.fabricMaintenanceRepository = fabricMaintenanceRepository;
    }

    @Override
    public FabricMaintenance save(FabricMaintenance fabricMaintenance) {
        log.debug("Request to save FabricMaintenance : {}", fabricMaintenance);
        return fabricMaintenanceRepository.save(fabricMaintenance);
    }

    @Override
    public FabricMaintenance update(FabricMaintenance fabricMaintenance) {
        log.debug("Request to update FabricMaintenance : {}", fabricMaintenance);
        return fabricMaintenanceRepository.save(fabricMaintenance);
    }

    @Override
    public Optional<FabricMaintenance> partialUpdate(FabricMaintenance fabricMaintenance) {
        log.debug("Request to partially update FabricMaintenance : {}", fabricMaintenance);

        return fabricMaintenanceRepository
            .findById(fabricMaintenance.getId())
            .map(existingFabricMaintenance -> {
                if (fabricMaintenance.getName() != null) {
                    existingFabricMaintenance.setName(fabricMaintenance.getName());
                }
                if (fabricMaintenance.getCode() != null) {
                    existingFabricMaintenance.setCode(fabricMaintenance.getCode());
                }

                return existingFabricMaintenance;
            })
            .map(fabricMaintenanceRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FabricMaintenance> findAll() {
        log.debug("Request to get all FabricMaintenances");
        return fabricMaintenanceRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FabricMaintenance> findOne(Long id) {
        log.debug("Request to get FabricMaintenance : {}", id);
        return fabricMaintenanceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FabricMaintenance : {}", id);
        fabricMaintenanceRepository.deleteById(id);
    }
}
