package org.example.WikipediaArticlesSearchService.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String name;

    @Transient
    @ManyToMany(mappedBy = "categories")
    private Set<Article> articles;
}
