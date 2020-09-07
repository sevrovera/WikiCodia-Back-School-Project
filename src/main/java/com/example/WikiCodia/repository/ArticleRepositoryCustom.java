package com.example.WikiCodia.repository;

import java.sql.SQLException;
import java.util.List;

import com.example.WikiCodia.model.Article;

public interface ArticleRepositoryCustom {
	List<Article> findArticleWithPreferences(String query);
	
	List<Article> findNewerArticles();
	
	void deleteFromFavorites(Long userId , Long articleId) throws SQLException;
}
