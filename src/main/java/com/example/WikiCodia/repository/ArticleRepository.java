package com.example.WikiCodia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.WikiCodia.model.Article;
import com.example.WikiCodia.model.Utilisateur;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom{
	List<Article> findByTitreContaining(String titre);
	
	@Query("select article from Article article where article.estValide = 0 and article.estPublie = 1")
	List<Article> findByIsPublishedAndNotValidated();
	 
	@Query("Select article from Article article where article.estPromu = 1")
	List<Article> findPromotedArticles();
	
	List<Article> findArticleWithPreferences(String query);
	
	List<Article> findNewerArticles();

	@Query("Select article from Article article where article.estPromu = 1")
	List<Article> findPomotedArticles();
		 
	List<Article> findFirst5ByAuteur(Utilisateur auteur);
}
