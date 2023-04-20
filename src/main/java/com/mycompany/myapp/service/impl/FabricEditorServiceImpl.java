package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.FabricEditor;
import com.mycompany.myapp.repository.FabricEditorRepository;
import com.mycompany.myapp.service.FabricEditorService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link FabricEditor}.
 */
@Service
@Transactional
public class FabricEditorServiceImpl implements FabricEditorService {

    private final Logger log = LoggerFactory.getLogger(FabricEditorServiceImpl.class);

    private final FabricEditorRepository fabricEditorRepository;

    public FabricEditorServiceImpl(FabricEditorRepository fabricEditorRepository) {
        this.fabricEditorRepository = fabricEditorRepository;
    }

    @Override
    public FabricEditor save(FabricEditor fabricEditor) {
        log.debug("Request to save FabricEditor : {}", fabricEditor);
        return fabricEditorRepository.save(fabricEditor);
    }

    @Override
    public FabricEditor update(FabricEditor fabricEditor) {
        log.debug("Request to update FabricEditor : {}", fabricEditor);
        return fabricEditorRepository.save(fabricEditor);
    }

    @Override
    public Optional<FabricEditor> partialUpdate(FabricEditor fabricEditor) {
        log.debug("Request to partially update FabricEditor : {}", fabricEditor);

        return fabricEditorRepository
            .findById(fabricEditor.getId())
            .map(existingFabricEditor -> {
                if (fabricEditor.getName() != null) {
                    existingFabricEditor.setName(fabricEditor.getName());
                }
                if (fabricEditor.getPrintDate() != null) {
                    existingFabricEditor.setPrintDate(fabricEditor.getPrintDate());
                }
                if (fabricEditor.getNumber() != null) {
                    existingFabricEditor.setNumber(fabricEditor.getNumber());
                }
                if (fabricEditor.getEditor() != null) {
                    existingFabricEditor.setEditor(fabricEditor.getEditor());
                }
                if (fabricEditor.getLanguage() != null) {
                    existingFabricEditor.setLanguage(fabricEditor.getLanguage());
                }
                if (fabricEditor.getPrice() != null) {
                    existingFabricEditor.setPrice(fabricEditor.getPrice());
                }
                if (fabricEditor.getImage() != null) {
                    existingFabricEditor.setImage(fabricEditor.getImage());
                }
                if (fabricEditor.getImageContentType() != null) {
                    existingFabricEditor.setImageContentType(fabricEditor.getImageContentType());
                }

                return existingFabricEditor;
            })
            .map(fabricEditorRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FabricEditor> findAll(Pageable pageable) {
        log.debug("Request to get all FabricEditors");
        return fabricEditorRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FabricEditor> findOne(Long id) {
        log.debug("Request to get FabricEditor : {}", id);
        return fabricEditorRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FabricEditor : {}", id);
        fabricEditorRepository.deleteById(id);
    }
}
