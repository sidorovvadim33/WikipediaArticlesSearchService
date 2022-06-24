package org.example.WikipediaArticlesSearchService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.example.WikipediaArticlesSearchService.model.Article;
import org.example.WikipediaArticlesSearchService.service.ArticleService;
import org.example.WikipediaArticlesSearchService.service.JsonToDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DefaultController {
    @Autowired
    private JsonToDatabaseService jsonToDatabaseService;

    @Autowired
    private ArticleService articleService;

    @GetMapping("/wiki/{name}")
    public ModelAndView getArticle(Model model, @PathVariable("name") String name, HttpServletRequest request) {
        ObjectWriter ow = new ObjectMapper().writer();
        String json = "Ошибка";
        Article article = articleService.findArticleByTitleIgnoreCase(name);

        if (request.getParameterMap().containsKey("pretty")) {
            try {
                json = ow.withDefaultPrettyPrinter().writeValueAsString(article);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                json = ow.writeValueAsString(article);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");

        model.addAttribute("jsonText", json);

        return modelAndView;
    }

    @GetMapping("/parseJsonToDB")
    public String parsing() {
        jsonToDatabaseService.addAllDataFromJsonToDB();
        return "Парсинг закончился";
    }

}
