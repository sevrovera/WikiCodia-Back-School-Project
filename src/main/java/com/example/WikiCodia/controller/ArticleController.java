package com.example.WikiCodia.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.WikiCodia.model.Article;
import com.example.WikiCodia.repository.ArticleRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/articles")
public class ArticleController {

	@Autowired
	ArticleRepository articleRepository;

	@GetMapping("/all")
	public ResponseEntity<List<Article>> getAllArticles(@RequestParam(required = false) String titre) {
		try {
			List<Article> articles = new ArrayList<Article>();

			if (titre == null)
				articleRepository.findAll().forEach(articles::add);
			else
				articleRepository.findByTitreContaining(titre).forEach(articles::add);

			if (articles.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(articles, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable("id") long id) {
		Optional<Article> articleData = articleRepository.findById(id);

		if (articleData.isPresent()) {
			return new ResponseEntity<>(articleData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	/*
	@PostMapping("/creation")
	public ResponseEntity<Article> createArticle(@RequestBody Article article) {
		try {
			Article _article = articleRepository.save(new Article(article.getTitre(), article.getDescription(),
					article.getContenu(), LocalDate.now(), article.getDateDerniereModif(), article.getEstPublie(),
					article.getEstPromu(), null, article.getLangage(), article.getFramework(), article.getAuteur(),
					article.getType(), article.getCategorie()));
			return new ResponseEntity<>(_article, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	*/

	@PostMapping("/creation")
	@ResponseBody
	public Article createArticle(@RequestBody Article a) {
		articleRepository.save(a);
		return a;
	}

	@PutMapping("/modification/{id}")
	public ResponseEntity<Article> updateArticle(@PathVariable("id") long id, @RequestBody Article articleUpdated) {
		Optional<Article> articleData = articleRepository.findById(id);

		if (articleData.isPresent()) {

			Article _article = articleData.get();

			if (articleUpdated.getTitre() != null) {
				_article.setTitre(articleUpdated.getTitre());
			}
			if (articleUpdated.getDescription() != null) {
				_article.setDescription(articleUpdated.getDescription());
			}
			if (articleUpdated.getContenu() != null) {
				_article.setContenu(articleUpdated.getContenu());
			}
			if (articleUpdated.getDateDerniereModif() != null) {
				_article.setDateDerniereModif(LocalDate.now());
			}
			if (articleUpdated.getEstPublie()) {
				_article.setEstPublie(articleUpdated.getEstPublie());
			} else {
				_article.setEstPublie(false);
			}
			if (articleUpdated.getEstPromu()) {
				_article.setEstPromu(articleUpdated.getEstPromu());
			} else {
				_article.setEstPromu(false);
			}
			if (articleUpdated.getVote() != null) {
				_article.setVote(articleUpdated.getVote());
			}
			if (articleUpdated.getLangage() != null) {
				_article.setLangage(articleUpdated.getLangage());
			}
			if (articleUpdated.getFramework() != null) {
				_article.setFramework(articleUpdated.getFramework());
			}
			if (articleUpdated.getType() != null) {
				_article.setType(articleUpdated.getType());
			}
			if (articleUpdated.getCategorie() != null) {
				_article.setCategorie(articleUpdated.getCategorie());
			}

			return new ResponseEntity<>(articleRepository.save(_article), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/suppression/{id}")
	public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") long id) {
		try {
			articleRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	@DeleteMapping("/suppression/all")
	public ResponseEntity<HttpStatus> deleteAllArticles() {
		try {
			articleRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}

	}

}
