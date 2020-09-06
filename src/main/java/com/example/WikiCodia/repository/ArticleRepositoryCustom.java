package com.example.WikiCodia.repository;

import java.util.List;

import com.example.WikiCodia.model.Article;

public interface ArticleRepositoryCustom {
	List<Article> findArticleWithPreferences(String query);
}
