package org.example.WikipediaArticlesSearchService.service;

import org.example.WikipediaArticlesSearchService.model.AuxiliaryText;
import org.example.WikipediaArticlesSearchService.repository.AuxiliaryTextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuxiliaryTextService {
    @Autowired
    AuxiliaryTextRepository auxiliaryTextRepository;

    public boolean saveAuxiliaryText(AuxiliaryText auxiliaryText) {
//        AuxiliaryText auxiliaryTextFromDB = auxiliaryTextRepository.findByName(auxiliaryText.getName());
//
//        if (auxiliaryTextFromDB != null) {
//            return false;
//        }

        auxiliaryTextRepository.save(auxiliaryText);
        return true;
    }
}
