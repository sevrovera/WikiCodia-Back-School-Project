package com.example.WikiCodia.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.example.WikiCodia.model.Article;



public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom {

	@Autowired
	private Environment env;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Article> findArticleWithPreferences(String query) 
	{
		
		System.out.println(query);		
        List<Article> listeGlobale = new ArrayList<Article>();
        listeGlobale = entityManager.createQuery(query).setMaxResults(20).getResultList();
		return listeGlobale;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Article> findNewerArticles() {
		
		List<Article> listeGlobale = new ArrayList<Article>();
		
		listeGlobale = entityManager.createQuery("select article from Article article order by article.dateCreation desc").setMaxResults(20).getResultList();
		
		return listeGlobale;
	}

	

	@Override
	public void deleteFromFavorites(Long userId, Long articleId) throws SQLException {
		
		String url = env.getProperty("spring.datasource.url");
		String dbUsername = env.getProperty("spring.datasource.username");
		String dbPassword = env.getProperty("spring.datasource.password");
		
		Connection con = DriverManager.getConnection(url, dbUsername, dbPassword);
		String sql = "delete from wikicodia.utilisateur_articles_favoris where utilisateur_id_utilisateur = " + userId + " and articles_favoris_id_article = " + articleId;
		
		try {
			   Statement stmt = con.createStatement();
			   int resultat = stmt.executeUpdate(sql);
			   System.out.println(resultat);
		} catch (SQLException e) {
		   System.out.println(e);
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public List<Article> findLastPromotedArticles(){
		
		List<Article> listeGlobale = new ArrayList<Article>();
		
		listeGlobale = entityManager.createQuery("select article from Article article where article.estPromu = 1 order by article.dateDerniereModif desc").setMaxResults(20).getResultList();
		System.out.println("coucou");
		return listeGlobale;
		
	}

	
}