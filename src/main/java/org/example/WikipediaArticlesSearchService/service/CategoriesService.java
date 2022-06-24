package org.example.WikipediaArticlesSearchService.service;

import org.example.WikipediaArticlesSearchService.model.Category;
import org.example.WikipediaArticlesSearchService.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Service
public class CategoriesService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    CategoriesRepository categoriesRepository;


    public boolean saveCategory(Category category) {
        Category categoryFromDb = categoriesRepository.findByName(category.getName());

        if (categoryFromDb != null) {
            return false;
        }

        categoriesRepository.save(category);
        return true;
    }

    public Category findCategoryByName(String name) {
        Category category = categoriesRepository.findByName(name);

        if (category == null) {
            throw new EntityNotFoundException("Category not found");
        }

        return category;
    }
}
