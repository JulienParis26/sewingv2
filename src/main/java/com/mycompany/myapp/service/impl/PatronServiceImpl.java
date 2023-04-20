package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Patron;
import com.mycompany.myapp.repository.PatronRepository;
import com.mycompany.myapp.service.PatronService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Patron}.
 */
@Service
@Transactional
public class PatronServiceImpl implements PatronService {

    private final Logger log = LoggerFactory.getLogger(PatronServiceImpl.class);

    private final PatronRepository patronRepository;

    public PatronServiceImpl(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    @Override
    public Patron save(Patron patron) {
        log.debug("Request to save Patron : {}", patron);
        return patronRepository.save(patron);
    }

    @Override
    public Patron update(Patron patron) {
        log.debug("Request to update Patron : {}", patron);
        return patronRepository.save(patron);
    }

    @Override
    public Optional<Patron> partialUpdate(Patron patron) {
        log.debug("Request to partially update Patron : {}", patron);

        return patronRepository
            .findById(patron.getId())
            .map(existingPatron -> {
                if (patron.getName() != null) {
                    existingPatron.setName(patron.getName());
                }
                if (patron.getRef() != null) {
                    existingPatron.setRef(patron.getRef());
                }
                if (patron.getType() != null) {
                    existingPatron.setType(patron.getType());
                }
                if (patron.getSexe() != null) {
                    existingPatron.setSexe(patron.getSexe());
                }
                if (patron.getBuyDate() != null) {
                    existingPatron.setBuyDate(patron.getBuyDate());
                }
                if (patron.getPublicationDate() != null) {
                    existingPatron.setPublicationDate(patron.getPublicationDate());
                }
                if (patron.getCreator() != null) {
                    existingPatron.setCreator(patron.getCreator());
                }
                if (patron.getDifficultLevel() != null) {
                    existingPatron.setDifficultLevel(patron.getDifficultLevel());
                }
                if (patron.getFabricQualification() != null) {
                    existingPatron.setFabricQualification(patron.getFabricQualification());
                }
                if (patron.getRequiredFootage() != null) {
                    existingPatron.setRequiredFootage(patron.getRequiredFootage());
                }
                if (patron.getRequiredLaize() != null) {
                    existingPatron.setRequiredLaize(patron.getRequiredLaize());
                }
                if (patron.getClothingType() != null) {
                    existingPatron.setClothingType(patron.getClothingType());
                }
                if (patron.getPrice() != null) {
                    existingPatron.setPrice(patron.getPrice());
                }
                if (patron.getPictureTechnicalDrawing() != null) {
                    existingPatron.setPictureTechnicalDrawing(patron.getPictureTechnicalDrawing());
                }
                if (patron.getPictureTechnicalDrawingContentType() != null) {
                    existingPatron.setPictureTechnicalDrawingContentType(patron.getPictureTechnicalDrawingContentType());
                }
                if (patron.getCarriedPicture1() != null) {
                    existingPatron.setCarriedPicture1(patron.getCarriedPicture1());
                }
                if (patron.getCarriedPicture1ContentType() != null) {
                    existingPatron.setCarriedPicture1ContentType(patron.getCarriedPicture1ContentType());
                }
                if (patron.getCarriedPicture2() != null) {
                    existingPatron.setCarriedPicture2(patron.getCarriedPicture2());
                }
                if (patron.getCarriedPicture2ContentType() != null) {
                    existingPatron.setCarriedPicture2ContentType(patron.getCarriedPicture2ContentType());
                }

                return existingPatron;
            })
            .map(patronRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Patron> findAll(Pageable pageable) {
        log.debug("Request to get all Patrons");
        return patronRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patron> findOne(Long id) {
        log.debug("Request to get Patron : {}", id);
        return patronRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Patron : {}", id);
        patronRepository.deleteById(id);
    }
}
