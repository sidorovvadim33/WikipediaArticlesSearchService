package org.example.WikipediaArticlesSearchService.repository;

import org.example.WikipediaArticlesSearchService.model.AuxiliaryText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuxiliaryTextRepository extends JpaRepository<AuxiliaryText, Long> {
    AuxiliaryText findByName(String name);
}
