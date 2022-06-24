package org.example.WikipediaArticlesSearchService.repository;

import org.example.WikipediaArticlesSearchService.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticlesRepository extends JpaRepository<Article, Long> {
    Article findByTitle(String title);
    Article findByTitleIgnoreCase(String title);
}
