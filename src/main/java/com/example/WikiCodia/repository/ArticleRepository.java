package com.example.WikiCodia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.WikiCodia.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long>{
	List<Article> findByTitreContaining(String titre);
}
