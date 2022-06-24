package org.example.WikipediaArticlesSearchService.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_timestamp")
    private Timestamp createTimestamp;

    private Timestamp timestamp;

    private String language;

    private String wiki;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "articles_categories",
            joinColumns = @JoinColumn(name = "articles_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id"))
    private Set<Category> categories;

    private String title;

    @Column(name = "auxiliary_text")
    @OneToMany(mappedBy = "article")
    private Set<AuxiliaryText> auxiliaryText;
}
