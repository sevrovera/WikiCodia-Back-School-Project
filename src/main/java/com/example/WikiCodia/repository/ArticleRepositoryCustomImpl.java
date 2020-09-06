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
        
        //listeGlobale = entityManager.createQuery("Select article from Article article where article.type.libType = 'Java' or article.type.libType = 'Outils utiles'").getResultList();
        listeGlobale = entityManager.createQuery("Select article from Article article where article.langage.lang = 'Java'").getResultList();
        
        
	
		return listeGlobale;
	}

	
}