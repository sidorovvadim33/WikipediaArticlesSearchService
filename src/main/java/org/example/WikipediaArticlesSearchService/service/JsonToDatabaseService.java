package org.example.WikipediaArticlesSearchService.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import org.example.WikipediaArticlesSearchService.model.Article;
import org.example.WikipediaArticlesSearchService.model.AuxiliaryText;
import org.example.WikipediaArticlesSearchService.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

@Service
public class JsonToDatabaseService {
    @Value("${json.path}")
    private String jsonPath;

    @Autowired
    CategoriesService categoriesService;

    @Autowired
    ArticleService articleService;

    @Autowired
    AuxiliaryTextService auxiliaryTextService;
    public boolean addAllDataFromJsonToDB() {
        try {
            addCategories(getJsonStreamParser());
            addArticles(getJsonStreamParser());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private JsonStreamParser getJsonStreamParser() {
        File file = new File(jsonPath);
        InputStream inputStream = null;
        Reader reader = null;
        try {
            inputStream = new FileInputStream(file);
            reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JsonStreamParser parser = new JsonStreamParser(reader);
        return parser;
    }

    private void addArticles(JsonStreamParser p) throws ParseException {
        while (p.hasNext()) {
            JsonElement e = p.next();
            if (e.isJsonObject()) {
                if (e.getAsJsonObject().has("template")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date createTimestamp = sdf.parse(e.getAsJsonObject().get("create_timestamp").getAsString().replaceAll("([A-Z])", " "));

                    Date timestamp = sdf.parse(e.getAsJsonObject().get("timestamp").getAsString().replaceAll("([A-Z])", " "));

                    String language = e.getAsJsonObject().get("language").getAsString().replaceAll("\"", "");
                    String wiki = e.getAsJsonObject().get("wiki").getAsString().replaceAll("\"", "");

                    String title = e.getAsJsonObject().get("title").getAsString().replaceAll("\"", "");
                    Article article = new Article();

                    HashSet<AuxiliaryText> auxiliaryTextHashSet = new HashSet<>();
                    if (e.getAsJsonObject().getAsJsonArray("auxiliary_text") != null) {
                        if (!e.getAsJsonObject().getAsJsonArray("auxiliary_text").isEmpty()) {
                            JsonArray auxiliaryTextJsonArray = e.getAsJsonObject().getAsJsonArray("auxiliary_text");
                            for (int i = 0; i < auxiliaryTextJsonArray.size(); i++) {
                                AuxiliaryText auxiliaryText = new AuxiliaryText();
                                auxiliaryText.setName(auxiliaryTextJsonArray.get(i).getAsString());
                                auxiliaryTextHashSet.add(auxiliaryText);
//                                auxiliaryTextService.saveAuxiliaryText(auxiliaryText);
                            }
                        }
                    }

                    article.setCreateTimestamp(new Timestamp(createTimestamp.getTime()));
                    article.setTimestamp(new Timestamp(timestamp.getTime()));
                    article.setLanguage(language);
                    article.setWiki(wiki);
                    article.setTitle(title);
//                    article.setAuxiliaryText(auxiliaryTextHashSet);

                    HashSet<Category> categories = new HashSet<>();
                    if (!e.getAsJsonObject().getAsJsonArray("category").isEmpty()) {
                        JsonArray jsonArray = e.getAsJsonObject().getAsJsonArray("category");
                        for (int i = 0; i < jsonArray.size(); i++) {

                            Category category = categoriesService.findCategoryByName(jsonArray.get(i).getAsString());
                            if (category != null) {
                                categories.add(category);
                            }
                        }
                        article.setCategories(categories);
                    } else {
                        article.setCategories(null);
                    }

                    articleService.saveArticle(article);

                    auxiliaryTextHashSet.forEach(aux -> {
                        Article articleForAux = articleService.findArticleByTitle(article.getTitle());
                        aux.setArticle(articleForAux);
                        auxiliaryTextService.saveAuxiliaryText(aux);
                    });
                }
            }
        }
    }

    private void addCategories(JsonStreamParser p) throws ParseException {
        while (p.hasNext()) {
            JsonElement e = p.next();
            if (e.isJsonObject()) {
                if (e.getAsJsonObject().has("template")) {

                    if (!e.getAsJsonObject().getAsJsonArray("category").isEmpty()) {
                        JsonArray jsonArray = e.getAsJsonObject().getAsJsonArray("category");
                        for (int i = 0; i < jsonArray.size(); i++) {

                            Category category = new Category();
                            category.setName(jsonArray.get(i).getAsString());
                            categoriesService.saveCategory(category);
                        }
                    }
                }
            }
        }
    }
}