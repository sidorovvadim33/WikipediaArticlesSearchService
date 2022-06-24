package org.example.WikipediaArticlesSearchService.service;

import org.example.WikipediaArticlesSearchService.model.Article;
import org.example.WikipediaArticlesSearchService.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@Service
public class ArticleService {
    @PersistenceContext
    private EntityManager em;
    @Autowired
    ArticlesRepository articlesRepository;

    public boolean saveArticle(Article article) {
        Article articleFromDB = articlesRepository.findByTitle(article.getTitle());

        if (articleFromDB != null) {
            return false;
        }

        articlesRepository.save(article);
        return true;
    }


    public Article findArticleByTitle(String title) {
        Article article = articlesRepository.findByTitle(title);

        if (article == null) {
            throw new EntityNotFoundException("Article not found");
        }
        return article;
    }

    public Article findArticleByTitleIgnoreCase(String title) {
        Article article = articlesRepository.findByTitleIgnoreCase(title);

        if (article == null) {
            throw new EntityNotFoundException("Article not found");
        }
        return article;
    }
}
