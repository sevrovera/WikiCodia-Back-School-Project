package com.example.WikiCodia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.WikiCodia.model.Utilisateur;
import com.example.WikiCodia.repository.UtilisateurRepository;

@RestController
@RequestMapping("/utilisateur")
public class UtilisateurController {
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@RequestMapping(value = "/creation", method = RequestMethod.POST)
	@ResponseBody
	public Utilisateur cree(Utilisateur u) {
		System.out.println(u);
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

	/*
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public void nouvelUtilisateur(
			@RequestHeader ("prenom") String prenom, 
			@RequestHeader("nom") String nom, 
			@RequestHeader("pseudo") String pseudo, 
			@RequestHeader("mail") String mail,
			@RequestHeader("lienLinkedin") String lienLinkedin, 
			@RequestHeader("statut") String statut) {
		LocalDate dateIncription = LocalDate.now();	
		Utilisateur nouvelUtilisateur = new Utilisateur(prenom,nom,pseudo,mail,lienLinkedin,statut,dateIncription);
		utilisateurRepository.save(nouvelUtilisateur);
	} */
	
	
	/*
	@RequestMapping("/connexion")
    @ResponseBody
    public Utilisateur connexion(@RequestHeader("mail") String mail, @RequestHeader("motDePasse") String motDePasse){
        Utilisateur utilisateur = utilisateurRepository.findByMail(mail);
        if(utilisateur.getMotDePasse().equals(motDePasse)){
            return utilisateur;
        }
        return null;
    }
	*/
}
