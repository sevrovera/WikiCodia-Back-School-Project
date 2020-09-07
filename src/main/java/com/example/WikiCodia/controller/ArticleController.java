package com.example.WikiCodia.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RestController;

import com.example.WikiCodia.model.Article;
import com.example.WikiCodia.model.Categorie;
import com.example.WikiCodia.model.Framework;
import com.example.WikiCodia.model.Langage;
import com.example.WikiCodia.model.Type;
import com.example.WikiCodia.model.Utilisateur;
import com.example.WikiCodia.model.utils.EmailUtils;
import com.example.WikiCodia.repository.ArticleRepository;
import com.example.WikiCodia.repository.CategorieRepository;
import com.example.WikiCodia.repository.EtatRepository;
import com.example.WikiCodia.repository.FrameworkRepository;
import com.example.WikiCodia.repository.GuildeRepository;
import com.example.WikiCodia.repository.LangageRepository;
import com.example.WikiCodia.repository.RoleRepository;
import com.example.WikiCodia.repository.TypeRepository;
import com.example.WikiCodia.repository.UtilisateurRepository;
//import com.example.WikiCodia.repository.VoteRepository;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
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

//	@Autowired
//	VoteRepository voteRepository;

	@Autowired
	LangageRepository langageRepository;

	@Autowired
	FrameworkRepository frameworkRepository;

	@Autowired
	CategorieRepository categorieRepository;

	@Autowired
	EtatRepository etatRepository;

	Map<Long, Article> articles;

	@GetMapping("/all")
	public ResponseEntity<List<Article>> getAllArticles(@RequestParam(required = false) String titre) {
		try {
			List<Article> articles = new ArrayList<Article>();

			if (titre == null) {
				articleRepository.findAll().forEach(articles::add);
			} else {
				articleRepository.findByTitreContaining(titre).forEach(articles::add);
			}

			if (articles.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			Optional<Utilisateur> util = utilisateurRepository.findById((long) 2);
			Utilisateur u = util.get();
			u.setArticlesFavoris(articles);
			utilisateurRepository.save(u);

			return new ResponseEntity<>(articles, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// methode useless en vrai qui sert juste a ajouter tous les article en favoris
	// à l'utilisateur 2
	@GetMapping("/alltofav")
	public void getAllArticlestofavorite() {
		List<Article> articles = new ArrayList<Article>();

		for (Article ar : articleRepository.findAll()) {
			articles.add(ar);
		}

		Optional<Utilisateur> util = utilisateurRepository.findById((long) 2);
		Utilisateur u = util.get();
		u.setArticlesFavoris(articles);
		utilisateurRepository.save(u);
	}

	/**
	 * Affiche tous les articles en attente de validation dans une section réservée
	 * aux admins
	 */
	@GetMapping("/pending")
	public ResponseEntity<List<Article>> getArticlesPublishedAndNotValidated() {
		try {
			List<Article> articles = new ArrayList<Article>();
			articleRepository.findByIsPublishedAndNotValidated().forEach(articles::add);

			if (articles.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(articles, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Méthode appelée lorsque l'admin justifie par un commentaire sa décision de
	 * valider ou rejeter un article.
	 * 
	 * @param id          de l'article commenté
	 * @param commentaire justifiant la décision de l'administateur
	 * @return article modifié avec le nouveau commentaire
	 */
	@PutMapping("/comment-decision/{id}/{com}")
	public ResponseEntity<Article> commentDecision(@PathVariable("id") long id, @PathVariable("com") String com) {

		Article commentedArticle = articleRepository.findById(id).get();

		if (commentedArticle != null) {
			commentedArticle.setComAdmin(com);
			articleRepository.save(commentedArticle);

			return new ResponseEntity<>(commentedArticle, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Méthode appelée lorsque l'admin refuse la publication d'un article.
	 * 
	 * @param id de l'article à rejeter
	 * @return article modifié avec statut "estPublié" remis à false
	 */
	@PutMapping("/reject/{id}")
	public ResponseEntity<Article> rejectArticle(@PathVariable("id") long id) {
		try {
			Article rejectedArticle = articleRepository.findById(id).get();
			if (rejectedArticle != null) {
				if (rejectedArticle.getComAdmin() != null && !rejectedArticle.getComAdmin().trim().isEmpty()) {

					rejectedArticle.setEstPublie(false);
					articleRepository.save(rejectedArticle);

					// Envoi de l'email d'information à l'utilisateur
					emailAuthor(rejectedArticle.getAuteur(), rejectedArticle.getEstValide(), rejectedArticle.getTitre(),
							rejectedArticle.getComAdmin());

				} else {
					return new ResponseEntity<>(rejectedArticle, HttpStatus.EXPECTATION_FAILED);
				}
			}
			return new ResponseEntity<>(rejectedArticle, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Méthode appelée lorsque l'admin valide la publication d'un article.
	 * 
	 * @param id de l'article à valider
	 * @return article modifié avec statut "estValide" basculé à true
	 */
	@PutMapping("/validate/{id}")
	public ResponseEntity<Article> validateArticle(@PathVariable("id") long id) {
		try {
			Article validatedArticle = articleRepository.findById(id).get();

			if (validatedArticle != null) {
				validatedArticle.setEstValide(true);
				articleRepository.save(validatedArticle);
				// Envoi de l'email d'information à l'utilisateur
				emailAuthor(validatedArticle.getAuteur(), validatedArticle.getEstValide(), validatedArticle.getTitre(),
						validatedArticle.getComAdmin());
			}
			return new ResponseEntity<>(validatedArticle, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Récupère l'email de l'auteur de l'article et l'éventuel commentaire associé à
	 * l'article puis envoie un email à l'auteur
	 * 
	 * @param id
	 * @return
	 */
	public void emailAuthor(Utilisateur auteur, boolean estValide, String titre, String comAdmin) {

		// Récupération de l'email associé à l'utilisateur auteur de l'article
		String recipient = auteur.getMail();
		String prenom = auteur.getPrenom();

		String subject = "";
		String body = "";

		// Contenu du mail si l'admin a validé l'article
		if (estValide) {
			subject = "Votre article a été validé!";
			body = "Bonjour " + prenom + ", \n Votre article '" + titre
					+ "' vient d'être validé et est désormais accessible à la communauté. \n"
					+ " Commentaire de l'admin : '" + comAdmin + "' \n" + " Un grand merci pour votre contribution ! \n"
					+ "L'Equipe Wikicodia";
			// Contenu du mail si l'admin a refusé l'article
		} else {
			subject = "Votre article a été refusé...";
			body = "Bonjour " + prenom + ", \n Votre article '" + titre
					+ "' a malheureusement été refusé pour le motif suivant : '" + comAdmin
					+ "'. N'hésitez pas à y apporter des modifications et à le soumettre de nouveau. \n"
					+ " Merci de votre compréhension et à bientôt ! \n" + "L'Equipe Wikicodia";
		}

		EmailUtils.sendFromGmail(recipient, subject, body);
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
			a.setEstValide(false);
			a.setTitre(article.getTitre());
			a.setComAdmin(null);
			// suppose qu'on ne peut pas voter pour son propre article
			a.setVote(null);

				if (frameworkRepository.findByFrameworkAndVersionEquals(article.getFramework().getFramework(),
						article.getFramework().getVersion()) != null) {
					a.setFramework(frameworkRepository.findByFrameworkAndVersionEquals(article.getFramework().getFramework(),
						article.getFramework().getVersion()));
				} else {
					Framework newFram = new Framework();
					newFram.setFramework(article.getFramework().getFramework());
					newFram.setVersion(article.getFramework().getVersion());
					frameworkRepository.save(newFram);
					a.setFramework(frameworkRepository.findByFrameworkAndVersionEquals(article.getFramework().getFramework(),
							article.getFramework().getVersion()));
				}
			


				if (langageRepository.findByLangAndVersionEquals(article.getLangage().getLang(),article.getLangage().getVersion()) != null) {
					a.setLangage(langageRepository.findByLangAndVersionEquals(article.getLangage().getLang(),article.getLangage().getVersion()));
				} else {
					Langage newLang = new Langage();
					newLang.setLang(article.getLangage().getLang());
					newLang.setVersion(article.getLangage().getVersion());
					langageRepository.save(newLang);
					a.setLangage(langageRepository.findByLangAndVersionEquals(article.getLangage().getLang(),article.getLangage().getVersion()));
				}
				
				a.setCategorie(categorieRepository.findByLibCategorieEquals(article.getCategorie().getLibCategorie()));


//			a.setCategorie(categorieRepository.findByLibCategorieEquals(article.getCategorie().getLibCategorie()));
//			if (categorieRepository.findByLibCategorieEquals(article.getCategorie().getLibCategorie()) != null) {
//			} else {
//				Categorie newCat = new Categorie();
//				newCat.setLibCategorie(article.getCategorie().getLibCategorie());
//				categorieRepository.save(newCat);
//				a.setCategorie(categorieRepository.findByLibCategorieEquals(article.getCategorie().getLibCategorie()));
//			}
//			
				a.setType(typeRepository.findByLibTypeEquals(article.getType().getLibType()));

				
//			a.setType(typeRepository.findByLibTypeEquals(article.getType().getLibType()));
//			if (typeRepository.findByLibTypeEquals(article.getType().getLibType()) != null) {
//			} else {
//				Type newTyp = new Type();
//				newTyp.setLibType(article.getType().getLibType());
//				typeRepository.save(newTyp);
//				a.setType(typeRepository.findByLibTypeEquals(article.getType().getLibType()));
//			}

			Optional<Utilisateur> util = utilisateurRepository.findById(article.getAuteur().getIdUtilisateur());
			Utilisateur auteur = util.get();
			a.setAuteur(auteur);
//			a.setAuteur(utilisateurRepository.getOne((long)1));
//			System.out.println("TEEEEEESSSSSSSTTTTTTTT REEEEESSSSSUUUULLLLLTTTTT");
//			System.out.println(article.auteur);

			articleRepository.save(a);

			return new ResponseEntity<>(a, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
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
			_article.setEstPromu(false);
			_article.setEstValide(false);
			if (articleUpdated.getComAdmin() != null) {
				_article.setComAdmin(articleUpdated.getComAdmin());
			}
			if (articleUpdated.getVote() != null) {
				_article.setVote(articleUpdated.getVote());
			}
			if (articleUpdated.getLangage() != null) {
				if (langageRepository.findByLangAndVersionEquals(articleUpdated.getLangage().getLang(),articleUpdated.getLangage().getVersion()) != null) {
					_article.setLangage(langageRepository.findByLangAndVersionEquals(articleUpdated.getLangage().getLang(),articleUpdated.getLangage().getVersion()));
				} else {
					Langage newLang = new Langage();
					newLang.setLang(articleUpdated.getLangage().getLang());
					newLang.setVersion(articleUpdated.getLangage().getVersion());
					langageRepository.save(newLang);
					_article.setLangage(langageRepository.findByLangAndVersionEquals(articleUpdated.getLangage().getLang(),articleUpdated.getLangage().getVersion()));
				}
				}

			
			if (articleUpdated.getFramework() != null) {
				if (frameworkRepository.findByFrameworkAndVersionEquals(articleUpdated.getFramework().getFramework(),
						articleUpdated.getFramework().getVersion()) != null) {
					_article.setFramework(frameworkRepository.findByFrameworkAndVersionEquals(articleUpdated.getFramework().getFramework(),
							articleUpdated.getFramework().getVersion()));
				} else {
					Framework newFram = new Framework();
					newFram.setFramework(articleUpdated.getFramework().getFramework());
					newFram.setVersion(articleUpdated.getFramework().getVersion());
					frameworkRepository.save(newFram);
					_article.setFramework(frameworkRepository.findByFrameworkAndVersionEquals(articleUpdated.getFramework().getFramework(),
							articleUpdated.getFramework().getVersion()));
				}
			}
			if (articleUpdated.getType() != null) {
				_article.setType(typeRepository.findByLibTypeEquals(articleUpdated.getType().getLibType()));

			}
			if (articleUpdated.getCategorie() != null) {
				
				_article.setCategorie(categorieRepository
						.findByLibCategorieEquals(articleUpdated.getCategorie().getLibCategorie()));

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

	@GetMapping("/mesarticles/{userid}")
	public ResponseEntity<List<Article>> getAllArticlesOfUser(@PathVariable("userid") long userid) {

		Optional<Utilisateur> user = utilisateurRepository.findById(userid);
		List<Article> tousMesArticles = new ArrayList<Article>();

		if (user.isPresent()) {
			List<Article> tous = articleRepository.findAll();
			for (Article article : tous) {
				if (article.getAuteur().getIdUtilisateur() == userid) {
					tousMesArticles.add(article);
				}
			}

			return new ResponseEntity<>(tousMesArticles, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Change la visibilité de l'article, sans impact sur la validité mais avec
	 * impact sur la promotion. Lorsqu'on dé-publie un article qui a été promu par
	 * un admin, cela a pour effet d'annuler la promotion.
	 * 
	 * @param articleId
	 * @return article avec nouvelle visibilité
	 */
	@PutMapping("/toggle-visibility/{articleId}")
	public ResponseEntity<Article> toggleArticleVisibility(@PathVariable("articleId") Long articleId) {
		try {
			Article article = articleRepository.findById(articleId).get();
			if (article != null) {
				if (article.getEstPublie()) {
					article.setEstPublie(false);
					article.setEstPromu(false);
					articleRepository.save(article);
				} else {
					article.setEstPublie(true);
					articleRepository.save(article);
				}
			}
			return new ResponseEntity<>(article, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/mesarticles-profil/{userid}")
	public ResponseEntity<List<Article>> getAllArticlesOfUserLimitFive(@PathVariable("userid") long userid) {

		Utilisateur user = utilisateurRepository.findById(userid).get();
		List<Article> tousMesArticles = articleRepository.findFirst5ByAuteur(user);

		return new ResponseEntity<>(tousMesArticles, HttpStatus.OK);
	}

	@GetMapping("/articlesFavoris/{userId}")
	public ResponseEntity<List<Article>> getArticlesFavorisByUserId(@PathVariable("userId") long userId) {

		Optional<Utilisateur> user = utilisateurRepository.findById(userId);
		Utilisateur utilisateur = user.get();

		List<Article> articlesFavoris = utilisateur.getArticlesFavoris();
		if (articlesFavoris == null || articlesFavoris.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(articlesFavoris, HttpStatus.OK);
		}

	}

	@GetMapping("/articlesPromus")
	public ResponseEntity<List<Article>> getArticlesPromus() {

		List<Article> articlesPromus = articleRepository.findPromotedArticles();
		if (articlesPromus == null || articlesPromus.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(articlesPromus, HttpStatus.OK);
		}
	}
	
	@GetMapping("/derniersArticlesPromus")
	public ResponseEntity<List<Article>> getDerniersArticlesPromus() {
		List<Article> derniersArticlesPromus = articleRepository.findLastPromotedArticles();
		
		if(derniersArticlesPromus == null || derniersArticlesPromus.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(derniersArticlesPromus, HttpStatus.OK);
		}
	}

	@PutMapping("/togglePromotion/{articleId}")
	public ResponseEntity<Article> toggleArticlePromotion(@PathVariable("articleId") Long articleId) {

		Optional<Article> articleOptional = articleRepository.findById(articleId);

		if (articleOptional.isPresent()) {
			Article article = articleOptional.get();

			if (article.getEstPromu().booleanValue() == true) {
				System.out.println("Article promu ? :" + article.getEstPromu().booleanValue());
				article.setEstPromu(false);
				articleRepository.save(article);

			} else {
				System.out.println("Article promu ? :" + article.getEstPromu().booleanValue());
				article.setEstPromu(true);
				articleRepository.save(article);
			}
			return new ResponseEntity<>(article, HttpStatus.OK);

		} else {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}
	
	//Select article from Article article where article.langage = nom or article.langage = nom or article.framework = nom or 
	@GetMapping("/articlesSuggeres/{userId}")
	public ResponseEntity<List<Article>> getArticlesSuggeres(@PathVariable("userId") Long userId){
		Optional <Utilisateur> user = utilisateurRepository.findById(userId);
		if (user.isPresent()) {
			Utilisateur utilisateur = user.get();
			String query = "Select article from Article article where ";
			
			List<Langage> langagesPreferes = utilisateur.getLangage();
			List<Framework> frameworksPreferes = utilisateur.getFramework();
			//List<Categorie> categoriesPreferes = utilisateur.getCategorie();
			List<Type> typesPreferes = utilisateur.getType();
			
			if(langagesPreferes.size() > 0) {
				for(int j = 0 ; j < langagesPreferes.size() ; j++) {
					if(typesPreferes.size() == 0) {
						query = query + "article.langage.lang = '" + langagesPreferes.get(j).getLang() + "' ";
					} else {
						for(int i = 0 ; i < typesPreferes.size() ; i++) {
							query = query + "article.langage.lang = '" + langagesPreferes.get(j).getLang() + "' and article.type.libType = '" + typesPreferes.get(i).getLibType() + "' ";
							if (i < typesPreferes.size() -1) {
								query = query + "or ";
							}
						}
					}
					if (j < langagesPreferes.size() -1) {
						query = query + "or ";
					}
				}
			}
			
			if(frameworksPreferes.size() > 0) {
				if(query != "Select article from Article article where ") {
					query = query + "or ";
				}
				
				for(int i = 0 ; i < frameworksPreferes.size() ; i++) {
					if(typesPreferes.size() == 0) {
						query = query + "article.framework.framework = '" + frameworksPreferes.get(i).getFramework() + "' ";
					} else {
						for(int j = 0 ; j < typesPreferes.size(); j++) {
							query = query + "article.framework.framework = '" + frameworksPreferes.get(i).getFramework() + "' and article.type.libType = '" + typesPreferes.get(j).getLibType() + "' ";
							if(j < typesPreferes.size() -1) {
								query = query + "or ";
							}
						}
					}
					if(i < frameworksPreferes.size() -1) {
						query = query + "or ";
					}
				}
			}
			
			System.out.println(query);
			List<Article> articlesPreferes = new ArrayList<Article>();
			
			if (query == "Select article from Article article where ") {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				query = query + "and article.estValide = 1";
				articlesPreferes = articleRepository.findArticleWithPreferences(query);
			}
			
			return new ResponseEntity<>(articlesPreferes , HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}
	
	@GetMapping("/articlesRecents")
	public ResponseEntity<List<Article>> findNewArticles(){
		List<Article> newerArticles = articleRepository.findNewerArticles();
		if(newerArticles.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(newerArticles , HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/articlesFavorisIds/{userId}")
	public ResponseEntity<List<Long>> findArticlesFavorisIds(@PathVariable("userId") Long userId){
		Utilisateur utilisateur = utilisateurRepository.findById(userId).get();
		List<Article> articlesFavoris = utilisateur.getArticlesFavoris();
		List<Long> articlesFavorisIds = new ArrayList<Long>();
		for(int i = 0 ; i < articlesFavoris.size() ; i++) {
			articlesFavorisIds.add(articlesFavoris.get(i).getIdArticle());
		}
		if(articlesFavorisIds.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(articlesFavorisIds , HttpStatus.OK);
		}
		
	}
	
	@PutMapping("/ajouterAuxFavoris/{userId}")
	public ResponseEntity<Article> addToMyFavorites(@PathVariable("userId") Long userId , @RequestBody Article article){
		Utilisateur utilisateur = utilisateurRepository.findById(userId).get();
		utilisateur.getArticlesFavoris().add(article);
		try {
			utilisateurRepository.save(utilisateur);
			return new ResponseEntity<>(article , HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/supprimerDesFavoris/{userId}")
	public ResponseEntity<Article> deleteFromFavorites(@PathVariable("userId") Long userId , @RequestBody Article article){
		
		
		try {
			articleRepository.deleteFromFavorites(userId, article.getIdArticle());
			return new ResponseEntity<>(article , HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

}
