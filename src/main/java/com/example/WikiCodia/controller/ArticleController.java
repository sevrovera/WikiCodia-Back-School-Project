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
		commentedArticle.setComAdmin(com);
		articleRepository.save(commentedArticle);

		return new ResponseEntity<>(commentedArticle, HttpStatus.OK);
	}

	/**
	 * Méthode appelée lorsque l'admin refuse la publication d'un article.
	 * 
	 * @param id de l'article à rejeter
	 * @return article modifié avec statut "estPublié" remis à false
	 */
	@PutMapping("/reject/{id}")
	public ResponseEntity<Article> rejectArticle(@PathVariable("id") long id) {

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
	}

	/**
	 * Méthode appelée lorsque l'admin valide la publication d'un article.
	 * 
	 * @param id de l'article à valider
	 * @return article modifié avec statut "estValide" basculé à true
	 */
	@PutMapping("/validate/{id}")
	public ResponseEntity<Article> validateArticle(@PathVariable("id") long id) {
		
		Article validatedArticle = articleRepository.findById(id).get();
		if (validatedArticle != null) {
			validatedArticle.setEstValide(true);
			articleRepository.save(validatedArticle);
			// Envoi de l'email d'information à l'utilisateur
			emailAuthor(validatedArticle.getAuteur(), validatedArticle.getEstValide(), validatedArticle.getTitre(),
					validatedArticle.getComAdmin());
		}
		return new ResponseEntity<>(validatedArticle, HttpStatus.OK);
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
					+ " Commentaire de l'admin : '" + comAdmin + "' \n" 
					+ " Un grand merci pour votre contribution ! \n"
					+ "L'Equipe Wikicodia";
			// Contenu du mail si l'admin a refusé l'article
		} else {
			subject = "Votre article a été refusé...";
			body = "Bonjour " + prenom + ", \n Votre article '" + titre
					+ "' a malheureusement été refusé pour le motif suivant : '" + comAdmin
					+ "'. N'hésitez pas à y apporter des modifications et à le soumettre de nouveau. \n"
					+ " Merci de votre compréhension et à bientôt ! \n"
					+ "L'Equipe Wikicodia";
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

	/*
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

			List<Framework> listFram = new ArrayList<Framework>();
			for (Framework frameworkItere : article.getFramework()) {
				if (frameworkRepository.findByFrameworkAndVersionEquals(frameworkItere.getFramework(),
						frameworkItere.getVersion()) != null) {
					listFram.add(frameworkRepository.findByFrameworkAndVersionEquals(frameworkItere.getFramework(),
							frameworkItere.getVersion()));
				} else {
					Framework newFram = new Framework();
					newFram.setFramework(frameworkItere.getFramework());
					newFram.setVersion(frameworkItere.getVersion());
					frameworkRepository.save(newFram);
					listFram.add(frameworkRepository.findByFrameworkAndVersionEquals(frameworkItere.getFramework(),
							frameworkItere.getVersion()));
				}
			}
			a.setFramework(listFram);

			List<Langage> listLang = new ArrayList<Langage>();
			for (Langage langageItere : article.getLangage()) {

				if (langageRepository.findByLangAndVersionEquals(langageItere.getLang(),
						langageItere.getVersion()) != null) {
					listLang.add(langageRepository.findByLangAndVersionEquals(langageItere.getLang(),
							langageItere.getVersion()));
				} else {
					Langage newLang = new Langage();
					newLang.setLang(langageItere.getLang());
					newLang.setVersion(langageItere.getVersion());
					langageRepository.save(newLang);
					listLang.add(langageRepository.findByLangAndVersionEquals(langageItere.getLang(),
							langageItere.getVersion()));
				}
			}
			a.setLangage(listLang);

//			a.setCategorie(categorieRepository.findByLibCategorieEquals(article.getCategorie().getLibCategorie()));
			if (categorieRepository.findByLibCategorieEquals(article.getCategorie().getLibCategorie()) != null) {
				a.setCategorie(categorieRepository.findByLibCategorieEquals(article.getCategorie().getLibCategorie()));
			} else {
				Categorie newCat = new Categorie();
				newCat.setLibCategorie(article.getCategorie().getLibCategorie());
				categorieRepository.save(newCat);
				a.setCategorie(categorieRepository.findByLibCategorieEquals(article.getCategorie().getLibCategorie()));
			}
//			
//			a.setType(typeRepository.findByLibTypeEquals(article.getType().getLibType()));
			if (typeRepository.findByLibTypeEquals(article.getType().getLibType()) != null) {
				a.setType(typeRepository.findByLibTypeEquals(article.getType().getLibType()));
			} else {
				Type newTyp = new Type();
				newTyp.setLibType(article.getType().getLibType());
				typeRepository.save(newTyp);
				a.setType(typeRepository.findByLibTypeEquals(article.getType().getLibType()));
			}

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
			if (articleUpdated.getEstPromu()) {
				_article.setEstPromu(articleUpdated.getEstPromu());
			} else {
				_article.setEstPromu(false);
			}
			if (articleUpdated.getEstValide()) {
				_article.setEstValide(articleUpdated.getEstValide());
			} else {
				_article.setEstValide(false);
			}
			if (articleUpdated.getComAdmin() != null) {
				_article.setComAdmin(articleUpdated.getComAdmin());
			}
			if (articleUpdated.getVote() != null) {
//				List<Vote> listVote = new ArrayList<Vote>();
//				for (Vote voteItere : articleUpdated.getVote()) {
//					
//					if (voteItere.getIdVote() != null) {
//						voteRepository.save(voteItere);
//						listVote.add(voteRepository.findByLikedAndCommentaireAndUtilisateurEquals(voteItere.getLiked(), voteItere.getCommentaire(), voteItere.getUtilisateur()));
//					}
//					else {
//						Vote newVote = new Vote();
//						newVote.setCommentaire(voteItere.getCommentaire());
//						newVote.setLiked(voteItere.getLiked());
//						newVote.setUtilisateur(voteItere.getUtilisateur());
//						voteRepository.save(newVote);
//						listVote.add(voteRepository.findByLikedAndCommentaireAndUtilisateurEquals(voteItere.getLiked(), voteItere.getCommentaire(), voteItere.getUtilisateur()));
//					}				
//				}
				_article.setVote(articleUpdated.getVote());
			}
			if (articleUpdated.getLangage() != null) {
				List<Langage> listLang = new ArrayList<Langage>();
				for (Langage langageItere : articleUpdated.getLangage()) {

					if (langageRepository.findByLangAndVersionEquals(langageItere.getLang(),
							langageItere.getVersion()) != null) {
						listLang.add(langageRepository.findByLangAndVersionEquals(langageItere.getLang(),
								langageItere.getVersion()));
					} else {
						Langage newLang = new Langage();
						newLang.setLang(langageItere.getLang());
						newLang.setVersion(langageItere.getVersion());
						langageRepository.save(newLang);
						listLang.add(langageRepository.findByLangAndVersionEquals(langageItere.getLang(),
								langageItere.getVersion()));
					}
				}
				_article.setLangage(listLang);

			}
			if (articleUpdated.getFramework() != null) {

				List<Framework> listFram = new ArrayList<Framework>();
				for (Framework frameworkItere : articleUpdated.getFramework()) {
					if (frameworkRepository.findByFrameworkAndVersionEquals(frameworkItere.getFramework(),
							frameworkItere.getVersion()) != null) {
						listFram.add(frameworkRepository.findByFrameworkAndVersionEquals(frameworkItere.getFramework(),
								frameworkItere.getVersion()));
					} else {
						Framework newFram = new Framework();
						newFram.setFramework(frameworkItere.getFramework());
						newFram.setVersion(frameworkItere.getVersion());
						frameworkRepository.save(newFram);
						listFram.add(frameworkRepository.findByFrameworkAndVersionEquals(frameworkItere.getFramework(),
								frameworkItere.getVersion()));
					}
				}
				_article.setFramework(listFram);
			}
			if (articleUpdated.getType() != null) {
				if (typeRepository.findByLibTypeEquals(articleUpdated.getType().getLibType()) != null) {
					_article.setType(typeRepository.findByLibTypeEquals(articleUpdated.getType().getLibType()));
				} else {
					Type newTyp = new Type();
					newTyp.setLibType(articleUpdated.getType().getLibType());
					typeRepository.save(newTyp);
					_article.setType(typeRepository.findByLibTypeEquals(articleUpdated.getType().getLibType()));
				}

			}
			if (articleUpdated.getCategorie() != null) {
				if (categorieRepository
						.findByLibCategorieEquals(articleUpdated.getCategorie().getLibCategorie()) != null) {
					_article.setCategorie(categorieRepository
							.findByLibCategorieEquals(articleUpdated.getCategorie().getLibCategorie()));
				} else {
					Categorie newCat = new Categorie();
					newCat.setLibCategorie(articleUpdated.getCategorie().getLibCategorie());
					categorieRepository.save(newCat);
					_article.setCategorie(categorieRepository
							.findByLibCategorieEquals(articleUpdated.getCategorie().getLibCategorie()));
				}
			}

			return new ResponseEntity<>(articleRepository.save(_article), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
*/
	
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
	public ResponseEntity<List<Article>> getArticlesPromus(){
		
		List<Article> articlesPromus = articleRepository.findPomotedArticles();
		if (articlesPromus == null || articlesPromus.size() == 0) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(articlesPromus, HttpStatus.OK);
		}
	}
	
	@PutMapping("/togglePromotion/{articleId}")
	public ResponseEntity<Article> toggleArticlePromotion(@PathVariable("articleId") Long articleId){
		
		Optional<Article> articleOptional = articleRepository.findById(articleId);
		
		if(articleOptional.isPresent()) {
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
			//Récupération des langages préférés
			List<Langage> langagesPreferes = utilisateur.getLangage();
			
			if (langagesPreferes.size() > 0) {
				for(int i = 0 ; i < langagesPreferes.size() ; i++) {
					
					query = query + "article.langage.lang = '" + langagesPreferes.get(i).getLang().toString() + "' ";
					if (i < langagesPreferes.size() -1) {
						query = query + "or ";
					}
				}
			}
			
			
			List<Framework> frameworksPreferes = utilisateur.getFramework();
			
			if (frameworksPreferes.size() > 0) {
				query = query + "or ";
				for(int i = 0 ; i < frameworksPreferes.size() ; i++) {
					query = query + "article.framework.framework = '" + frameworksPreferes.get(i).getFramework().toString() + "' ";
					if (i < frameworksPreferes.size() -1) {
						query = query + "or ";
					}
				}
			}
			
			
			List<Categorie> categoriesPreferes = utilisateur.getCategorie();
			
			if (categoriesPreferes.size() > 0) {
				query = query + "or ";
				for(int i = 0 ; i < categoriesPreferes.size() ; i++) {
					query = query + "article.categorie.libCategorie = '" + categoriesPreferes.get(i).getLibCategorie().toString() + "' ";
					if (i < categoriesPreferes.size() -1) {
						query = query + "or ";
					}
				}
			}
			
			
			List<Type> typesPreferes = utilisateur.getType();
			
			if (typesPreferes.size() > 0) {
				query = query + "or ";
				for(int i = 0 ; i < typesPreferes.size() ; i++) {
					query = query + "article.type.libType = '" + typesPreferes.get(i).getLibType().toString() + "' ";
					if (i < typesPreferes.size() -1) {
						query = query + "or ";
					}
				}
			}
			
			
			
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

}
