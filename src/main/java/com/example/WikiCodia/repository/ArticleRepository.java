package com.example.WikiCodia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.WikiCodia.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{
	List<Article> findByTitreContaining(String titre);
	
	 @Query("select article from Article article where article.estValide = 0 and article.estPublie = 1")
	    List<Article> findByIsPublishedAndNotValidated();
}
