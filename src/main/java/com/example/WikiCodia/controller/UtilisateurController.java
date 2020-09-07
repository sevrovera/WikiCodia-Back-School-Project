package com.example.WikiCodia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.WikiCodia.model.Categorie;
import com.example.WikiCodia.model.Framework;
import com.example.WikiCodia.model.Langage;
import com.example.WikiCodia.model.Role;
import com.example.WikiCodia.model.Type;

import com.example.WikiCodia.model.Utilisateur;
import com.example.WikiCodia.repository.CategorieRepository;
import com.example.WikiCodia.repository.EtatRepository;
import com.example.WikiCodia.repository.FrameworkRepository;
import com.example.WikiCodia.repository.LangageRepository;
import com.example.WikiCodia.repository.RoleRepository;
import com.example.WikiCodia.repository.TypeRepository;
import com.example.WikiCodia.repository.UtilisateurRepository;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	private CategorieRepository categorieRepository;

	@Autowired
	private FrameworkRepository frameworkRepository;

	@Autowired
	private LangageRepository langageRepository;

	@Autowired
	private TypeRepository typeRepository;

	@Autowired
	private EtatRepository etatRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@RequestMapping(value = "/creation", method = RequestMethod.POST)
	@ResponseBody
	public Utilisateur cree(Utilisateur u) {
		//mise ne place de l etat actif
		u.setEtat(etatRepository.findById((long) 1).get());
		u.setMotDePasse(passwordEncoder.encode(u.getMotDePasse()));
		Role role = roleRepository.save(new Role("normal"));
		u.setRole(role);
		utilisateurRepository.save(u);
		return u;
	}

	@RequestMapping(value = "/modification", method = RequestMethod.PUT)
	@ResponseBody
	public Utilisateur modification(@RequestBody Utilisateur u) {
		System.out.println(u);

		Utilisateur modifUtilisateur = utilisateurRepository.findById(u.getIdUtilisateur()).get();

		if (!modifUtilisateur.getMotDePasse().equals(u.getMotDePasse())) {
			modifUtilisateur.setMotDePasse(passwordEncoder.encode(u.getMotDePasse()));
		}

		modifUtilisateur.setNom(u.getNom());
		modifUtilisateur.setPrenom(u.getPrenom());
		modifUtilisateur.setPseudo(u.getPseudo());
		modifUtilisateur.setMail(u.getMail());
		modifUtilisateur.setLienLinkedIn(u.getLienLinkedIn());
		modifUtilisateur.setStatut(u.getStatut());
		modifUtilisateur.setEtat(u.getEtat());
		modifUtilisateur.setRole(u.getRole());
		modifUtilisateur.setGuilde(u.getGuilde());
		modifUtilisateur.setFramework(u.getFramework());
		modifUtilisateur.setLangage(u.getLangage());
		modifUtilisateur.setType(u.getType());
		modifUtilisateur.setCategorie(u.getCategorie());
		modifUtilisateur.setDateInscription(u.getDateInscription());
		modifUtilisateur.setDateDerniereConnexion(u.getDateDerniereConnexion());

		utilisateurRepository.save(modifUtilisateur);

		return modifUtilisateur;
	}

	@RequestMapping(value = "/modification-speciale", method = RequestMethod.PUT)
	@ResponseBody
	public Utilisateur modificatioSpeciale(@RequestBody Utilisateur u) {
		System.out.println(u);

		Utilisateur modifUtilisateur = utilisateurRepository.findById(u.getIdUtilisateur()).get();

		if (!modifUtilisateur.getMotDePasse().equals(u.getMotDePasse())) {
			modifUtilisateur.setMotDePasse(passwordEncoder.encode(u.getMotDePasse()));
		}

		modifUtilisateur.setNom(u.getNom());
		modifUtilisateur.setPrenom(u.getPrenom());
		modifUtilisateur.setPseudo(u.getPseudo());
		modifUtilisateur.setMail(u.getMail());
		modifUtilisateur.setLienLinkedIn(u.getLienLinkedIn());
		modifUtilisateur.setStatut(u.getStatut());
		modifUtilisateur.setEtat(u.getEtat());
		modifUtilisateur.setRole(u.getRole());
		modifUtilisateur.setGuilde(u.getGuilde());
		modifUtilisateur.setDateInscription(u.getDateInscription());
		modifUtilisateur.setDateDerniereConnexion(u.getDateDerniereConnexion());

		utilisateurRepository.save(modifUtilisateur);

		return modifUtilisateur;
	}

	@RequestMapping(value = "/modification-date", method = RequestMethod.PUT)
	@ResponseBody
	public Utilisateur modificationDate(@RequestBody Utilisateur u) {
		System.out.println(u);
		Utilisateur modifUtilisateur = utilisateurRepository.findById(u.getIdUtilisateur()).get();

		modifUtilisateur.setDateDerniereConnexion(u.getDateDerniereConnexion());

		utilisateurRepository.save(modifUtilisateur);

		return modifUtilisateur;
	}


	@RequestMapping(value = "/suppression/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Boolean suppression(@PathVariable("id") Long id) {
		utilisateurRepository.deleteById(id);
		return true;
	}

	@RequestMapping("/{id}")
	@ResponseBody
	public Utilisateur voir(@PathVariable("id") Long id) {

		return utilisateurRepository.findById(id).get();

	}

	// Utilisé pour recupérer l utilisateur avec ces préférences
	@GetMapping("/categories/{userId}")
	public ResponseEntity<List<Categorie>> getCategorieByUserId(@PathVariable("userId") long userId) {
		
		Optional<Utilisateur> user = utilisateurRepository.findById(userId);
		Utilisateur utilisateur = user.get();
		
		List<Categorie> categories = utilisateur.getCategorie();
		return new ResponseEntity<>(categories, HttpStatus.OK);	
	}

	@GetMapping("/framework/{userId}")
	public ResponseEntity<List<Framework>> getFrameworkByUserId(@PathVariable("userId") long userId) {
		
		Optional<Utilisateur> user = utilisateurRepository.findById(userId);
		Utilisateur utilisateur = user.get();
		
		List<Framework> framework = utilisateur.getFramework();
		return new ResponseEntity<>(framework, HttpStatus.OK);

		
	}

	@GetMapping("/type/{userId}")
	public ResponseEntity<List<Type>> getTypeByUserId(@PathVariable("userId") long userId) {
		
		Optional<Utilisateur> user = utilisateurRepository.findById(userId);
		Utilisateur utilisateur = user.get();
		
		List<Type> type = utilisateur.getType();
		return new ResponseEntity<>(type, HttpStatus.OK);
		
		
	}

	@GetMapping("/langage/{userId}")
	public ResponseEntity<List<Langage>> getLangageByUserId(@PathVariable("userId") long userId) {
		
		Optional<Utilisateur> user = utilisateurRepository.findById(userId);
		Utilisateur utilisateur = user.get();
		
		List<Langage> langage = utilisateur.getLangage();
		return new ResponseEntity<>(langage, HttpStatus.OK);
		
		
	}
	
	@GetMapping("/trouverUnUtilisateur")
	@ResponseBody
	public Optional<Utilisateur> findByMail(@RequestHeader("mail") String mail) {
		return utilisateurRepository.findUserWithMail(mail);
	}
	
	@PostMapping("/trouverDesAuteurs")
	@ResponseBody
	public ResponseEntity<List<Utilisateur>> findAuteurs(@RequestBody List<Long> auteursIds){
		List<Utilisateur> auteurs = new ArrayList<Utilisateur>();
		
		for (int i = 0 ; i < auteursIds.size() ; i++) {
			Long id = auteursIds.get(i);
			Utilisateur auteur = utilisateurRepository.findByIdUtilisateurEquals(id);
			auteurs.add(auteur);
		}
		
		if(auteurs.size() == 0 || auteurs == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(auteurs, HttpStatus.OK);
		}	
	}

	@PostMapping("/set-categories/{userId}")
	@ResponseBody
	public ResponseEntity<List<Categorie>> findCategories(@RequestBody List<Categorie> catId, @PathVariable("userId") long userId){
		//recup utilisateur
		Utilisateur utilisateur = utilisateurRepository.findById(userId).get();
		//remplacement des données de l utilisateur
		utilisateur.setCategorie(catId);
		//sauvegarde utilisateur
		utilisateurRepository.save(utilisateur);
		return new ResponseEntity<>(catId, HttpStatus.OK);
	}

	@PostMapping("/set-framework/{userId}")
	@ResponseBody
	public ResponseEntity<List<Framework>> findFramework(@RequestBody List<Framework> frameworks, @PathVariable("userId") long userId){
		//recup utilisateur
		Utilisateur utilisateur = utilisateurRepository.findById(userId).get();
		//remplacement des données de l utilisateur
		utilisateur.setFramework(frameworks);
		//sauvegarde utilisateur
		utilisateurRepository.save(utilisateur);
		return new ResponseEntity<>(frameworks, HttpStatus.OK);
	}

	@PostMapping("/set-langage/{userId}")
	@ResponseBody
	public ResponseEntity<List<Langage>> findLangage(@RequestBody List<Langage> langages, @PathVariable("userId") long userId){
		//recup utilisateur
		Utilisateur utilisateur = utilisateurRepository.findById(userId).get();
		//remplacement des données de l utilisateur
		utilisateur.setLangage(langages);
		//sauvegarde utilisateur
		utilisateurRepository.save(utilisateur);
		return new ResponseEntity<>(langages, HttpStatus.OK);
	}

	@PostMapping("/set-type/{userId}")
	@ResponseBody
	public ResponseEntity<List<Type>> findType(@RequestBody List<Type> types, @PathVariable("userId") long userId){
		//recup utilisateur
		Utilisateur utilisateur = utilisateurRepository.findById(userId).get();
		//remplacement des données de l utilisateur
		utilisateur.setType(types);
		//sauvegarde utilisateur
		utilisateurRepository.save(utilisateur);
		return new ResponseEntity<>(types, HttpStatus.OK);
	}
}
