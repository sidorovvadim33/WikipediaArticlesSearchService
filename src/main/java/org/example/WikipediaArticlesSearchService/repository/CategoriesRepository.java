package org.example.WikipediaArticlesSearchService.repository;

import org.example.WikipediaArticlesSearchService.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
