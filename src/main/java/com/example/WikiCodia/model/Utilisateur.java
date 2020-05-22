package com.example.WikiCodia.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="utilisateur")
public class Utilisateur {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_utilisateur")
	private Long idUtilisateur;
	
	@Column(name="prenom")
	private String prenom;
	
	@Column(name="nom")
	private String nom;
	
	@Column(name="pseudo")
	private String pseudo;
	
	@Column(name="mail")
	private String mail;
	
	@Column(name="mot_de_passe")
	private String motDePasse;
	
	@Column(name="lien_linkedin")
	private String lienLinkedin;
	
	@Column(name="statut")
	private String statut;
	
	@Column(name="date_inscription")
	private LocalDate dateInscription;
	
	@Column(name="date_derniere_connexion")
	private LocalDate dateDerniereConnexion;
	
	//GETTERS SETTERS
	public Long getIdUtilisateur() {
		return idUtilisateur;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getLienLinkedIn() {
		return lienLinkedin;
	}

	public void setLienLinkedIn(String lienLinkedin) {
		this.lienLinkedin = lienLinkedin;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public LocalDate getDateInscription() {
		return dateInscription;
	}

	public void setDateInscription(LocalDate dateInscription) {
		this.dateInscription = dateInscription;
	}

	public LocalDate getDateDerniereConnexion() {
		return dateDerniereConnexion;
	}

	public void setDateDerniereConnexion(LocalDate dateDerniereConnexion) {
		this.dateDerniereConnexion = dateDerniereConnexion;
	}
	
	//CONSTRUCTOR	
	public Utilisateur(String prenom, String nom, String pseudo, String mail,
			String lienLinkedin, String statut, LocalDate dateDerniereConnexion) {
		super();
		this.prenom = prenom;
		this.nom = nom;
		this.pseudo = pseudo;
		this.mail = mail;
		this.lienLinkedin = lienLinkedin;
		this.statut = statut;
		this.dateDerniereConnexion = dateDerniereConnexion;
	}
	
	public Utilisateur() {
		
	}		
}
