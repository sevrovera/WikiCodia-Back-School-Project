package com.example.WikiCodia.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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
import com.example.WikiCodia.model.Categorie;
import com.example.WikiCodia.model.Etat;
import com.example.WikiCodia.model.Framework;
import com.example.WikiCodia.model.Guilde;
import com.example.WikiCodia.model.Langage;
import com.example.WikiCodia.model.Role;
import com.example.WikiCodia.model.Type;
import com.example.WikiCodia.model.Utilisateur;
import com.example.WikiCodia.model.Vote;
import com.example.WikiCodia.repository.ArticleRepository;
import com.example.WikiCodia.repository.CategorieRepository;
import com.example.WikiCodia.repository.EtatRepository;
import com.example.WikiCodia.repository.FrameworkRepository;
import com.example.WikiCodia.repository.GuildeRepository;
import com.example.WikiCodia.repository.LangageRepository;
import com.example.WikiCodia.repository.RoleRepository;
import com.example.WikiCodia.repository.TypeRepository;
import com.example.WikiCodia.repository.UtilisateurRepository;
import com.example.WikiCodia.repository.VoteRepository;
import com.example.WikiCodia.service.ArticleService;

@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@RequestMapping("/articles")
public class ArticleController {

	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired
	GuildeRepository guildeRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	TypeRepository typeRepository;
	
	@Autowired
	UtilisateurRepository utilisateurRepository;
	
	
	@Autowired
	VoteRepository voteRepository;
	
	@Autowired
	LangageRepository langageRepository;
	
	
	@Autowired
	FrameworkRepository frameworkRepository;
	
	
	@Autowired
	CategorieRepository categorieRepository;
	
	
	@Autowired
	EtatRepository etatRepository;

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
//	@ResponseBody
	public ResponseEntity<Article> createArticle(@RequestBody Article article) {
		
		try {
			Article a = new Article();
			
			a.setContenu(article.getContenu());
			a.setDateCreation(LocalDate.now());
			a.setDateDerniereModif(LocalDate.now());
			a.setDescription(article.getDescription());
			a.setEstPromu(false);
			a.setEstPublie(article.getEstPublie());
			a.setTitre(article.getTitre());
			
			//suppose qu'on ne peut pas voter pour son propre article 
			a.setVote(null);

			//suppose que seuls les types, catégories, languages, framework, versions ... enregistrés préalablements sont valides et disponibles pour la création d'un article
			List<Framework> listFram = new ArrayList<Framework>();
			for (Framework frameworkItere : article.getFramework()) {
				listFram.add(frameworkRepository.findByFrameworkEquals(frameworkItere.getFramework()));
				// listFram.add(frameworkRepository.findByFrameworkAndVersionEquals(frameworkItere.getFramework(), frameworkItere.getVersion()));
			}
			a.setFramework(listFram);
			
			List<Langage> listLang = new ArrayList<Langage>();
			for (Langage langageItere : article.getLangage()) {
				listLang.add(langageRepository.findByLangEquals(langageItere.getLang()));
				// listLang.add(langageRepository.findByLangAndVersionEquals(langageItere.getLang(), langageItere.getVersion()));
			}
			a.setLangage(listLang);
			
			a.setCategorie(categorieRepository.findByLibCategorieEquals(article.getCategorie().getLibCategorie()));
			
			a.setType(typeRepository.findByLibTypeEquals(article.getType().getLibType()));
			
			// a.setAuteur(utilisateurRepository.findByPseudoEquals(article.getAuteur().getPseudo()));
			// a.setAuteur(utilisateurRepository.findByMailAndPseudoEquals(article.getAuteur().getMail(), article.getAuteur().getPseudo()));
			
			articleRepository.save(a);
			
			return new ResponseEntity<>(a, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}


// METHODE POUR IMPLEMENTER LA BDD SI ELLE EST VIDE
	
//	@PostMapping("/creation")
//	@ResponseBody
//	public Article createArticle() {
//		
//		Guilde guilde = new Guilde();
//		guilde.setGuilde("nom de la guilde");
//		guildeRepository.save(guilde);
//		
//		Role role = new Role();
//		role.setRole("role");
//		roleRepository.save(role);
//		
//		Type type = new Type();
//		type.setLibType("libType");
//		typeRepository.save(type);
//		List<Type> types = new ArrayList<Type>();
//		types.add(type);
//		
//		Etat etat = new Etat();
//		etat.setEtat("etat");
//		etatRepository.save(etat);
//		
//		Langage langage = new Langage();
//		langage.setLang("langage");
//		langage.setVersion("version langage");
//		langageRepository.save(langage);
//		List<Langage> langages = new ArrayList<Langage>();
//		langages.add(langage);
//		
//		Framework framework = new Framework();
//		framework.setFramework("framework");
//		framework.setVersion("version framework");
//		frameworkRepository.save(framework);
//		List<Framework> frameworks = new ArrayList<Framework>();
//		frameworks.add(framework);
//		
//		Categorie categorie = new Categorie();
//		categorie.setLibCategorie("libCategorie");
//		categorieRepository.save(categorie);
//		
//		Utilisateur utilisateur = new Utilisateur();
//		utilisateur.setCategorie(new ArrayList<Categorie>());
//		utilisateur.setDateDerniereConnexion(LocalDate.now());
//		utilisateur.setDateInscription(LocalDate.now());
//		utilisateur.setEtat(etat);
//		utilisateur.setFramework(new ArrayList<Framework>());
//		utilisateur.setGuilde(new ArrayList<Guilde>());
//		utilisateur.setLangage(new ArrayList<Langage>());
//		utilisateur.setLienLinkedin("lienLinkedin");
//		utilisateur.setMail("mail@test.fr");
//		utilisateur.setMotDePasse("motDePasse");
//		utilisateur.setNom("nom utilisateur");
//		utilisateur.setPrenom("prenom");
//		utilisateur.setPseudo("pseudo");
//		utilisateur.setRole(role);
//		utilisateur.setStatut("statut");
//		utilisateur.setType(types);
//		utilisateurRepository.save(utilisateur);
//				
//		Vote vote = new Vote();
//		vote.setCommentaire("commentaire de test");
//		vote.setUtilisateur(utilisateur);
//		voteRepository.save(vote);
//		List<Vote> votes = new ArrayList<Vote>();
//		votes.add(vote);
//		
//		
//		Article article = new Article();
//		article.setAuteur(utilisateur);
//		article.setCategorie(categorie);
//		article.setContenu("contenu texte de l'article");
//		article.setDateCreation(LocalDate.now());
//		article.setDateDerniereModif(LocalDate.now());
//		article.setDescription("texte de description de l'article");
//		article.setFramework(frameworks);
//		article.setLangage(langages);
//		article.setTitre("titre");
//		article.setType(type);
//		article.setVote(null);
//		
//		articleRepository.save(article);
//		return article;
//	}
	

	
	
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
