package com.example.WikiCodia.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.WikiCodia.model.Utilisateur;
import com.example.WikiCodia.repository.UtilisateurRepository;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {
	
	
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@RequestMapping(value = "/creation", method = RequestMethod.POST)
	@ResponseBody
	public Utilisateur cree(@RequestBody Utilisateur u) {
		utilisateurRepository.save(u);
		return u;
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
