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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.WikiCodia.model.Role;

import com.example.WikiCodia.model.Utilisateur;
import com.example.WikiCodia.repository.EtatRepository;
import com.example.WikiCodia.repository.RoleRepository;
import com.example.WikiCodia.repository.UtilisateurRepository;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;

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

		modifUtilisateur.setNom(u.getNom());
		modifUtilisateur.setPrenom(u.getPrenom());
		modifUtilisateur.setPseudo(u.getPseudo());
		modifUtilisateur.setMail(u.getMail());
		modifUtilisateur.setLienLinkedIn(u.getLienLinkedIn());
		modifUtilisateur.setStatut(u.getStatut());
		modifUtilisateur.setEtat(u.getEtat());
		modifUtilisateur.setRole(u.getRole());
		modifUtilisateur.setGuilde(u.getGuilde());
		modifUtilisateur.setMotDePasse(u.getMotDePasse());
		modifUtilisateur.setFramework(u.getFramework());
		modifUtilisateur.setLangage(u.getLangage());
		modifUtilisateur.setType(u.getType());
		modifUtilisateur.setCategorie(u.getCategorie());
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
}
