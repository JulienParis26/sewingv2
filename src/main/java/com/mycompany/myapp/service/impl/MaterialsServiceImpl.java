package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Materials;
import com.mycompany.myapp.repository.MaterialsRepository;
import com.mycompany.myapp.service.MaterialsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Materials}.
 */
@Service
@Transactional
public class MaterialsServiceImpl implements MaterialsService {

    private final Logger log = LoggerFactory.getLogger(MaterialsServiceImpl.class);

    private final MaterialsRepository materialsRepository;

    public MaterialsServiceImpl(MaterialsRepository materialsRepository) {
        this.materialsRepository = materialsRepository;
    }

    @Override
    public Materials save(Materials materials) {
        log.debug("Request to save Materials : {}", materials);
        return materialsRepository.save(materials);
    }

    @Override
    public Materials update(Materials materials) {
        log.debug("Request to update Materials : {}", materials);
        return materialsRepository.save(materials);
    }

    @Override
    public Optional<Materials> partialUpdate(Materials materials) {
        log.debug("Request to partially update Materials : {}", materials);

        return materialsRepository
            .findById(materials.getId())
            .map(existingMaterials -> {
                if (materials.getName() != null) {
                    existingMaterials.setName(materials.getName());
                }
                if (materials.getWebSite() != null) {
                    existingMaterials.setWebSite(materials.getWebSite());
                }
                if (materials.getDescription() != null) {
                    existingMaterials.setDescription(materials.getDescription());
                }

                return existingMaterials;
            })
            .map(materialsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Materials> findAll() {
        log.debug("Request to get all Materials");
        return materialsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Materials> findOne(Long id) {
        log.debug("Request to get Materials : {}", id);
        return materialsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Materials : {}", id);
        materialsRepository.deleteById(id);
    }
}
