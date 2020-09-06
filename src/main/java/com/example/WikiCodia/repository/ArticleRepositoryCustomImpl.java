package com.example.WikiCodia.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.WikiCodia.model.Article;

public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Article> findArticleWithPreferences(String query) 
	{
		
		
		System.out.println(query);
		
        List<Article> listeGlobale = new ArrayList<Article>();
        
        
        listeGlobale = entityManager.createQuery(query).setMaxResults(20).getResultList();
	
		return listeGlobale;
	}

	@Override
	public List<Article> findNewerArticles() {
		
		List<Article> listeGlobale = new ArrayList<Article>();
		
		listeGlobale = entityManager.createQuery("select article from Article article order by article.dateCreation desc").setMaxResults(20).getResultList();
		
		return listeGlobale;
	}

	
}