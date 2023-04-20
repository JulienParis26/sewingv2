package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Fabric;
import com.mycompany.myapp.repository.FabricRepository;
import com.mycompany.myapp.service.FabricService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Fabric}.
 */
@Service
@Transactional
public class FabricServiceImpl implements FabricService {

    private final Logger log = LoggerFactory.getLogger(FabricServiceImpl.class);

    private final FabricRepository fabricRepository;

    public FabricServiceImpl(FabricRepository fabricRepository) {
        this.fabricRepository = fabricRepository;
    }

    @Override
    public Fabric save(Fabric fabric) {
        log.debug("Request to save Fabric : {}", fabric);
        return fabricRepository.save(fabric);
    }

    @Override
    public Fabric update(Fabric fabric) {
        log.debug("Request to update Fabric : {}", fabric);
        return fabricRepository.save(fabric);
    }

    @Override
    public Optional<Fabric> partialUpdate(Fabric fabric) {
        log.debug("Request to partially update Fabric : {}", fabric);

        return fabricRepository
            .findById(fabric.getId())
            .map(existingFabric -> {
                if (fabric.getName() != null) {
                    existingFabric.setName(fabric.getName());
                }
                if (fabric.getRef() != null) {
                    existingFabric.setRef(fabric.getRef());
                }
                if (fabric.getUni() != null) {
                    existingFabric.setUni(fabric.getUni());
                }
                if (fabric.getBuySize() != null) {
                    existingFabric.setBuySize(fabric.getBuySize());
                }
                if (fabric.getElastic() != null) {
                    existingFabric.setElastic(fabric.getElastic());
                }
                if (fabric.getElasticRate() != null) {
                    existingFabric.setElasticRate(fabric.getElasticRate());
                }
                if (fabric.getRating() != null) {
                    existingFabric.setRating(fabric.getRating());
                }
                if (fabric.getColorName() != null) {
                    existingFabric.setColorName(fabric.getColorName());
                }
                if (fabric.getColor1() != null) {
                    existingFabric.setColor1(fabric.getColor1());
                }
                if (fabric.getColor2() != null) {
                    existingFabric.setColor2(fabric.getColor2());
                }
                if (fabric.getColor3() != null) {
                    existingFabric.setColor3(fabric.getColor3());
                }
                if (fabric.getLaize() != null) {
                    existingFabric.setLaize(fabric.getLaize());
                }
                if (fabric.getMeterPrice() != null) {
                    existingFabric.setMeterPrice(fabric.getMeterPrice());
                }
                if (fabric.getMeterBuy() != null) {
                    existingFabric.setMeterBuy(fabric.getMeterBuy());
                }
                if (fabric.getMeterInStock() != null) {
                    existingFabric.setMeterInStock(fabric.getMeterInStock());
                }
                if (fabric.getBuyDate() != null) {
                    existingFabric.setBuyDate(fabric.getBuyDate());
                }
                if (fabric.getGramPerMeter() != null) {
                    existingFabric.setGramPerMeter(fabric.getGramPerMeter());
                }
                if (fabric.getSizeMin() != null) {
                    existingFabric.setSizeMin(fabric.getSizeMin());
                }
                if (fabric.getSizeMax() != null) {
                    existingFabric.setSizeMax(fabric.getSizeMax());
                }
                if (fabric.getImage1() != null) {
                    existingFabric.setImage1(fabric.getImage1());
                }
                if (fabric.getImage1ContentType() != null) {
                    existingFabric.setImage1ContentType(fabric.getImage1ContentType());
                }
                if (fabric.getImage2() != null) {
                    existingFabric.setImage2(fabric.getImage2());
                }
                if (fabric.getImage2ContentType() != null) {
                    existingFabric.setImage2ContentType(fabric.getImage2ContentType());
                }
                if (fabric.getImage3() != null) {
                    existingFabric.setImage3(fabric.getImage3());
                }
                if (fabric.getImage3ContentType() != null) {
                    existingFabric.setImage3ContentType(fabric.getImage3ContentType());
                }

                return existingFabric;
            })
            .map(fabricRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Fabric> findAll(Pageable pageable) {
        log.debug("Request to get all Fabrics");
        return fabricRepository.findAll(pageable);
    }

    public Page<Fabric> findAllWithEagerRelationships(Pageable pageable) {
        return fabricRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Fabric> findOne(Long id) {
        log.debug("Request to get Fabric : {}", id);
        return fabricRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fabric : {}", id);
        fabricRepository.deleteById(id);
    }
}
